package com.example.luoshuimumu.traveldiary.modle.Act;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.modle.Map.BDLocationSingleton;

import java.io.File;

public abstract class AbsActNewMedia extends ActionBarActivity {
    //公有方法 异步获取定位信息
    protected BDLocation mLocation = null;
    //日期date 从TimeUtils获取
    String mTime;
    //每次启动act只创建一个媒体资源 所以有唯一的资源uri
    Uri mUri;
    String mPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_act_new_media2);
    }


    private void getPosition() {
        //开一个异步任务
        new AsyncGetPositonTask().execute();

    }

    private class AsyncGetPositonTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //若地图单例处于未完成的状态
                while (false == BDLocationSingleton.isFlagComplete()) {
                    Thread.sleep(500);
                }
                //获取当前位置
                mLocation = BDLocationSingleton.getPosition();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    protected void setMediaParams(String time, String path) {
        this.mTime = time;
        this.mUri = Uri.fromFile(new File(mPath));
        this.mPath = path;
    }

    /**
     * 完成新媒体的录制 设置数据并返回
     */
    protected void complete(int resultCode) {
        setMediaParams(mTime, mPath);

        mLocation = new BDLocation();
        //判断是否有未赋值的参数
        if (mTime == null || mTime.equals("") || mUri == null || mUri.equals("") || mPath == null || mPath.equals("") || mLocation == null) {
            Toast.makeText(getApplicationContext(), "params less when create new media", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("time", mTime);
        intent.putExtra("uri", mUri.toString());
        intent.putExtra("path", mPath);
        //应该还有定位信息
        Bundle bundle = new Bundle();
        bundle.putParcelable("location", mLocation);
        intent.putExtras(bundle);
        setResult(resultCode, intent);
        finishActivity(ActCreate.REQUEST_CODE_NEW_MEDIA);
    }
}
