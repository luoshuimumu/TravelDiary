package com.example.luoshuimumu.traveldiary.test;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.example.luoshuimumu.traveldiary.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AcTestAlbum extends ActionBarActivity {
    private static final String TAG = "AcTestAlbum";
    public static final String IMAGE_TYPE = "image/*";
    public static int IMAGE_CODE = 10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_test_album);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(IMAGE_TYPE);
        startActivityForResult(intent, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "ResultCode Error");
            return;
        }
        Bitmap bitmap = null;
        ContentResolver resolver = getContentResolver();

        if (IMAGE_CODE == requestCode) {
            try {
                Uri originalUri = data.getData();//从返回的数据中获得图片uri
                bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                //获取图片路径
                String[] proj={MediaStore.Images.Media.DATA};
                //尝试的方法
                Cursor cursor=resolver.query(originalUri,proj,null,null,null);




            }catch(FileNotFoundException e){

            }
            catch(IOException e){

            }
        }

    }
}
