package com.blankj.androidutilcode.feature.core.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.FragmentUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/01
 *     desc  : Fragment 工具类 Demo
 * </pre>
 */
public class FragmentActivity extends BaseActivity {

    private int[] itemIds = new int[]{
            R.id.navigation_fragment_zero,
            R.id.navigation_fragment_one,
            R.id.navigation_fragment_two
    };

    private BottomNavigationView navigation;
    private Fragment[] mFragments = new Fragment[3];
    private int curIndex;

    public static void start(Context context) {
        Intent starter = new Intent(context, FragmentActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex");
        }

        navigation = findViewById(R.id.navigation_fragment);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragments[0] = Root0Fragment.newInstance();
        mFragments[1] = Root1Fragment.newInstance();
        mFragments[2] = Root2Fragment.newInstance();
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fragment_container, curIndex);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }


//    @Override
//    public void onBackPressed() {
////        if (!FragmentUtils.dispatchBackPress(getSupportFragmentManager())) {
////            super.onBackPressed();
////        }
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_fragment_zero:
                    showCurrentFragment(0);
                    return true;
                case R.id.navigation_fragment_one:
                    showCurrentFragment(1);
                    return true;
                case R.id.navigation_fragment_two:
                    showCurrentFragment(2);
                    return true;
            }
            return false;
        }
    };

    private void showCurrentFragment(int index) {
        FragmentUtils.showHide(curIndex = index, mFragments);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("curIndex", curIndex);
    }
}
