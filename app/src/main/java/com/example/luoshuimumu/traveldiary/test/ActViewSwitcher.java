package com.example.luoshuimumu.traveldiary.test;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.luoshuimumu.traveldiary.R;

import java.util.ArrayList;

public class ActViewSwitcher extends ActionBarActivity {
    private static final int NUMBER_PER_SCREEN = 12;

    private static class DataItem {
        public String dateItem;
        public Drawable drawable;
    }

    private ArrayList<DataItem> items = new ArrayList<>();
    private int mScreenNo = -1;
    private int mScreenCount;
    ViewSwitcher switcher;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_view_switcher);
        inflater = LayoutInflater.from(ActViewSwitcher.this);
        for (int i = 0; i < 40; i++) {
            String label = "app-" + i;
            Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
            DataItem item = new DataItem();
            item.dateItem = label;
            item.drawable = drawable;
            items.add(item);
        }

        mScreenCount = items.size() / NUMBER_PER_SCREEN;
        if (items.size() % NUMBER_PER_SCREEN > 0) mScreenCount++;

        switcher = (ViewSwitcher) findViewById(R.id.switcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return inflater.inflate(R.layout.widget_slidelistview, null);
            }
        });
        next(null);

    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            if (mScreenNo == mScreenCount - 1 && items.size() % NUMBER_PER_SCREEN != 0) {
                return items.size() % NUMBER_PER_SCREEN;

            }
            return NUMBER_PER_SCREEN;
        }

        @Override
        public DataItem getItem(int i) {
            return items.get(mScreenNo * NUMBER_PER_SCREEN + i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            View view = v;
            if (null == view) {
                view = inflater.inflate(R.layout.widget_viewswitcher_iconlabel, null);
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(getItem(i).dateItem);
            imageView.setImageDrawable(getItem(i).drawable);

            return view;
        }
    };

    public void next(View v) {
        if (mScreenNo < mScreenCount - 1) {
            mScreenNo++;
            switcher.setInAnimation(this, R.anim.slide_in_right);
            switcher.setOutAnimation(this, R.anim.slide_out_left);
            ((GridView) switcher.getNextView()).setAdapter(adapter);
            switcher.showNext();
        }
    }

    public void prev(View v) {
        if (mScreenNo > 0) {
            mScreenNo--;
            switcher.setInAnimation(this, R.anim.slide_in_right);
            switcher.setOutAnimation(this, R.anim.slide_out_left);
            ((GridView) switcher.getNextView()).setAdapter(adapter);
            switcher.showPrevious();
        }
    }


}

