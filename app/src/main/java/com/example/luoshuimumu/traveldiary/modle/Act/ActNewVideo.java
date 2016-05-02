package com.example.luoshuimumu.traveldiary.modle.Act;

import android.app.ActionBar;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.constant.Constant;
import com.example.luoshuimumu.traveldiary.utils.TimeUtils;

import java.io.IOException;

/**
 * 需要摄像头权限
 */
public class ActNewVideo extends AbsActNewMedia implements View.OnClickListener {
    Button btn_record, btn_stop, btn_play, btn_save, btn_pause;
    SurfaceView mRecordView, mPlayView;

    MediaRecorder mRecorder;
    MediaPlayer mPlayer;
    //记录当前视频播放位置
    int playPosition;


    boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_new_video);

        btn_record = (Button) findViewById(R.id.act_video_btn_record);
        btn_stop = (Button) findViewById(R.id.act_video_btn_stop);
        btn_play = (Button) findViewById(R.id.act_video_btn_play);
        btn_save = (Button) findViewById(R.id.act_video_btn_save);
        btn_pause = (Button) findViewById(R.id.act_video_btn_pause);

        btn_record.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_pause.setOnClickListener(this);

        //录制模块
        initRecordModel();

        //播放模块
        initPlayModel();
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.act_video_btn_record: {
                    record();
                    break;
                }
                case R.id.act_video_btn_stop: {
                    stopVideo();
                    break;
                }
                case R.id.act_video_btn_play: {
                    playVideo();//可能抛异常
                    break;
                }
                case R.id.act_video_btn_save: {
                    complete(ActCreate.RESULT_CODE_NEW_VIDEO);
                    break;
                }
                case R.id.act_video_btn_pause: {
                    pauseVideo();
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void record() {
        try {
            mTime = TimeUtils.getTime();
            mPath = Constant.DEFAULT_VIDEO_PATH + "/video" + mTime + ".mp4";
            mRecorder = new MediaRecorder();
            mRecorder.reset();

            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            //先设置格式再设置参数
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
            mRecorder.setVideoSize(320, 280);
            //每秒4帧
            mRecorder.setVideoFrameRate(4);
            //设置输出路径
            mRecorder.setOutputFile(mPath);
            //使用surfaceView预览视频
            mRecorder.setPreviewDisplay(mRecordView.getHolder().getSurface());

            mRecorder.prepare();
            mRecorder.start();

            Toast.makeText(getApplicationContext(), "recording", Toast.LENGTH_SHORT).show();
            btn_record.setEnabled(false);
            btn_stop.setEnabled(true);

            isRecording = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopVideo() {
        if (isRecording) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;

            btn_record.setEnabled(true);
            btn_stop.setEnabled(false);
        }
    }

    private void initRecordModel() {
        mRecordView = (SurfaceView) findViewById(R.id.act_video_surfaceView_record);
        mRecordView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //分辨率
        mRecordView.getHolder().setFixedSize(320, 280);
        mRecordView.getHolder().setKeepScreenOn(true);
    }

    private void initPlayModel() {
        mPlayView = (SurfaceView) findViewById(R.id.act_video_surfaceView_play);
        mPlayView.getHolder().setKeepScreenOn(true);
        mPlayView.getHolder().addCallback(new PlaySurfaceListener());
    }

    private class PlaySurfaceListener implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (playPosition > 0) {
                try {
                    playVideo();
                    //直接从指定位置播放
                    mPlayer.seekTo(playPosition);
                    playPosition = 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        }
    }

    private void playVideo() throws IOException {
        mPlayer.reset();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //设置需要播放的视频
        mPlayer.setDataSource(mPath);
        //视频输出到surfaceView
        mPlayer.setDisplay(mPlayView.getHolder());
        mPlayer.prepare();
        //获取窗口管理器
        WindowManager wm = getWindowManager();
        DisplayMetrics metris = new DisplayMetrics();
        //获取屏幕大小
        wm.getDefaultDisplay().getMetrics(metris);
        //设置视频保持纵横比缩放占满整个屏幕
        mPlayView.setLayoutParams(new ActionBar.LayoutParams(metris.widthPixels, mPlayer.getVideoHeight() * metris.widthPixels / mPlayer.getVideoWidth()));
        mPlayer.start();

    }

    private void pauseVideo() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
        btn_play.setVisibility(View.VISIBLE);
        btn_play.setEnabled(true);
    }

    @Override
    protected void onPause() {
        if (mPlayer.isPlaying()) {
            playPosition = mPlayer.getCurrentPosition();
            mPlayer.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mRecorder.release();
        mPlayer.release();

        super.onDestroy();
    }
}
