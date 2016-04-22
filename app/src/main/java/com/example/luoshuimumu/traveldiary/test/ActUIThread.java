package com.example.luoshuimumu.traveldiary.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.R;

import java.util.ArrayList;
import java.util.List;


public class ActUIThread extends Activity {

    static final String UPPER_NUN = "upper";

    class NextThread extends Thread {

        public Handler mHandler;

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x123) {
                        int upper = msg.getData().getInt(UPPER_NUN);
                        List<Integer> numbers = new ArrayList<>();
                        outer:
                        for (int i = 2; i < upper; i++) {
                            for (int j = 2; j <=Math.sqrt(i); j++) {
                                if (i != 2 && i % j == 0) {
                                    continue outer;
                                }

                            }
                            numbers.add(i);
                        }
                        Toast.makeText(ActUIThread.this, numbers.toString(), Toast.LENGTH_SHORT).show();

                    }
                    super.handleMessage(msg);
                }
            };
            Looper.loop();

        }
    }

    EditText et;
    NextThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_uithread);

        et = (EditText) findViewById(R.id.et);

        mThread = new NextThread();
        mThread.start();

    }

    public void next(View v) {
        Message msg = new Message();
        msg.what = 0x123;
        Bundle bundle = new Bundle();
        bundle.putInt(UPPER_NUN, Integer.parseInt(et.getText().toString()));
        msg.setData(bundle);

        mThread.mHandler.sendMessage(msg);


    }

}
