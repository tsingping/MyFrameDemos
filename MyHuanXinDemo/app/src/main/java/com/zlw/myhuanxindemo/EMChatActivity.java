package com.zlw.myhuanxindemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.zlw.myhuanxindemo.utils.Logger;

import java.io.Serializable;

public class EMChatActivity extends AppCompatActivity {

    private static final String TAG = EMChatActivity.class.getSimpleName();
    private FragmentManager fm;
    String toChatUsername;

    public static void startMe(Context context, String username) {
        context.startActivity(new Intent(context, EMChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, username));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emchat);
        fm = getSupportFragmentManager();
        Serializable serializable = getIntent().getSerializableExtra(EaseConstant.EXTRA_USER_ID);
        toChatUsername = (String) serializable;

        Logger.i(TAG, "username:" + toChatUsername);
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        if (TextUtils.isEmpty(toChatUsername)) {
            args.putString(EaseConstant.EXTRA_USER_ID, "zlw");
        } else {
            args.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        }

        chatFragment.setArguments(args);

        fm.beginTransaction().replace(R.id.myfragment, chatFragment).commit();


    }


    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
}
