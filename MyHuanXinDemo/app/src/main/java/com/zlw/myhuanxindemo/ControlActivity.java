package com.zlw.myhuanxindemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zlw.myhuanxindemo.utils.EMManager;

public class ControlActivity extends AppCompatActivity {


    Button bt_contact_list, bt_chat, bt_conversation_list, bt_loginout;

    public static void startMe(Context context) {
        context.startActivity(new Intent(context, ControlActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        bt_contact_list = (Button) findViewById(R.id.bt_contact_list);
        bt_chat = (Button) findViewById(R.id.bt_chat);
        bt_conversation_list = (Button) findViewById(R.id.bt_conversation_list);
        bt_loginout = (Button) findViewById(R.id.bt_loginout);

        bt_contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMFriendActivity.startMe(ControlActivity.this);
            }
        });
        bt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMChatActivity.startMe(ControlActivity.this, "zlw123");

            }
        });
        bt_conversation_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgListActivity.startMe(ControlActivity.this);

            }
        });
        bt_loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMManager.getIns().loginout();
                finish();
            }
        });
    }
}
