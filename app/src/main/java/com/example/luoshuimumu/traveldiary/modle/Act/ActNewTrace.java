package com.example.luoshuimumu.traveldiary.modle.Act;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.luoshuimumu.traveldiary.LocationApplication;
import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.service.LocationService;
import com.example.luoshuimumu.traveldiary.utils.TimeUtils;
import com.example.luoshuimumu.traveldiary.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

/**
 * 此类一旦打开即开始定位及绘制服务
 * <p/>
 * 需要增加一个定时器 定时停止该服务
 */
public class ActNewTrace extends AbsActNewMedia {
    final String BTN_STOP = "stop";
    final String BTN_SAVE = "save";


    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private LocationService mLocService;
    //百度的结果集
    private LinkedList<LocationEntity> bdLocationList = new LinkedList<>();

    //我的结果集
    private LinkedList<BDLocation> mLocationLists = new LinkedList<>();


    /**
     * 在完成前调用 将文件写入txt
     */
    private void writeLocationTxt() {
        initTracePath();
        File file = new File(mPath);
        try {
            OutputStream os = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            //循环写入mLocationLists
            for (BDLocation location : mLocationLists) {
                osw.write(String.valueOf(location.getAltitude()));
                osw.write(" ");
                osw.write(String.valueOf(location.getLatitude()));
                osw.write("\n");
            }
            //写入完毕关闭
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void initTracePath() {
        String outputPath = ActNewTrace.this.getExternalFilesDir(null).toString();
        mTime = TimeUtils.getTime();
        mPath = outputPath + "/trace" + mTime + ".txt";
        mUri = Uri.parse(mPath);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_new_trace);
        mMapView = (MapView) findViewById(R.id.act_trace_mapview);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));

        mLocService = ((LocationApplication) getApplicationContext()).locationService;
        LocationClientOption option = mLocService.getDefaultLocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setCoorType("bd0911");
        mLocService.setLocationOption(option);
        mLocService.registerListener(mListener);
        startBDService();

        findViewById(R.id.act_trace_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (((Button) v).getText().toString()) {
                    case BTN_STOP:
                        stopBDService();
                        ((Button) v).setText(BTN_SAVE);
                        break;
                    case BTN_SAVE:
                        writeLocationTxt();
                        complete(ActCreate.RESULT_CODE_NEW_TRACE);
                        break;
                    default:
                        break;


                }
            }
        });

    }

    private void startBDService() {
        mLocService.start();
    }

    private void stopBDService() {
        mLocService.stop();
    }

    /**
     * 定位结果回调，在这里需要保存新的位置信息，画出新标记
     */
    BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null && (bdLocation.getLocType() == 161 || bdLocation.getLocType() == 66)) {
                Message locMsg = new Message();
                Bundle locData;
                locData = Algorithm(bdLocation);
                //一定保存数据 但不一定绘图


                //当前activity在前端时绘图？？？或者从数据库加载坐标点绘图
                if (locData != null) {
                    locData.putParcelable("loc", bdLocation);
                    locMsg.setData(locData);
                    mLocHandler.sendMessage(locMsg);
                }
            }
        }
    };

    /**
     * 调用数据库模块插入数据
     * 先存在内存中 没达到五十个点向文件写入数据 在该类完成后向sqlite插入一条数据 标记位置
     * position的经纬度都是long类型
     *
     * @param location
     */
    private void insertTraceData(BDLocation location) {

    }

    /**
     * 接收定位结果消息并在地图上画出
     */
    private Handler mLocHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                BDLocation location = msg.getData().getParcelable("loc");
                int iscal = msg.getData().getInt("iscalculate");
                if (location != null) {
                    //这个locaiton就是要保存的数据
                    //将其保存在内存中，在activity结束时写入文件
                    mLocationLists.add(location);

                    //这里取得经纬度
                    LatLng point = new LatLng(location.getLatitude(), location.getLongitude());


                    //构建图标
                    BitmapDescriptor bitmap = null;
                    if (iscal == 0) {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_marker_cal);//非推算结果
                    } else {
                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_marker_uncal);//非推算结果

                    }

                    //如果当前地图在显示，则构建MarkerOption，用于在地图上添加Marker
//                    if (mMapView.isFocused()) {
                    OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                    mBaiduMap.addOverlay(option);
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));

//                    }
                }


            } catch (Exception e) {
                //handle exception

            }


        }
    };


    /***
     * 平滑策略代码实现方法，主要通过对新定位和历史定位结果进行速度评分，
     * 来判断新定位结果的抖动幅度，如果超过经验值，则判定为过大抖动，进行平滑处理,若速度过快，
     * 则推测有可能是由于运动速度本身造成的，则不进行低速平滑处理 ╭(●｀∀´●)╯
     *
     * @param location
     * @return
     */
    private Bundle Algorithm(BDLocation location) {
        Bundle locData = new Bundle();
        double curSpeed = 0;
        if (bdLocationList.isEmpty() || bdLocationList.size() < 2) {
            LocationEntity temp = new LocationEntity();
            temp.location = location;
            temp.time = System.currentTimeMillis();
            locData.putInt("iscalculate", 0);
            bdLocationList.add(temp);
        } else {
            if (bdLocationList.size() > 5)
                bdLocationList.removeFirst();
            double score = 0;
            for (int i = 0; i < bdLocationList.size(); ++i) {
                LatLng lastPoint = new LatLng(bdLocationList.get(i).location.getLatitude(),
                        bdLocationList.get(i).location.getLongitude());
                LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
                double distance = DistanceUtil.getDistance(lastPoint, curPoint);
                curSpeed = distance / (System.currentTimeMillis() - bdLocationList.get(i).time) / 1000;
                score += curSpeed * Utils.EARTH_WEIGHT[i];
            }
            if (score > 0.00000999 && score < 0.00005) { // 经验值,开发者可根据业务自行调整，也可以不使用这种算法
                location.setLongitude(
                        (bdLocationList.get(bdLocationList.size() - 1).location.getLongitude() + location.getLongitude())
                                / 2);
                location.setLatitude(
                        (bdLocationList.get(bdLocationList.size() - 1).location.getLatitude() + location.getLatitude())
                                / 2);
                locData.putInt("iscalculate", 1);
            } else {
                locData.putInt("iscalculate", 0);
            }
            LocationEntity newLocation = new LocationEntity();
            newLocation.location = location;
            newLocation.time = System.currentTimeMillis();
            bdLocationList.add(newLocation);

        }
        return locData;
    }


    class LocationEntity {
        BDLocation location;
        long time;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocService.unregisterListener(mListener);
        mLocService.stop();
        mMapView.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
