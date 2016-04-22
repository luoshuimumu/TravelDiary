package com.example.luoshuimumu.traveldiary.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestPicFromIntent extends ActionBarActivity {
    private String capturePath = null;
    private final int REQUEST_CODE_PICK_IMAGE = 1993;
    private final int REQUEST_CODE_CAPTURE_CAMERA = 1994;

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        //扫描sd卡状态
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMERA);
        } else {
            Toast.makeText(TestPicFromIntent.this, "no sdCard", Toast.LENGTH_SHORT).show();
        }
    }

    //从相机得到未压缩图片的版本
    protected void getImageFromCameraNoCompress() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            String outFilePath = "";//一个常量
            File dir = new File(outFilePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            capturePath = "常量" + System.currentTimeMillis() + ".jpg";
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMERA);

        } else {
            Toast.makeText(TestPicFromIntent.this, "no sdCard", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pic_from_intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_PICK_IMAGE == requestCode) {
            Uri uri = data.getData();
        } else if (REQUEST_CODE_CAPTURE_CAMERA == requestCode) {
            Uri uri = data.getData();
            //由于机型不同 有时候会取不到uri
            if (null == uri) {
                Bundle bundle = data.getExtras();
                if (null != bundle) {
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    //此处可以生成图片
                    saveImage(bitmap, "");
                } else {
                    Toast.makeText(TestPicFromIntent.this, "未取到照片", Toast.LENGTH_SHORT).show();

                }
            } else {
                //find path by uri
            }
        }


    }

    private void saveImage(Bitmap bitmap, String path) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path, false));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
