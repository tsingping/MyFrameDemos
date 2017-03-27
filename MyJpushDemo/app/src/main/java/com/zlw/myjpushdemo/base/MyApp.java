package com.zlw.myjpushdemo.base;

import android.app.Application;
import android.app.Notification;
import android.content.Context;

import com.zlw.myjpushdemo.MainActivity;
import com.zlw.myjpushdemo.R;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by zlw on 2017/2/23.
 */

public class MyApp extends Application {
    private static Context instance;

    public static Context getIns() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

}
