package com.hznu.transactionofidlegoods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.hznu.transactionofidlegoods.login.ui.login.LoginActivity;
import com.hznu.transactionofidlegoods.utils.BaseActivity;
import com.hznu.transactionofidlegoods.utils.SharePreferencesUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Toast.makeText(MainActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}