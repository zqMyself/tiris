package com.xkc.androidgoogleplay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;
import com.umeng.analytics.game.UMGameAgent;
import com.xck.tirisfirebasesdk.module.login.utils.SelectLoginUtil;

/**
 * 登陆
 */
public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Button      btn_logout;
    private TextView tv_user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_login);
        //登陆
        btn_login = findViewById(R.id.btn_login);
        btn_logout = findViewById(R.id.btn_logout);
        tv_user = findViewById(R.id.tv_user);

       SelectLoginUtil.getInstance()
                .setOnLoginCall(new SelectLoginUtil.onLoginCallListener() {
                    @Override
                    public void success(String userId, String session) {
                        updateUI(userId);
                    }
                    @Override
                    public void error() {
                    }
        });

        login();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //退出
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }


        });
    }

    private void updateUI(String userID) {
        tv_user.setText("userId:" + (userID == null ? "" : userID) );
        btn_login.setVisibility(userID == null ? View.VISIBLE : View.GONE);
    }


    private void logout() {
        SelectLoginUtil.getInstance().logout(this);
    }
    private void login() {
        SelectLoginUtil.getInstance().showSelectLoginTheWay(this);
    }
}
