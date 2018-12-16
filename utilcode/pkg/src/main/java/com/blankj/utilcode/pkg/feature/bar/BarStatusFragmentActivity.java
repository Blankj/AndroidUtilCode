package com.blankj.utilcode.pkg.feature.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseActivity;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : demo about BarUtils
 * </pre>
 */
public class BarStatusFragmentActivity extends BaseActivity {

    private int[] itemIds = new int[]{
            R.id.navigation_color,
            R.id.navigation_alpha,
            R.id.navigation_image_view,
            R.id.navigation_custom
    };

    private ViewPager            mVpStatusBar;
    private BottomNavigationView navigation;
    private ArrayList<Fragment>  mFragmentList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BarStatusFragmentActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar_status_fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        mVpStatusBar = findViewById(R.id.vp_status_bar);
        navigation = findViewById(R.id.navigation_status_bar);

        mFragmentList.add(BarStatusColorFragment.newInstance());
        mFragmentList.add(BarStatusAlphaFragment.newInstance());
        mFragmentList.add(BarStatusImageViewFragment.newInstance());
        mFragmentList.add(BarStatusCustomFragment.newInstance());

        mVpStatusBar.setOffscreenPageLimit(3);
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
            int i = item.getItemId();
            if (i == R.id.navigation_color) {
                mVpStatusBar.setCurrentItem(0);
                return true;
            } else if (i == R.id.navigation_alpha) {
                mVpStatusBar.setCurrentItem(1);
                return true;
            } else if (i == R.id.navigation_image_view) {
                mVpStatusBar.setCurrentItem(2);
                return true;
            } else if (i == R.id.navigation_custom) {
                mVpStatusBar.setCurrentItem(3);
                return true;
            }
            return false;
        }
    };
}
