package com.blankj.utilcode.pkg.feature.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseFragment;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class ChildFragment extends BaseFragment
        implements FragmentUtils.OnBackClickListener {

    private TextView tvAboutFragment;

    public static ChildFragment newInstance() {
        Bundle args = new Bundle();
        ChildFragment fragment = new ChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_child;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        Random random = new Random();
        FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        findViewById(R.id.btn_show_about_fragment).setOnClickListener(this);
        findViewById(R.id.btn_pop).setOnClickListener(this);
        tvAboutFragment = findViewById(R.id.tv_about_fragment);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        tvAboutFragment.setText("");
        int i = view.getId();
        if (i == R.id.btn_show_about_fragment) {
            tvAboutFragment.setText("top: " + FragmentUtils.getSimpleName(FragmentUtils.getTop(getFragmentManager()))
                    + "\ntopInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopInStack(getFragmentManager()))
                    + "\ntopShow: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShow(getFragmentManager()))
                    + "\ntopShowInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShowInStack(getFragmentManager()))
                    + "\n---all of fragments---\n"
                    + FragmentUtils.getAllFragments(getFragmentManager()).toString()
                    + "\n----------------------\n\n"
                    + "---stack top---\n"
                    + FragmentUtils.getAllFragmentsInStack(getFragmentManager()).toString()
                    + "\n---stack bottom---\n\n"
            );

//            case R.id.btn_pop:
//                FragmentUtils.popFragment(getFragmentManager());
//                break;
        }
    }

    @Override
    public boolean onBackClick() {
        LogUtils.d("demo2 onBackClick");
        return false;
    }
}
