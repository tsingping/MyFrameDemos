package com.zlw.myhuanxindemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

public class MsgListActivity extends AppCompatActivity {
    private FragmentManager fm;

    public static void startMe(Context context) {
        context.startActivity(new Intent(context, MsgListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);

        fm = getSupportFragmentManager();
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                EMChatActivity.startMe(MsgListActivity.this, conversation.conversationId());

            }
        });
        fm.beginTransaction().replace(R.id.myfragment, conversationListFragment).commit();
    }
}
