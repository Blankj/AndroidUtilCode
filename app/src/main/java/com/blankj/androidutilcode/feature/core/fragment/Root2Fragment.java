package com.blankj.androidutilcode.feature.core.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseFragment;
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
public class Root2Fragment extends BaseFragment
        implements FragmentUtils.OnBackClickListener {

    ImageView ivSharedElement;
    TextView  tvAboutFragment;

    public static Root2Fragment newInstance() {
        Bundle args = new Bundle();
        Root2Fragment fragment = new Root2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_root;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        Random random = new Random();
        FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        view.findViewById(R.id.btn_show_about_fragment).setOnClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_add_hide).setOnClickListener(this);
        view.findViewById(R.id.btn_add_hide_stack).setOnClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_add_show).setOnClickListener(this);
        view.findViewById(R.id.btn_add_child).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_to_root).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_add).setOnClickListener(this);
        view.findViewById(R.id.btn_hide_show).setOnClickListener(this);
        view.findViewById(R.id.btn_replace).setOnClickListener(this);
        ivSharedElement = (ImageView) view.findViewById(R.id.iv_shared_element);
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
            case R.id.btn_add:
                FragmentUtils.add(getFragmentManager(),
                        ChildFragment.newInstance(),
                        R.id.fragment_container);
                break;
            case R.id.btn_add_hide:
                FragmentUtils.add(getFragmentManager(),
                        ChildFragment.newInstance(),
                        R.id.fragment_container,
                        true);
                break;
            case R.id.btn_add_hide_stack:
                FragmentUtils.add(getFragmentManager(),
                        ChildFragment.newInstance(),
                        R.id.fragment_container,
                        true,
                        true);
                break;
//            case R.id.btn_add_show:
//                FragmentUtils.add(getFragmentManager(),
//                        addSharedElement(Demo1Fragment.newInstance()),
//                        R.id.fragment_container,
//                        false,
//                        false,
//                        sharedElement);
//                break;
//            case R.id.btn_add_child:
//                FragmentUtils.add(getChildFragmentManager(),
//                        ChildFragment.newInstance(),
//                        R.id.child_fragment_container,
//                        false,
//                        true);
//                break;
//            case R.id.btn_pop_to_root:
//                FragmentUtils.popToFragment(getFragmentManager(),
//                        Demo1Fragment.class,
//                        true);
//                break;
//            case R.id.btn_pop_add:
//                FragmentUtils.popAddFragment(getFragmentManager(),
//                        addSharedElement(ChildFragment.newInstance()),
//                        R.id.fragment_container,
//                        true,
//                        sharedElement);
//                break;
//            case R.id.btn_hide_show:
//                Fragment fragment1 = FragmentUtils.findFragment(getFragmentManager(), Demo1Fragment.class);
//                if (fragment1 != null) {
//                    FragmentUtils.showHideFragment(this, fragment1);
//                } else {
//                    ToastUtils.showLong("please add demo1 first!");
//                }
//                break;
//            case R.id.btn_replace:
//                ((FragmentActivity) getActivity()).rootFragment = FragmentUtils.replaceFragment(this, addSharedElement(Demo3Fragment.newInstance()), false, sharedElement);
//                break;
        }
    }

    private Fragment addSharedElement(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new DetailTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailTransition());
        }
        return fragment;
    }

    @Override
    public boolean onBackClick() {
//        FragmentUtils.popToFragment(getFragmentManager(), Demo1Fragment.class, true);
        return false;
    }
}
