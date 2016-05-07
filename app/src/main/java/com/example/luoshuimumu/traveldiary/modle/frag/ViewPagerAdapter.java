package com.example.luoshuimumu.traveldiary.modle.frag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by luoshuimumu on 2016/4/27.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<AbsFragxxxList> list;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<AbsFragxxxList> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public int getItemPosition(Object object) {
        Log.e("getItemPosition", object.getClass().getSimpleName());
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }
}
