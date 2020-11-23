package com.hznu.transactionofidlegoods.login.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.bottomnavigation.BottonNavigationActivity;
import com.hznu.transactionofidlegoods.utils.BaseActivity;

public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.edtTxt_username);
        final EditText passwordEditText = findViewById(R.id.edtTxt_password);
        final Button loginButton = findViewById(R.id.btn_login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        //观察登录表单的数据验证状态(此处代表简单表单验证)
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                //非空代表LoginFormState被赋值
                //如果有误，将按钮设置为不可按状态，并且发出错误信息
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        //观察登录结果状态
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                //表示登录信息检验(非表单校验)，此处代表信息核对完成后，进度条隐藏
                loadingProgressBar.setVisibility(View.GONE);

                if (loginResult.getError() != null) {  //信息核对失败处理
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {  //信息核对成功处理
                    updateUiWithUser(loginResult.getSuccess());

                    Toast.makeText(LoginActivity.this, "Hey Man!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, BottonNavigationActivity.class);
                    startActivity(intent);

                    setResult(Activity.RESULT_OK);

                    //Complete and destroy login activity once successful
                    //成功后完成并销毁登录活动
                    finish();
                }

            }
        });

        //创建输入框监听事件监听器
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        //为文本框设定对应校验信息
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        //对密码输入框加一层验证
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        //添加按钮click监听事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //简单表单校验无误后按下按钮，显示进度条
                loadingProgressBar.setVisibility(View.VISIBLE);
                //将登录信息传至后台校验
                loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
    }

    //登录成功处理方法
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();
    }

    //登录失败处理方法
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}