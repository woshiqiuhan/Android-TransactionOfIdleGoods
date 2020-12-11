package com.hznu.transactionofidlegoods.register;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.alibaba.fastjson.JSON;
import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.bottomnavigation.BottonNavigationActivity;
import com.hznu.transactionofidlegoods.domain.User;
import com.hznu.transactionofidlegoods.login.LoginActivity;
import com.hznu.transactionofidlegoods.service.GetUserInfo;
import com.hznu.transactionofidlegoods.service.UserRegister;
import com.hznu.transactionofidlegoods.utils.BaseActivity;
import com.hznu.transactionofidlegoods.utils.FilePersistenceUtils;
import com.hznu.transactionofidlegoods.utils.IdentifyingCodeUtils;
import com.hznu.transactionofidlegoods.utils.MD5Util;
import com.hznu.transactionofidlegoods.utils.SharePreferencesUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class RegisterActivity extends BaseActivity {

    private EditText userloginidEditText;
    private EditText passwordEditText;
    private EditText repeatpasswordEditText;
    private EditText usernameEditText;
    private EditText useremailEditText;
    private EditText phonenumEditText;
    private ProgressBar loadingProgressBar;

    private Button registerButton;

    @SuppressLint("CutPasteId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        userloginidEditText = (EditText) findViewById(R.id.edtTxt_register_user_login_id);
        passwordEditText = (EditText) findViewById(R.id.edtTxt_register_password);
        repeatpasswordEditText = (EditText) findViewById(R.id.edtTxt_register_repeat_password);
        usernameEditText = (EditText) findViewById(R.id.edtTxt_register_username);
        useremailEditText = (EditText) findViewById(R.id.edtTxt_register_email);
        phonenumEditText = (EditText) findViewById(R.id.edtTxt_register_phone_num);
        registerButton = (Button) findViewById(R.id.btn_register_register);

        loadingProgressBar = (ProgressBar) findViewById(R.id.register_loading);


        userloginidEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!userloginidEditText.getText().toString().matches("^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$")) {
                    userloginidEditText.setError("登录账号格式有误！");
                }
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!passwordEditText.getText().toString().matches("^(\\w){8,20}$")) {
                    passwordEditText.setError("密码长度介于8到20位之间，且由字母或数字构成！");
                }
            }
        });

        repeatpasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!repeatpasswordEditText.getText().toString().equals(passwordEditText.getText().toString())) {
                    repeatpasswordEditText.setError("两次密码输入不一致！");
                }
            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (usernameEditText.getText().toString().isEmpty()) {
                    usernameEditText.setError("用户名不能为空！");
                }
            }
        });

        useremailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!useremailEditText.getText().toString().matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$")) {
                    useremailEditText.setError("邮箱格式有误！");
                }
            }
        });

        phonenumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!phonenumEditText.getText().toString().matches("^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$")) {
                    phonenumEditText.setError("手机号码格式有误！");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!phonenumEditText.getText().toString().matches("^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$")) {
                    phonenumEditText.setError("手机号码格式有误！");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userloginidEditText.getText().toString().isEmpty() &&
                        !passwordEditText.getText().toString().isEmpty() &&
                        !repeatpasswordEditText.getText().toString().isEmpty() &&
                        !usernameEditText.getText().toString().isEmpty() &&
                        !useremailEditText.getText().toString().isEmpty() &&
                        !phonenumEditText.getText().toString().isEmpty() &&
                        userloginidEditText.getError() == null &&
                        passwordEditText.getError() == null &&
                        repeatpasswordEditText.getError() == null &&
                        usernameEditText.getError() == null &&
                        useremailEditText.getError() == null &&
                        phonenumEditText.getError() == null) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    User user = new User(userloginidEditText.getText().toString(), MD5Util.getMD5Str(passwordEditText.getText().toString()),
                            usernameEditText.getText().toString(), useremailEditText.getText().toString(),
                            phonenumEditText.getText().toString());

                    boolean register = UserRegister.register(user);
                    if (register) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                        //跳转主页面
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        loadingProgressBar.setVisibility(View.INVISIBLE);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                        dialog.setTitle("警告");
                        dialog.setMessage("注册失败！用户账号已存在！");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                    }

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setTitle("警告");
                    dialog.setMessage("填写信息有误！请检查");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}