package com.xkc.androidgoogleplay;

import android.app.Application;
import com.umeng.analytics.game.UMGameAgent;
import com.xck.tirisfirebasesdk.module.config.XCKConfigure;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        //初始化xkc SDK
        new XCKConfigure.Builder()
                .setContext(this)
                .isLog(true)
                //FireBase id 直接默认这个是google-services.json 文件中自动解析出来的
                .setFireBaseRequestIdToken(getString(R.string.default_web_client_id))
                //项目ID 、应用ID 、版本 默认为1.0.0
                .setProjectIdAndAppIdAndVersion("trsdk-200c5","1","1.0.0")
                .build();
    }
}
