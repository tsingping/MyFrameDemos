package com.zlw.myhuanxindemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.zlw.myhuanxindemo.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EMFriendActivity extends AppCompatActivity {
    private static final String TAG = EMFriendActivity.class.getSimpleName();
    private FragmentManager fm;


    public static void startMe(Context context) {
        context.startActivity(new Intent(context, EMFriendActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em);
        fm = getSupportFragmentManager();
        EaseContactListFragment contactListFragment = new EaseContactListFragment();
        //需要设置联系人列表才能启动fragment

        contactListFragment.setContactsMap(getContacts());
        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                EMChatActivity.startMe(EMFriendActivity.this, user.getUsername());
            }
        });
        fm.beginTransaction().replace(R.id.myfragment, contactListFragment).commit();

    }

    /**
     * prepared users, password is "123456"
     * you can use these user to test
     *
     * @return
     */
    private Map<String, EaseUser> getContacts() {
        List<String> users = null;
        Map<String, EaseUser> contacts = new HashMap<String, EaseUser>();
        try {
            users = EMClient.getInstance().contactManager().getAllContactsFromServer();
            Logger.i(TAG, "user size: " + users.size());
            for (int i = 1; i <= users.size(); i++) {
                EaseUser user = new EaseUser(users.get(i));
                contacts.put(users.get(i), user);
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
            Logger.printStackTrace(e);
        }
        return contacts;
    }
}
