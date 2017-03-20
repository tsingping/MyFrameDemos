package com.zlw.mymediacontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zlw.mymediacontrol.me.RemoteManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_send, bt_exit, mPlayPause;

    private boolean isplay;
    private RemoteManager mRemoteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mRemoteManager = RemoteManager.getInstance(getApplication(), this);
        mRemoteManager.initCallBack();
        bt_send.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
    }

    private void initView() {
        bt_send = (Button) findViewById(R.id.bt_send);
        bt_exit = (Button) findViewById(R.id.bt_exit);
        mPlayPause = (Button) findViewById(R.id.play_pause);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                mRemoteManager.register_18();
                mRemoteManager.send("music_" + (int) (Math.random() * 10000));
                break;
            case R.id.play_pause:
                isplay = !isplay;
                mRemoteManager.updataState(isplay);
                break;
        }
    }
}
