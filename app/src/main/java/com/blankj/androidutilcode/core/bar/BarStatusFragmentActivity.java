package com.blankj.androidutilcode.core.bar;

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
import com.blankj.androidutilcode.core.fragment.BarStatusAlphaFragment;
import com.blankj.androidutilcode.core.fragment.BarStatusColorFragment;
import com.blankj.androidutilcode.core.fragment.BarStatusImageViewFragment;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : Bar工具类Demo
 * </pre>
 */
public class BarStatusFragmentActivity extends BaseActivity {

    private int[] itemIds = new int[]{R.id.navigation_color, R.id.navigation_alpha, R.id.navigation_image_view};

    private ViewPager            mVpHome;
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
        mVpHome = (ViewPager) findViewById(R.id.vp_home);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        mFragmentList.add(BarStatusColorFragment.newInstance());
        mFragmentList.add(BarStatusAlphaFragment.newInstance());
        mFragmentList.add(BarStatusImageViewFragment.newInstance());


        mVpHome.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        mVpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    public void doBusiness(Context context) {

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
                    mVpHome.setCurrentItem(0);
                    return true;
                case R.id.navigation_alpha:
                    mVpHome.setCurrentItem(1);
                    return true;
                case R.id.navigation_image_view:
                    mVpHome.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
}
