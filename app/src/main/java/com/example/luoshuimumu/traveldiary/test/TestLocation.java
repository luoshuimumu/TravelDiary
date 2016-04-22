package com.example.luoshuimumu.traveldiary.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.luoshuimumu.traveldiary.R;

public class TestLocation extends ActionBarActivity {
    public LocationClient mLocationClient = null;
    public BDLocationListener mListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_location);
        mLocationClient = new LocationClient(getApplicationContext());
        if (mListener != null)
            mLocationClient.registerLocationListener(mListener);
    }

}
