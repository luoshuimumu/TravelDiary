package com.example.luoshuimumu.traveldiary.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.baidu.location.BDLocation;
import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.model.DB.MediaSqliteHelper;
import com.example.luoshuimumu.traveldiary.model.Map.BDLocationSingleton;
import com.example.luoshuimumu.traveldiary.model.frag.AbsFragxxxList;

import java.util.List;

public class ActCreate extends ActionBarActivity {
    protected static final int REQUEST_CODE_NEW_MEDIA = 1990;

    protected static final int RESULT_CODE_NEW_PIC = 1993;
    protected static final int RESULT_CODE_NEW_TEXT = 1994;
    protected static final int RESULT_CODE_NEW_AUDIO = 1995;
    protected static final int RESULT_CODE_NEW_VIDEO = 1996;
    protected static final int RESULT_CODE_NEW_TRACE = 1997;


    //数据库单例
    SQLiteOpenHelper mSqliteSingleton;
    FragmentManager mFragmentManager;
    AbsFragxxxList fragDiaryList;
    AbsFragxxxList fragMapList;
    public MediaSqliteHelper dbHelper;

    //地图模块
//    BDLocationListener mBDListener = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_create);
        BDLocationSingleton.create(getApplicationContext());

//        BDLocationSingleton.setListener(getmBDListener());
        mFragmentManager = getSupportFragmentManager();
        dbHelper = new MediaSqliteHelper(getApplicationContext(), MediaSqliteHelper.NAME_MEDIA_SQL, null, 1);

    }


    /**
     * 弹出dialog选择新建的素材种类
     * 日记 文字 照片 音频 视频 轨迹
     */
    public void onNewMediaClick() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("new Media")

                .setItems(new String[]{"pic", "text", "audio", "video", "trace"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        onNewPicClicked();
                                        break;
                                    case 1:
                                        onNewTextClicked();
                                        break;
                                    case 2:
                                        onNewAudioClicked();
                                        break;
                                    case 3:
                                        onNewVideoClicked();
                                        break;
                                    case 4:
                                        onNewTraceClicked();
                                        break;
                                    default:
                                        break;

                                }

                            }
                        }
                );
        builder.create().show();


    }

    /**
     * 供fragment调用的函数
     * 用表明和id从数据库删除数据
     *
     * @param tableName 表名
     * @param _id       主键
     */
    public void delete(String tableName, String _id) {

    }

    /**
     * 主要响应“新建媒体”事件的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data        选取几个关键入库参数 date path name等 还有识别媒体类型的标记
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NEW_MEDIA) {
            //入库 更新ui
            switch (resultCode) {
                case RESULT_CODE_NEW_PIC:
                    insertPic(data);
                    break;
                case RESULT_CODE_NEW_TEXT:
                    insertText(data);
                    break;
                case RESULT_CODE_NEW_AUDIO:
                    insertAudio(data);
                    break;
                case RESULT_CODE_NEW_VIDEO:
                    insertVideo(data);
                    break;
                case RESULT_CODE_NEW_TRACE:
                    insertTrace(data);
                    break;
                default:
                    break;
            }
            refreshUI();
        }


    }

    private void onNewPicClicked() {
        Intent i = new Intent(ActCreate.this, ActNewPic.class);
        Bundle bundle = new Bundle();
        startActivityForResult(i, REQUEST_CODE_NEW_MEDIA, bundle);
    }

    private void onNewTextClicked() {
        Intent i = new Intent(ActCreate.this, ActNewText.class);
        Bundle bundle = new Bundle();
        startActivityForResult(i, REQUEST_CODE_NEW_MEDIA, bundle);
    }

    private void onNewAudioClicked() {
        Intent i = new Intent(ActCreate.this, ActNewAudio.class);
        Bundle bundle = new Bundle();
        startActivityForResult(i, REQUEST_CODE_NEW_MEDIA, bundle);
    }

    private void onNewVideoClicked() {
        Intent i = new Intent(ActCreate.this, ActNewVideo.class);
        Bundle bundle = new Bundle();
        startActivityForResult(i, REQUEST_CODE_NEW_MEDIA, bundle);
    }

    private void onNewTraceClicked() {
        Intent i = new Intent(ActCreate.this, ActNewTrace.class);
        Bundle bundle = new Bundle();
        startActivityForResult(i, REQUEST_CODE_NEW_MEDIA, bundle);
    }

    /**
     * 在这里要调用MediaSqliteHelper.insertMedia(Intent) ,需要在intent参数中
     * 加上type
     * path就是title 可以不用再存一次title
     *
     * @return
     */
    private boolean insertPic(Intent data) {
        data.putExtra("type", "img");
        insertData(data);
        return true;
    }

    private boolean insertText(Intent data) {
        data.putExtra("type", "text");
        insertData(data);
        return true;
    }

    private boolean insertAudio(Intent data) {
        data.putExtra("type", "audio");
        insertData(data);
        return true;
    }

    private boolean insertVideo(Intent data) {
        data.putExtra("type", "video");
        insertData(data);
        return true;
    }

    private boolean insertTrace(Intent data) {
        data.putExtra("type", "trace");
        insertData(data);
        return true;
    }

    /**
     * 刷新方法 应查询数据库 更新listview的数据
     */
    private void refreshUI() {

    }

//    /**
//     * 初始化返回地图结果的监听器
//     *
//     * @return
//     */
//    private BDLocationListener getmBDListener() {
//        if (mBDListener == null) {
//            mBDListener = new BDLocationListener() {
//                @Override
//                public void onReceiveLocation(BDLocation bdLocation) {
////                    refreshLocation(bdLocation);
//                    //更新ui
//                    callFragmentNewLocation();
//                }
//            };
//        }
//        return mBDListener;
//    }

//    /**
//     * 在百度定位回调处使用，更新唯一的地点信息
//     *
//     * @param location
//     */
//    private void refreshLocation(BDLocation location) {
//        mBDCurrentLocation = location;
//    }

    /**
     * 获取当前fragment调用其方法要求更新定位信息 不对。。。。
     */
    private void callFragmentNewLocation() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        List<android.support.v4.app.Fragment> list = mFragmentManager.getFragments();
        for (android.support.v4.app.Fragment fragment : list) {
            if (fragment != null && fragment.isVisible()) {
                ((AbsFragxxxList) fragment).refreshLocation();
            }
        }
    }

//    /**
//     * fragment获取定位结果
//     *
//     * @return
//     */
//    public BDLocation getmBDCurrentLocation() {
//        return mBDCurrentLocation;
//    }

    /**
     * 向数据库插入数据 这个方法似乎不该由它持有？
     *
     * @param intent 此方法只在ActCreate接收到返回结果(带intent)时调用
     */
    public void insertData(Intent intent) {
        String type = intent.getStringExtra("type");
        String time = intent.getStringExtra("time");
        String uri = intent.getStringExtra("uri");
        String path = intent.getStringExtra("path");
        BDLocation location = intent.getParcelableExtra("location");

        //插入语句
//        create table " + "media" + " (_id integer" +
//        "primary key autoincrement,type varchar(255)," +
//                "date varchar(255),location varchar(255),uri varchar(255)," +
//                "path varchar(255));";

        dbHelper.getReadableDatabase().execSQL(
                "insert into media (type,date,location,uri,path) values(" +
                        type + "," + time + "," + location.getPoiList().get(0) + ","
                        + uri + "," + path + ");"
        );
    }

    /**
     * 关闭数据库
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
