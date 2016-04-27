package com.example.luoshuimumu.traveldiary.model.frag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by luoshuimumu on 2016/4/27.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> list;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
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
}
