package com.example.luoshuimumu.traveldiary.model;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.constant.Constant;
import com.example.luoshuimumu.traveldiary.utils.TimeUtils;

import java.io.File;

public class ActNewPic extends AbsActNewMedia {
    private final int REQUEST_CODE_CAPTURE_CAMERA = 2005;
    //需要储存的参数

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getImageFromCamera();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAPTURE_CAMERA) {
            if (resultCode == RESULT_OK) {
                //这里获取uri和路径并存入数据库
//                setMediaParams(mTime, mPath);
                //通知调用者ActCreate更新UI列表
                complete(ActCreate.RESULT_CODE_NEW_PIC);

            }
        }
    }

    private void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            //生成文件储存路径
            String outputPath = Constant.DEFAULT_IMAGE_PATH;
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //获取时间生成完整路径

            mTime = TimeUtils.getTime();
            mPath = outputPath + "/img" + mTime + ".jpg";
            mUri = Uri.fromFile(new File(mPath));

            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMERA);

        } else {
            Toast.makeText(getApplicationContext(), "please ensure sdCard exists!", Toast.LENGTH_SHORT).show();
        }


    }

}
//    int screenHight, screenWidth;
//    SurfaceView surfaceView;
//    SurfaceHolder holder;
//    boolean isPreview;
//    Camera camera;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_act_new_pic);
//
//        WindowManager wm = getWindowManager();
//        Display display = wm.getDefaultDisplay();
//        DisplayMetrics metris = new DisplayMetrics();
//        //获取屏幕尺寸
//        display.getMetrics(metris);
//        screenHight = metris.heightPixels;
//        screenWidth = metris.widthPixels;
//
//        //获取界面surface组件
//        surfaceView = (SurfaceView) findViewById(R.id.surfaveView);
//        //设置surfaceView不需要自己维护缓冲区
//        holder = surfaceView.getHolder();
//        //添加回调
//        holder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder surfaceHolder) {
//                Toast.makeText(ActNewPic.this, "open camera", Toast.LENGTH_SHORT).show();
//                initCamera();
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//
//            }
//        });
//
//
//    }
//
//    private void initCamera() {
//        //默认打开后置摄像头
//        if (!isPreview) {
//            camera = Camera.open(0);
//            camera.setDisplayOrientation(90);
//        }
//        if (camera != null && !isPreview) {
//            try {
//
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        }
//
//
//    }
//
//}
