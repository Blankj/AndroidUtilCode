package com.blankj.androidutilcode.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.fragment.Demo0Fragment;
import com.blankj.utilcode.utils.FragmentUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/01
 *     desc  :
 * </pre>
 */
public class FragmentActivity extends AppCompatActivity {

    public Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        fragment = FragmentUtils.addFragment(getSupportFragmentManager(), Demo0Fragment.newInstance(), R.id.fragment_container);
    }
}
