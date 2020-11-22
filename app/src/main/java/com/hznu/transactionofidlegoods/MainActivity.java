package com.hznu.transactionofidlegoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hznu.transactionofidlegoods.bottomnavigation.BottonNavigationActivity;

public class MainActivity extends AppCompatActivity {

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
    }
}