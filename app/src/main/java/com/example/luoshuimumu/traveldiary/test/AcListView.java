
package com.example.luoshuimumu.traveldiary.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.example.luoshuimumu.traveldiary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcListView extends ActionBarActivity {
    String[] autoCompleteStr = {"aaa", "bbb", "bcc"};
    MultiAutoCompleteTextView actv = null;

    GridView gridView = null;
    ImageView imageView = null;
    int[] virtualAlbumRes = {R.drawable.item_btn_yes_v4, R.drawable.item_btn_yes_v4, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};

    //    R.drawable.item_btn_yes_v4,
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_list_view);
        //自动完成输入框相关
         /*
        actv = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setCompletionHint("请选择");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.widget_slidelistview.simple_dropdown_item_1line, autoCompleteStr);
        actv.setAdapter(aa);
        //为什么这一句是必要的
        actv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());*/

        gridView = (GridView) findViewById(R.id.gridView);
        List<Map<String, Object>> listItem = new ArrayList<>();

        for (int i = 0; i < virtualAlbumRes.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", virtualAlbumRes[i]);
            listItem.add(item);
        }
        imageView = (ImageView) findViewById(R.id.iamgeView);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItem, R.layout.grid_item, new String[]{
                "image"}, new int[]{R.id.iv_});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imageView.setImageResource(virtualAlbumRes[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imageView.setImageResource(virtualAlbumRes[i]);
            }
        });
    }


}


