package com.zlw.mylocationdemo;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 高德地图
 */
public class LocationManager {
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;


    private static LocationManager ins;

    public static LocationManager getIns() {

        if (ins == null) {
            ins = new LocationManager();
        }
        return ins;
    }

    public LocationManager build(Context context, AMapLocationListener mLocationListener) {
        if (mLocationListener == null) {
            return this;
        }
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        return this;
    }

    public void start() {
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

}
