package com.blankj.androidutilcode.feature.core.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseFragment;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  :
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
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_child;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        Random random = new Random();
        FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        view.findViewById(R.id.btn_show_about_fragment).setOnClickListener(this);
        view.findViewById(R.id.btn_pop).setOnClickListener(this);
        tvAboutFragment = (TextView) view.findViewById(R.id.tv_about_fragment);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        tvAboutFragment.setText("");
        switch (view.getId()) {
            case R.id.btn_show_about_fragment:
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
                break;
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
