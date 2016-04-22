package com.example.luoshuimumu.traveldiary.test;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.ViewSwitcher;

import com.example.luoshuimumu.traveldiary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActImageSwitcher extends ActionBarActivity {
    int[] imgs = new int[]{
            R.drawable.ic_launcher, R.drawable.abc_btn_radio_to_on_mtrl_015, R.drawable.abc_cab_background_internal_bg
            , R.drawable.abc_btn_radio_material, R.drawable.abc_ic_go_search_api_mtrl_alpha,
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,};

    ImageSwitcher mImageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_image_switcher);
        final List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", imgs[i]);
            items.add(item);
        }
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(ActImageSwitcher.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.WRAP_CONTENT, ImageSwitcher.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.grid_item, new String[]{"image"}, new int[]{R.id.iv_});
        GridView gridView = (GridView) findViewById(R.id.gridView2);
        gridView.setAdapter(adapter);
        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mImageSwitcher.setImageResource(imgs[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mImageSwitcher.setImageResource(imgs[i]);
            }
        });

    }

}
