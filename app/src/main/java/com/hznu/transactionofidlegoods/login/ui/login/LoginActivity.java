package com.hznu.transactionofidlegoods.login.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.hznu.transactionofidlegoods.utils.IdentifyingCodeUtils;
import com.hznu.transactionofidlegoods.utils.SharePreferencesUtil;

public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private EditText identifyingCodeEditText;
    private ImageView identifyingCodeImageView;
    private ProgressBar loadingProgressBar;
    private Bitmap identifyingCodeBitmap;
    private String identifyingCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        usernameEditText = (EditText) findViewById(R.id.edtTxt_username);
        passwordEditText = (EditText) findViewById(R.id.edtTxt_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        registerButton = (Button) findViewById(R.id.btn_register);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading);
        identifyingCodeEditText = (EditText) findViewById(R.id.edtTxt_identifyingCode);
        identifyingCodeImageView = (ImageView) findViewById(R.id.iv_identifyingCode);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        //判断本地是否缓存了用户账号密码，如果缓存即直接登录否则跳转到登录界面
        SharedPreferences userinformation = getSharedPreferences(SharePreferencesUtil.USER_INFORMATION_FILE, MODE_PRIVATE);
        String localUsername = userinformation.getString("username", null);
        String localPassword = userinformation.getString("password", null);
        if (localUsername != null && localPassword != null) {
            loadingProgressBar.setVisibility(View.VISIBLE);
            //将登录信息传至后台校验
            loginViewModel.login(localUsername, localPassword);
        }

        //获取工具类生成的图片验证码对象
        identifyingCodeBitmap = IdentifyingCodeUtils.getInstance().createBitmap();
        //获取当前图片验证码的对应内容用于校验
        identifyingCode = IdentifyingCodeUtils.getInstance().getCode();

        identifyingCodeImageView.setImageBitmap(identifyingCodeBitmap);
        identifyingCodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifyingCodeBitmap = IdentifyingCodeUtils.getInstance().createBitmap();
                identifyingCode = IdentifyingCodeUtils.getInstance().getCode();
                identifyingCodeImageView.setImageBitmap(identifyingCodeBitmap);
                Toast.makeText(LoginActivity.this, "验证码修改成功", Toast.LENGTH_SHORT).show();
            }
        });


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

                    //存储此次用户登录信息到本地
                    SharePreferencesUtil.save(LoginActivity.this,
                            usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            SharePreferencesUtil.USER_INFORMATION_FILE);

                    updateUiWithUser(loginResult.getSuccess());

                    //跳转主页面
                    Intent intent = new Intent(LoginActivity.this, BottonNavigationActivity.class);
                    startActivity(intent);

                    setResult(Activity.RESULT_OK);

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
                if (identifyingCodeEditText.getText().toString().equalsIgnoreCase(identifyingCode)) {
                    //验证码正确才进行登录
                    //简单表单校验无误后按下按钮，显示进度条
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    //将登录信息传至后台校验
                    loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                } else {
                    //创建弹窗
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("验证码不正确！");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                    //刷新验证码
                    identifyingCodeBitmap = IdentifyingCodeUtils.getInstance().createBitmap();
                    identifyingCode = IdentifyingCodeUtils.getInstance().getCode();
                    identifyingCodeImageView.setImageBitmap(identifyingCodeBitmap);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferencesUtil.clear(LoginActivity.this, SharePreferencesUtil.USER_INFORMATION_FILE);
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