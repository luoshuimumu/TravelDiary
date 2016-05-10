package com.example.luoshuimumu.traveldiary.modle.Act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.luoshuimumu.traveldiary.LocationApplication;
import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.modle.DB.MediaEntity;
import com.example.luoshuimumu.traveldiary.modle.Map.BDLocationSingleton;
import com.example.luoshuimumu.traveldiary.modle.frag.AbsFragxxxList;
import com.example.luoshuimumu.traveldiary.modle.frag.FragListAudio;
import com.example.luoshuimumu.traveldiary.modle.frag.FragListPic;
import com.example.luoshuimumu.traveldiary.modle.frag.FragListText;
import com.example.luoshuimumu.traveldiary.modle.frag.FragListTrace;
import com.example.luoshuimumu.traveldiary.modle.frag.FragListVideo;
import com.example.luoshuimumu.traveldiary.modle.frag.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
// TODO: 2016/5/9 ActNewMedia返回后Fragment的mDataList未更新 故页面也未更新
// TODO: 2016/5/9 Fragment 状态保存未完成 编辑模式下checkbox勾选 左右滑动后消失

public class ActCreate extends ActionBarActivity implements AbsFragxxxList.OnFragmentInteractionListener {
    public static boolean FLAG_EDIT_MODE = true;
//    //持有五个fragment的编辑状态缓存列表,在新建fragment时传输过去
//    private Set<MediaEntity> mTextChoosedList = new TreeSet<>();
//    private Set<MediaEntity> mPicChoosedList = new TreeSet<>();
//    private Set<MediaEntity> mAudioChoosedList = new TreeSet<>();
//    private Set<MediaEntity> mVideoChoosedList = new TreeSet<>();
//    private Set<MediaEntity> mTraceChoosedList = new TreeSet<>();

    private Set<MediaEntity> mMediaChoosedList = new TreeSet<>();
    public static final String EDIT_MODE_ADD = "add";
    public static final String EDIT_MODE_DELETE = "delete";

    @Override
    public void onFragmentInteraction(MediaEntity entity, String mode, String type) {
        if (mode.equals("add")) {
            mMediaChoosedList.add(entity);
        } else if (mode.equals("delete")) {
            mMediaChoosedList.remove(entity);
        }
//        switch (type){
//            case MediaEntity.TYPE_TEXT:
//                    if(mode.equals("add")){
//                        mTextChoosedList.add(entity);
//                    }else {
//                        mTextChoosedList.remove(entity);
//                    }
//                break;
//            case MediaEntity.TYPE_PIC:
//                if(mode.equals("add")){
//                    mTextChoosedList.add(entity);
//                }else {
//                    mTextChoosedList.remove(entity);
//                }
//                break;
//            case MediaEntity.TYPE_AUDIO:
//
//                break;
//            case MediaEntity.TYPE_VIDEO:
//
//                break;
//            case MediaEntity.TYPE_TRACE:
//
//                break;
//        }
    }

//    /**
//     * 将加过来的entity加入mTotalChoosedList
//     *
//     * @param entity
//     */
//    @Override
//    public void onFragmentInteraction(MediaEntity entity, String mode, String frag) {
//        if (frag.equals(FragListText.class.getSimpleName())) {
//            //更新fragment列表数据
//            if (mode.equals("add"))
//                mTextChoosedList.add(entity);
//            else if (mode.equals("delete"))
//                mTextChoosedList.remove(entity);
//        } else if (frag.equals(FragListPic.class.getSimpleName())) {
//            //更新fragment列表数据
//            if (mode.equals("add"))
//                mPicChoosedList.add(entity);
//            else if (mode.equals("delete"))
//                mPicChoosedList.remove(entity);
//        } else if (frag.equals(FragListAudio.class.getSimpleName())) {
//            //更新fragment列表数据
//            if (mode.equals("add"))
//                mAudioChoosedList.add(entity);
//            else if (mode.equals("delete"))
//                mAudioChoosedList.remove(entity);
//        } else if (frag.equals(FragListVideo.class.getSimpleName())) {
//            //更新fragment列表数据
//            if (mode.equals("add"))
//                mVideoChoosedList.add(entity);
//            else if (mode.equals("delete"))
//                mVideoChoosedList.remove(entity);
//        } else if (frag.equals(FragListTrace.class.getSimpleName())) {
//            //更新fragment列表数据
//            if (mode.equals("add"))
//                mTraceChoosedList.add(entity);
//            else if (mode.equals("delete"))
//                mTraceChoosedList.remove(entity);
//        }
//
//
//    }

    public static final int REQUEST_CODE_NEW_MEDIA = 1990;

    public static final int RESULT_CODE_NEW_PIC = 1993;
    public static final int RESULT_CODE_NEW_TEXT = 1994;
    public static final int RESULT_CODE_NEW_AUDIO = 1995;
    public static final int RESULT_CODE_NEW_VIDEO = 1996;
    public static final int RESULT_CODE_NEW_TRACE = 1997;


    //数据库单例
    SQLiteOpenHelper mSqliteSingleton;
    FragmentManager mFragmentManager;
    AbsFragxxxList fragDiaryList;
    AbsFragxxxList fragMapList;


    //地图模块
//    BDLocationListener mBDListener = null;

    //内置五个fragment 分别展示不同类型媒体信息的列表
    ViewPager mViewPager;
    ViewPagerAdapter mPagerAdapter;
    ArrayList<AbsFragxxxList> mFragList;

    //怎么初始化
    //初始化需要从数据库加载数据

    AbsFragxxxList mFragText;
    AbsFragxxxList mFragPic;
    AbsFragxxxList mFragAudio;
    AbsFragxxxList mFragVideo;
    AbsFragxxxList mFragTrace;

    //五个切换按钮 五个切换图片 在切换到不同fragment时切换
    private ImageView iv_frag_text;
    private ImageView iv_frag_audio;
    private ImageView iv_frag_pic;
    private ImageView iv_frag_video;
    private ImageView iv_frag_trace;

    TextView tv_frag_text;
    TextView tv_frag_audio;
    TextView tv_frag_pic;
    TextView tv_frag_video;
    TextView tv_frag_trace;

    LinearLayout ll_frag_text;
    LinearLayout ll_frag_pic;
    LinearLayout ll_frag_audio;
    LinearLayout ll_frag_video;
    LinearLayout ll_frag_trace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_act_create);

//        if (LocationApplication.dbHelper.getWritableDatabase() == null) {
//            Toast.makeText(ActCreate.this, "db failed", Toast.LENGTH_SHORT).show();
//        } else {
//
//            Toast.makeText(ActCreate.this, LocationApplication.dbHelper.getWritableDatabase().toString(), Toast.LENGTH_SHORT).show();
//
//        }

        BDLocationSingleton.create(getApplicationContext());

//        BDLocationSingleton.setListener(getmBDListener());
        mFragmentManager = getSupportFragmentManager();
        initView();

        //需要设置fragmentList和viewPager的关系
        initViewPager();

    }


    /**
     * 查询数据库获取多媒体条目
     */
    private void initView() {
        initViewWidget();

    }

    private void initViewWidget() {
        mViewPager = (ViewPager) findViewById(R.id.content);

        iv_frag_text = (ImageView) findViewById(R.id.act_create_iv_frag_text);
        iv_frag_audio = (ImageView) findViewById(R.id.act_create_iv_frag_audio);
        iv_frag_pic = (ImageView) findViewById(R.id.act_create_iv_frag_pic);
        iv_frag_video = (ImageView) findViewById(R.id.act_create_iv_frag_video);
        iv_frag_trace = (ImageView) findViewById(R.id.act_create_iv_frag_trace);
        tv_frag_text = (TextView) findViewById(R.id.act_create_tv_frag_text);
        tv_frag_audio = (TextView) findViewById(R.id.act_create_tv_frag_audio);
        tv_frag_pic = (TextView) findViewById(R.id.act_create_tv_frag_pic);
        tv_frag_video = (TextView) findViewById(R.id.act_create_tv_frag_video);
        tv_frag_trace = (TextView) findViewById(R.id.act_create_tv_frag_trace);

        FragChangeClickedListener fragChangeClickedListener = new FragChangeClickedListener();
        ll_frag_text = (LinearLayout) findViewById(R.id.act_create_ll_frag_text);
        ll_frag_pic = (LinearLayout) findViewById(R.id.act_create_ll_frag_pic);
        ll_frag_audio = (LinearLayout) findViewById(R.id.act_create_ll_frag_audio);
        ll_frag_video = (LinearLayout) findViewById(R.id.act_create_ll_frag_video);
        ll_frag_trace = (LinearLayout) findViewById(R.id.act_create_ll_frag_trace);
        ll_frag_text.setOnClickListener(fragChangeClickedListener);
        ll_frag_pic.setOnClickListener(fragChangeClickedListener);
        ll_frag_audio.setOnClickListener(fragChangeClickedListener);
        ll_frag_video.setOnClickListener(fragChangeClickedListener);
        ll_frag_trace.setOnClickListener(fragChangeClickedListener);
    }

    private class FragChangeClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.act_create_ll_frag_text: {
                    mViewPager.setCurrentItem(0);
                    setTabSelection(0);
                    break;
                }
                case R.id.act_create_ll_frag_pic: {
                    mViewPager.setCurrentItem(1);
                    setTabSelection(1);
                    break;
                }
                case R.id.act_create_ll_frag_audio: {
                    mViewPager.setCurrentItem(2);
                    setTabSelection(2);
                    break;
                }
                case R.id.act_create_ll_frag_video: {
                    mViewPager.setCurrentItem(3);
                    setTabSelection(3);
                    break;
                }
                case R.id.act_create_ll_frag_trace: {
                    mViewPager.setCurrentItem(4);
                    setTabSelection(4);
                    break;
                }
                default:
                    break;
            }


        }
    }

    /**
     * 只用于改变 frag切换按钮的图片和颜色
     *
     * @param index 不同fragment的编号
     */
    private void setTabSelection(int index) {
        clearTabSelection();
        switch (index) {
            case 0:
                tv_frag_text.setTextColor(Color.BLUE);
                break;
            case 1:
                tv_frag_pic.setTextColor(Color.BLUE);
                break;
            case 2:
                tv_frag_audio.setTextColor(Color.BLUE);
                break;
            case 3:
                tv_frag_video.setTextColor(Color.BLUE);
                break;
            case 4:
                tv_frag_trace.setTextColor(Color.BLUE);
                break;
            default:
                break;
        }

    }

    /**
     * 将所有fragment切换按钮的图片和文字颜色切换为初始状态
     */
    private void clearTabSelection() {
        tv_frag_text.setTextColor(Color.BLACK);
        tv_frag_audio.setTextColor(Color.BLACK);
        tv_frag_pic.setTextColor(Color.BLACK);
        tv_frag_video.setTextColor(Color.BLACK);
        tv_frag_trace.setTextColor(Color.BLACK);
    }

    /**
     * 需要设置fragmentList和viewPager的关系
     * <p/>
     * 这里初始化frag
     */

    private void initViewPager() {
        mFragList = new ArrayList<>();
        mFragText = FragListText.newInstance(MediaEntity.TYPE_TEXT, FLAG_EDIT_MODE);
        mFragPic = FragListPic.newInstance(MediaEntity.TYPE_PIC, FLAG_EDIT_MODE);
        mFragAudio = FragListAudio.newInstance(MediaEntity.TYPE_AUDIO, FLAG_EDIT_MODE);
        mFragVideo = FragListVideo.newInstance(MediaEntity.TYPE_VIDEO, FLAG_EDIT_MODE);
        mFragTrace = FragListTrace.newInstance(MediaEntity.TYPE_TRACE, FLAG_EDIT_MODE);
        mFragList.add(mFragText);
        mFragList.add(mFragPic);
        mFragList.add(mFragAudio);
        mFragList.add(mFragVideo);
        mFragList.add(mFragTrace);

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragList);
        mViewPager.setAdapter(mPagerAdapter);
        setTabSelection(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
     * @param requestCode 本类常量
     * @param resultCode  本类常量
     * @param data        选取几个关键入库参数 date path name等 还有识别媒体类型的标记
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NEW_MEDIA) {
            //入库 更新ui
            switch (resultCode) {
                case RESULT_CODE_NEW_PIC:
                    data.putExtra("type", MediaEntity.TYPE_PIC);
                    insertPic(data);
                    break;
                case RESULT_CODE_NEW_TEXT:
                    data.putExtra("type", MediaEntity.TYPE_TEXT);
                    insertText(data);
                    break;
                case RESULT_CODE_NEW_AUDIO:
                    data.putExtra("type", MediaEntity.TYPE_AUDIO);
                    insertAudio(data);
                    break;
                case RESULT_CODE_NEW_VIDEO:
                    data.putExtra("type", MediaEntity.TYPE_VIDEO);
                    insertVideo(data);
                    break;
                case RESULT_CODE_NEW_TRACE:
                    data.putExtra("type", MediaEntity.TYPE_TRACE);
                    insertTrace(data);
                    break;
                default:
                    break;
            }
            //没有新插入数据时不更新
            //更新列表 每个列表在不同的fragment中 应该指定更新其中一个fragment

        }


    }

    private void onNewPicClicked() {

        Intent i = new Intent(ActCreate.this, CameraActivity.class);
//        Intent i = new Intent(ActCreate.this, ActNewPic.class);
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
     * @return 是否插入成功的标记(未使用)
     */
    private boolean insertPic(Intent data) {

        insertData(data);
        refreshFragDataList(data);
        refreshUI();
        return true;
    }

    private boolean insertText(Intent data) {

        insertData(data);
        refreshFragDataList(data);
        refreshUI();
        return true;
    }

    private boolean insertAudio(Intent data) {

        insertData(data);
        refreshFragDataList(data);
        refreshUI();
        return true;
    }

    private boolean insertVideo(Intent data) {

        insertData(data);
        refreshFragDataList(data);
        refreshUI();
        return true;
    }

    private boolean insertTrace(Intent data) {

        insertData(data);
        refreshFragDataList(data);
        refreshUI();
        return true;
    }


    /**
     * 刷新方法 应查询数据库 更新listview的数据
     */
    private void refreshUI() {
        if (mPagerAdapter != null) mPagerAdapter.notifyDataSetChanged();

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
//                ((AbsFragxxxList) fragment).refreshLocation();
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
        //这里有可能Location是空值
        BDLocation location = intent.getParcelableExtra("location");
        String locationStr = "null";
        if (location.getPoiList() != null) {
            locationStr = location.getPoiList().get(0).getName();
        }

        //插入语句
//        create table " + "media" + " (_id integer" +
//        "primary key autoincrement,type varchar(255)," +
//                "date varchar(255),location varchar(255),uri varchar(255)," +
//                "path varchar(255));";

        LocationApplication.dbHelper.getWritableDatabase().execSQL(
                "insert into media (type,date,location,uri,path) values(\"" +
                        type + "\",\"" + time + "\",\"" + locationStr + "\",\""
                        + uri + "\",\"" + path + "\");"
        );
        LocationApplication.dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_media:
                onNewMediaClick();
                return true;
            case R.id.action_edit_mode:


                if (FLAG_EDIT_MODE) {
                    //这里需要清除所有的列表
                    FLAG_EDIT_MODE = false;
                    mMediaChoosedList = new TreeSet<>();
                    //清空fragment的缓存列表


                    Iterator iterator = mFragUIHandlerList.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Message msg = new Message();
                        msg.what = AbsFragxxxList.MSG_CLEAR_DATA;
                        Map.Entry entity = (Map.Entry) iterator.next();
//                        String key = (String) entity.getKey();
                        Handler handler = (Handler) entity.getValue();
                        handler.sendMessage(msg);
                    }
                } else {
                    FLAG_EDIT_MODE = true;
                }
                refreshUI();

                return true;
            case R.id.action_create_diary:
                StringBuffer sb = new StringBuffer();
                Iterator iterator = mMediaChoosedList.iterator();
                while ((iterator.hasNext())) {
                    sb.append(((MediaEntity) iterator.next()).getId() + ",");
                }
                Log.e("mMediaChoosedList", sb.toString());

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 关闭数据库
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (LocationApplication.dbHelper != null) {
            LocationApplication.dbHelper.close();
        }
    }

    Map<String, Handler> mFragUIHandlerList = new HashMap<>();

    public void registHandler(String type, Handler handler) {
        if (mFragUIHandlerList == null) mFragUIHandlerList = new HashMap<>();
        mFragUIHandlerList.put(type, handler);
    }

    // TODO: 2016/5/9 type是在insert函数里面加的 这里没有
    private void refreshFragDataList(Intent data) {
        Message msg = new Message();
        msg.what = AbsFragxxxList.MSG_REFRESH_DATA;
        Bundle bundle = new Bundle();
        String type = data.getStringExtra("type");
        bundle.putString("type", type);
        bundle.putString("time", data.getStringExtra("time"));
        msg.setData(bundle);

        Handler handler =
                mFragUIHandlerList.get(type);
        handler.sendMessage(msg);
    }
}
