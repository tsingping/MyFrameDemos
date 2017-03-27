package com.zlw.myjpushdemo;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;

    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_show);
        /**
         * 定制推送反馈
         */
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MainActivity.this);
        builder.statusBarDrawable = R.drawable.jpush_notification_icon;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);

        /**
         * Notification样式
         */
        MultiActionsNotificationBuilder multibuilder = new MultiActionsNotificationBuilder(MainActivity.this);
        //添加按钮，参数(按钮图片、按钮文字、扩展数据)
        multibuilder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        multibuilder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        multibuilder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(2, builder);
/**
 * Notification高级样式
 */
//        CustomPushNotificationBuilder custombuilder = new
//                CustomPushNotificationBuilder(MainActivity.this,
//                R.layout.customer_notitfication_layout,
//                R.id.icon,
//                R.id.title,
//                R.id.text);
//        // 指定定制的 Notification Layout
//        builder.statusBarDrawable = R.drawable.your_notification_icon;
//        // 指定最顶层状态栏小图标
//        builder.layoutIconDrawable = R.drawable.your_2_notification_icon;
//        // 指定下拉状态栏时显示的通知图标
//        JPushInterface.setPushNotificationBuilder(2, builder);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    //TODO;EVENT-bus
    private void showText(String msg) {
        tv_show.setText(msg);
    }

}
