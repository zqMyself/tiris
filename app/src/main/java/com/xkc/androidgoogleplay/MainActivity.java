package com.xkc.androidgoogleplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button      btn_login;
    private Button      btn_pay;
    private TextView    tv_user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_main);
        //登陆
        btn_login = findViewById(R.id.btn_login);
        btn_pay = findViewById(R.id.btn_pay);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //支付
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pay();
            }


        });
    }

    private void btn_pay() {
        startActivity(new Intent(this,PayActivity.class));
    }

    private void login() {
        startActivity(new Intent(this,LoginActivity.class));
    }
}
