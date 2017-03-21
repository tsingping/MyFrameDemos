package com.zlw.myhuanxindemo.base;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.zlw.myhuanxindemo.utils.EMManager;

/**
 * Created by zlw on 2017/3/20.
 */

public class MyApp extends Application {
    private static Context instance;
    Context appContext;

    public static Context getIns() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        EMManager.getIns().init(this);
        EMOptions options = new EMOptions();
        EaseUI.getInstance().init(this, options);
    }


}
