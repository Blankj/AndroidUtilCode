package com.blankj.androidutilcode.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.activity.FragmentActivity;
import com.blankj.utilcode.util.FragmentUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  :
 * </pre>
 */
public class Demo1Fragment extends Fragment
        implements View.OnClickListener{

    public static Demo1Fragment newInstance() {

        Bundle args = new Bundle();

        Demo1Fragment fragment = new Demo1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView tvAboutFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_show_about_fragment).setOnClickListener(this);
        view.findViewById(R.id.btn_hide_show).setOnClickListener(this);
        tvAboutFragment = (TextView) view.findViewById(R.id.tv_about_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Random random = new Random();
        FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        tvAboutFragment.setText("");
        switch (view.getId()) {
            case R.id.btn_show_about_fragment:
                tvAboutFragment.setText("lastAdd: " + FragmentUtils.getLastAddFragment(getFragmentManager()).getClass().getSimpleName()
                        + "\nlastAddInStack: " + (FragmentUtils.getLastAddFragmentInStack(getFragmentManager()) != null ? FragmentUtils.getLastAddFragmentInStack(getFragmentManager()).getClass().getSimpleName() : "null")
                        + "\ntopShow: " + FragmentUtils.getTopShowFragment(getFragmentManager()).getClass().getSimpleName()
                        + "\ntopShowInStack: " + (FragmentUtils.getTopShowFragmentInStack(getFragmentManager()) != null ? FragmentUtils.getTopShowFragmentInStack(getFragmentManager()).getClass().getSimpleName() : "null")
                        + "\n---all of fragments---\n"
                        + FragmentUtils.getAllFragments(getFragmentManager()).toString()
                        + "\n----------------------\n\n"
                        + "---stack top---\n"
                        + FragmentUtils.getAllFragmentsInStack(getFragmentManager()).toString()
                        + "\n---stack bottom---\n\n"
                );
                break;
            case R.id.btn_hide_show:
                FragmentUtils.hideAllShowFragment(((FragmentActivity) getActivity()).rootFragment);
                break;
        }
    }

//    @Override
//    public boolean onBackClick() {
//        LogUtils.d("onBackClick");
//        FragmentUtils.showFragment(((FragmentActivity) getActivity()).rootFragment);
//        FragmentUtils.popFragment(getFragmentManager());
//        return true;
//    }
}
