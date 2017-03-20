package com.zlw.mylocationdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    Button bt_show;
    TextView show_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_text = (TextView) findViewById(R.id.show_text);
        bt_show = (Button) findViewById(R.id.bt_show);
        initPermission();


        bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = LocationManager.getIns();
                locationManager.build(MainActivity.this.getApplicationContext(), new AMapLocationListener() {

                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        showMsg(aMapLocation.getAddress());
                    }
                }).start();

            }
        });
    }


    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initPermission() {
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                        } else {
                        }
                    }
                });
    }
}
