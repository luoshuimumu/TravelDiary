package com.example.luoshuimumu.traveldiary.model.Map;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by luoshuimumu on 2016/4/18.
 */
public class BDLocationSingleton {
    //需要一个更新标记，activity通过比对自身类名与此标记来判定是否要获取定位信息
    private static boolean FLAG_COMPLETE = false;

    static private LocationClient client = null;
    static private BDLocation mBDCurrentLocation = null;

    private static BDLocationSingleton singleton;

    {
        if (singleton == null)
            singleton = new BDLocationSingleton();
    }

    private BDLocationSingleton() {
    }

    public static BDLocationSingleton getSingleton() {
        return singleton;
    }

    public static void create(Context context) {
        client = new LocationClient(context);
        setDefaultListener();
        //配置默认的定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度
        option.setCoorType("bd0911");//返回的定位结果坐标系
        option.setScanSpan(3000);//定位间隔
        option.setIsNeedAddress(true);//需要返回地址信息
        option.setIsNeedLocationDescribe(true);//需要返回语义化结果 "在天安门附近"
        option.setOpenGps(true);
        option.setLocationNotify(true);//当gps有效时每秒一次输出gps结果
        option.setIsNeedLocationPoiList(true);//需要poi结果 getPoiList取得
        option.setIgnoreKillProcess(false);//默认不杀死进程
        option.SetIgnoreCacheException(false);//不手机crash信息
        option.setEnableSimulateGps(true);
        client.setLocOption(option);


    }

    /**
     * 修改create的默认配置
     *
     * @param option
     */
    public static void setOption(LocationClientOption option) {
        client.setLocOption(option);
    }

    /**
     * 修改create的默认监听器
     *
     * @param listener
     */
    public static void setListener(BDLocationListener listener) {
        client.registerLocationListener(listener);
    }

    public static void setDefaultListener() {
        client.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                FLAG_COMPLETE = true;
                mBDCurrentLocation = bdLocation;
            }
        });
    }

    /**
     * 开始定位
     */
    public static void startPosition() {
        FLAG_COMPLETE = false;
        client.start();
    }

    /**
     * 获取定位结果 定位结果会自动回调
     *
     * @return 可以是一串String
     */
    public static BDLocation getPosition() {
        return mBDCurrentLocation;
    }

    public static boolean isFlagComplete() {
        return FLAG_COMPLETE;
    }
}
