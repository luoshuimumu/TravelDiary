package com.example.luoshuimumu.traveldiary.test;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;

import com.example.luoshuimumu.traveldiary.R;

public class ActViewFliper extends ActionBarActivity {
    AdapterViewFlipper viewFlipper;
    SeekBar ratingBar;
    ImageView mImageView;
    float mAlpha = 1;
    int[] imgs = {R.drawable.abc_cab_background_internal_bg, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.ic_launcher, R.drawable.abc_ab_share_pack_holo_dark, R.drawable.abc_btn_radio_to_on_mtrl_015};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_view_fliper);
        viewFlipper = (AdapterViewFlipper) findViewById(R.id.viewFlipper);
        ratingBar = (SeekBar) findViewById(R.id.seekBar);
        ratingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mAlpha =(float) i / seekBar.getMax();
                if (null != mImageView) {
                    mImageView.setAlpha(mAlpha);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public Object getItem(int i) {
                return imgs[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ImageView imageView = new ImageView(ActViewFliper.this);
                imageView.setImageResource(imgs[i]);
                imageView.setAlpha(mAlpha);
                mImageView = imageView;
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }


        };
        viewFlipper.setAdapter(adapter);
    }

    public void prev(View view) {
        viewFlipper.showPrevious();
        viewFlipper.stopFlipping();
    }

    public void next(View view) {
        viewFlipper.showNext();
        viewFlipper.stopFlipping();
    }

    public void auto(View view) {
        viewFlipper.startFlipping();
    }
}
