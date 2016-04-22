package com.example.luoshuimumu.traveldiary.model;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.constant.Constant;
import com.example.luoshuimumu.traveldiary.utils.TimeUtils;

import java.io.File;
import java.io.IOException;

/**
 * 需要添加录音权限
 */
public class ActNewAudio extends AbsActNewMedia {

    Button btn_record, btn_stop;
    File soundFile;
    MediaRecorder mRecorder;
    MediaPlayer mPlayer;
    //方便试听 每次只允许保存一条录音


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_new_audio);
        btn_record = (Button) findViewById(R.id.act_audio_btn_record);
        btn_stop = (Button) findViewById(R.id.act_audio_btn_stop);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecord();
                //录音结束后应该支持试听然后允许保存到数据库
            }
        });


    }

    /**
     * 录音
     */
    private void record() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                //若当前在播放声音则停止
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                }

//                1. 设置路径
//                2. 设置格式
//                3. 设置参数
//                4. 开始
//                5. 保存
                mTime = TimeUtils.getTime();
                mPath
                        = Constant.DEFAULT_AUDIO_PATH + "/audio" + mTime + ".amr";

                soundFile = new File(mPath);
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource((MediaRecorder.AudioSource.MIC));
                //必须先设置格式再设置编码
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                mRecorder.setOutputFile(soundFile.getAbsolutePath());
                mRecorder.prepare();
                mRecorder.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "please ensure sdCard exists!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 停止录音
     */
    private void stopRecord() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    /**
     * 停止播放录音
     */
    private void stopPlay() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * 确认保存当前的录音
     */
    private void save() {
        //应该再传回一个bundle
        setMediaParams(mTime, mPath);
        finishActivity(RESULT_OK);

    }

    /**
     * 播放当前已录制的录音
     */
    private void play() {
        if (mPath == null) return;
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mPath);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {

        //停止播放与录音
        stopPlay();

        stopRecord();
        super.onStop();
    }
}
