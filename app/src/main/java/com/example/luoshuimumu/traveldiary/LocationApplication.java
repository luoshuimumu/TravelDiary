package com.example.luoshuimumu.traveldiary;


import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.example.luoshuimumu.traveldiary.model.DB.MediaSqliteHelper;
import com.example.luoshuimumu.traveldiary.service.LocationService;

/**
 * ��Application�����аٶȶ�λSDK�Ľӿ�˵����ο������ĵ���http://developer.baidu.com/map/loc_refer/index.html
 * <p/>
 * �ٶȶ�λSDK�ٷ���վ��http://developer.baidu.com/map/index.php?title=android-locsdk
 * <p/>
 * ֱ�ӿ���com.baidu.location.service�����Լ��Ĺ����£������ü��ɻ�ȡ��λ�����Ҳ���Ը���demo�������з�װ
 */
public class LocationApplication extends Application {
    public LocationService locationService;
    public Vibrator mVibrator;
    public static MediaSqliteHelper dbHelper;

    public static MediaSqliteHelper getDbHelper() {

        return dbHelper;
    }

    public void initSQLite() {
        dbHelper = new MediaSqliteHelper(getApplicationContext(), MediaSqliteHelper.NAME_MEDIA_SQL, null, 1);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * ��ʼ����λsdk��������Application�д���
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

        initSQLite();

    }


}
