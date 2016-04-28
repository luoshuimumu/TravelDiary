package com.example.luoshuimumu.traveldiary.test;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.model.frag.AbsFragxxxList;
import com.example.luoshuimumu.traveldiary.test.frag.BlankFragment1;
import com.example.luoshuimumu.traveldiary.test.frag.BlankFragment2;
import com.example.luoshuimumu.traveldiary.test.frag.BlankFragment3;

import java.util.ArrayList;
import java.util.List;

public class ActTestViewPager extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, BlankFragment1.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener, BlankFragment3.OnFragmentInteractionListener {


    private Fragment homeFragment = new BlankFragment1();
    private Fragment settingFragment = new BlankFragment2();
    private Fragment moreFragment = new BlankFragment3();

    private View homeLayout;
    private View settingLayout;
    private View moreLayout;

    private ImageView homeImage;
    private ImageView settingImage;
    private ImageView moreImage;

    private TextView homeText;
    private TextView settingText;
    private TextView moreText;


    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_act_test_view_pager);

        initViews();

        final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(homeFragment);
        fragmentList.add(settingFragment);
        fragmentList.add(moreFragment);

        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragmentList));

        viewPager.setOnPageChangeListener(this);
        setTabSelection(0);
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.content);
        homeLayout = findViewById(R.id.home_layout);
        settingLayout = findViewById(R.id.setting_layout);
        moreLayout = findViewById(R.id.more_layout);

        homeImage = (ImageView) findViewById(R.id.home_image);
        settingImage = (ImageView) findViewById(R.id.setting_image);
        moreImage = (ImageView) findViewById(R.id.more_image);

        homeText = (TextView) findViewById(R.id.home_text);
        settingText = (TextView) findViewById(R.id.setting_text);
        moreText = (TextView) findViewById(R.id.more_text);

        homeLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        moreLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_layout:
                setTabSelection(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.setting_layout:
                setTabSelection(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.more_layout:
                setTabSelection(2);
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        homeImage.setImageResource(R.drawable.ic_launcher);
        homeText.setTextColor(Color.parseColor("#82858b"));
        settingImage.setImageResource(R.drawable.ic_launcher);
        settingText.setTextColor(Color.parseColor("#82858b"));
        moreImage.setImageResource(R.drawable.ic_launcher);
        moreText.setTextColor(Color.parseColor("#82858b"));
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        setTabSelection(arg0);
    }

    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();

        switch (index) {
            case 0:
                // 当点击了主页tab时，改变控件的图片和文字颜色
                homeImage.setImageResource(R.drawable.ic_launcher);
                homeText.setTextColor(Color.BLUE);
                break;
            case 1:
                // 当点击了语言设置tab时，改变控件的图片和文字颜色
                settingImage.setImageResource(R.drawable.ic_launcher);
                settingText.setTextColor(Color.BLUE);
                break;
            case 2:
                // 当点击了更多tab时，改变控件的图片和文字颜色
                moreImage.setImageResource(R.drawable.ic_launcher);
                moreText.setTextColor(Color.BLUE);
                break;
            case 3:
            default:
                break;
        }
    }

    private class TabPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> list;

        public TabPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
