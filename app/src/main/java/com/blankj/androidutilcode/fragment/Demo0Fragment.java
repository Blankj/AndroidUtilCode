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
import com.blankj.utilcode.utils.FragmentUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.Random;
import java.util.Stack;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  :
 * </pre>
 */
public class Demo0Fragment extends Fragment
        implements View.OnClickListener {

    private Fragment fragment1;

    public static Demo0Fragment newInstance() {

        Bundle args = new Bundle();

        Demo0Fragment fragment = new Demo0Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView tvAboutFragment;

    Stack<Fragment> stack = new Stack<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo0, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_show_stack).setOnClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_add_child).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_to_root).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_add).setOnClickListener(this);
        view.findViewById(R.id.btn_hide_show).setOnClickListener(this);
        view.findViewById(R.id.btn_replace).setOnClickListener(this);
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
            case R.id.btn_show_stack:
                tvAboutFragment.setText(FragmentUtils.getAllFragments(getFragmentManager()).toString());
                break;
            case R.id.btn_add:
                stack.add(FragmentUtils.addFragment(getFragmentManager(), Demo1Fragment.newInstance(), R.id.fragment_container, true, true));
                break;
            case R.id.btn_add_child:
                FragmentUtils.addFragment(getChildFragmentManager(), Demo2Fragment.newInstance(), R.id.child_fragment_container, false, true);
                break;
            case R.id.btn_pop_to_root:
                FragmentUtils.popToFragment(getFragmentManager(), Demo1Fragment.class, true);
                stack.clear();
                break;
            case R.id.btn_pop_add:
                if (!stack.isEmpty()) {
                    stack.pop();
                }
                FragmentUtils.popAddFragment(getFragmentManager(), R.id.fragment_container, Demo2Fragment.newInstance(), true);
                break;
            case R.id.btn_hide_show:
                if (!stack.isEmpty()) {
                    FragmentUtils.hideShowFragment(this, stack.peek());
                } else {
                    ToastUtils.showLongToast("please add first!");
                }
                break;
            case R.id.btn_replace:
                FragmentUtils.replaceFragment(this, newInstance(), false);
                break;
        }
    }
}
