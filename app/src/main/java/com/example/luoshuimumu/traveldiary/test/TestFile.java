package com.example.luoshuimumu.traveldiary.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.RandomAccessFile;

public class TestFile extends ActionBarActivity {
    String mFilePath = "lsmmDiary.txt";
    String mStoragePath = "/lsmmDiary.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_file);
        if (hasStorage() == false) {
            return;
        }
        write("lsmm");
        Toast.makeText(TestFile.this, read(), Toast.LENGTH_SHORT).show();


    }


    //检查是否插入内存卡
    private boolean hasStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    //返回sd卡目录
    private File getSDDir() {
        return Environment.getExternalStorageDirectory();
    }

    //在储存卡中读写数据
    String readSD() {
        try {
            if (hasStorage()) {
                File dir = getSDDir();
                FileInputStream fis = new FileInputStream(dir + mStoragePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                StringBuffer sb = new StringBuffer("");
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();
                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    void writeSD(String content) {
        try {
            if (hasStorage()) {
                File dir = getSDDir();
                File targetFile = new File(dir.getCanonicalPath() + mStoragePath);
                RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
                //移动指针
                raf.seek(targetFile.length());
                raf.write(content.getBytes());
                raf.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //在手机内置储存中读写文件
    String read() {
        try {
            FileInputStream fis = openFileInput(mFilePath);
            byte[] buff = new byte[1024];
            int hasRead = 0;
            StringBuffer sb = new StringBuffer("");
            while ((hasRead = fis.read(buff)) > 0) {
                sb.append(new String(buff, 0, hasRead));
            }
            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void write(String content) {
        try {
            FileOutputStream fos = openFileOutput(mFilePath, MODE_APPEND);
            PrintStream ps = new PrintStream(fos);

            ps.println(content);
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
