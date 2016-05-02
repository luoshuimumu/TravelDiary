package com.example.luoshuimumu.traveldiary.modle.Act;


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
