package com.hznu.transactionofidlegoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hznu.transactionofidlegoods.bottomnavigation.BottonNavigationActivity;
import com.hznu.transactionofidlegoods.login.ui.login.LoginActivity;
import com.hznu.transactionofidlegoods.utils.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_heyMan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hey Man!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, BottonNavigationActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请登录！", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}