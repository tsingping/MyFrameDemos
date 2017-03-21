package com.zlw.myhuanxindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zlw.myhuanxindemo.utils.EMManager;
import com.zlw.myhuanxindemo.utils.Logger;

public class MainActivity extends AppCompatActivity {
    EditText et_username, et_password;
    Button bt_login, bt_register;

    EMManager emManager;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        emManager = EMManager.getIns();
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emManager.loginAsync(et_username.getText().toString(), et_password.getText().toString(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Logger.d(TAG, "登录成功！");
                        ControlActivity.startMe(MainActivity.this);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Logger.d(TAG, "登录失败！");
                    }

                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        emManager.register(et_username.getText().toString(), et_password.getText().toString());
                    }
                }.start();
            }
        });
    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
    }

}
