package com.blankj.androidutilcode.feature.core.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : Bar 工具类 Demo
 * </pre>
 */
public class BarStatusFragmentActivity extends BaseActivity {

    private int[] itemIds = new int[]{
            R.id.navigation_color,
            R.id.navigation_alpha,
            R.id.navigation_image_view
    };

    private ViewPager            mVpStatusBar;
    private BottomNavigationView navigation;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BarStatusFragmentActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar_status_fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        mVpStatusBar = findViewById(R.id.vp_status_bar);
        navigation = findViewById(R.id.navigation_status_bar);

        mFragmentList.add(BarStatusColorFragment.newInstance());
        mFragmentList.add(BarStatusAlphaFragment.newInstance());
        mFragmentList.add(BarStatusImageViewFragment.newInstance());


        mVpStatusBar.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        mVpStatusBar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(itemIds[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_color:
                    mVpStatusBar.setCurrentItem(0);
                    return true;
                case R.id.navigation_alpha:
                    mVpStatusBar.setCurrentItem(1);
                    return true;
                case R.id.navigation_image_view:
                    mVpStatusBar.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
}
