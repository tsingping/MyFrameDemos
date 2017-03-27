package com.zlw.myhuanxindemo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zlw on 2017/3/20.
 */

public class EMManager {

    private static final String TAG = EMManager.class.getSimpleName();
    private static volatile EMManager inst = null;
    private Map<String, EaseUser> contactList;

    private EMManager() {
    }


    public static EMManager getIns() {
        if (inst == null) {
            synchronized (EMManager.class) {
                if (inst == null) {
                    inst = new EMManager();
                }
            }
        }
        return inst;
    }


    public void init(Context appContext) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(true);
        //初始化
        EMClient.getInstance().init(appContext, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(appContext, pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Logger.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
    }

    public void loginAsync(String username, String password) {
        EMClient.getInstance().login(username, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Logger.d(TAG, "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Logger.d(TAG, "登录聊天服务器失败！");
            }
        });
    }

    public void loginAsync(String username, String password, EMCallBack emCallBack) {
        EMClient.getInstance().login(username, password, emCallBack);
    }

    public void loginout() {
        EMClient.getInstance().logout(true);
    }

    public void loginoutAsync() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }

    public void register(String username, String password) {
        try {
            EMClient.getInstance().createAccount(username, password);//同步方法
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public void registerAsync(String username, String password) {
        try {
            EMClient.getInstance().createAccount(username, password);//同步方法
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    private String getAppName(Context appContext, int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Logger.d(TAG, "Error>> :" + e.toString());
            }
        }
        return processName;
    }


}
