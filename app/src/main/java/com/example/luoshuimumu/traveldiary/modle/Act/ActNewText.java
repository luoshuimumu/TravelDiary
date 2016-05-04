package com.example.luoshuimumu.traveldiary.modle.Act;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.baidu.location.BDLocation;
import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.utils.TimeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ActNewText extends AbsActNewMedia {
    Spinner spinner;
    EditText et_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_new_text);

        spinner = (Spinner) findViewById(R.id.act_text_spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item
                , new String[]{"心情", "遇见", "购物", "饮食", "住宿"}));

        et_content = (EditText) findViewById(R.id.act_text_et_content);

        findViewById(R.id.act_text_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //写入文件
                writeTxt();
                complete(ActCreate.RESULT_CODE_NEW_TEXT);
            }
        });

    }

    /**
     * 获取spinner 获取et
     */
    private void writeTxt() {
        initTextPath();
        File file = new File(mPath);
        try {
            OutputStream os = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(os);

            osw.write(spinner.getSelectedItem().toString());
            osw.write("\n");

            osw.write(et_content.getText().toString());
            //写入完毕关闭
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initTextPath() {
        String outputPath = ActNewText.this.getExternalFilesDir(null).toString();
        mTime = TimeUtils.getTime();
        mPath = outputPath + "/text" + mTime + ".txt";
        mUri = Uri.parse(mPath);


    }


}
