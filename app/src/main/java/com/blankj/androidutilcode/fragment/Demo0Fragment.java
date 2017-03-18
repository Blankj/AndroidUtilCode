package com.blankj.androidutilcode.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.activity.FragmentActivity;
import com.blankj.androidutilcode.transition.DetailTransition;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  :
 * </pre>
 */
public class Demo0Fragment extends Fragment
        implements View.OnClickListener, FragmentUtils.OnBackClickListener {

    private Demo0Fragment               demo0Fragment;
    private FragmentUtils.SharedElement sharedElement;

    public static Demo0Fragment newInstance() {

        Bundle args = new Bundle();

        Demo0Fragment fragment = new Demo0Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Button    btnShowAboutFragment;
    private ImageView ivSharedElement;
    private TextView  tvAboutFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo0, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnShowAboutFragment = (Button) view.findViewById(R.id.btn_show_about_fragment);
        btnShowAboutFragment.setOnClickListener(this);
        view.findViewById(R.id.btn_add_hide).setOnClickListener(this);
        view.findViewById(R.id.btn_add_show).setOnClickListener(this);
        view.findViewById(R.id.btn_add_child).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_to_root).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_add).setOnClickListener(this);
        view.findViewById(R.id.btn_hide_show).setOnClickListener(this);
        view.findViewById(R.id.btn_replace).setOnClickListener(this);
        ivSharedElement = (ImageView) view.findViewById(R.id.iv_shared_element);
        tvAboutFragment = (TextView) view.findViewById(R.id.tv_about_fragment);
        demo0Fragment = this;
        sharedElement = new FragmentUtils.SharedElement(ivSharedElement, getString(R.string.fragment_transition));
        setExitTransition(new Fade());
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
                        + "\ntopShow: " + (FragmentUtils.getTopShowFragment(getFragmentManager()) != null ? FragmentUtils.getTopShowFragment(getFragmentManager()).getClass().getSimpleName() : "null")
                        + "\ntopShowInStack: " + (FragmentUtils.getTopShowFragmentInStack(getFragmentManager()) != null ? FragmentUtils.getTopShowFragmentInStack(getFragmentManager()).getClass().getSimpleName() : "null")
                        + "\n---all of fragments---\n"
                        + FragmentUtils.getAllFragments(getFragmentManager()).toString()
                        + "\n----------------------\n\n"
                        + "---stack top---\n"
                        + FragmentUtils.getAllFragmentsInStack(getFragmentManager()).toString()
                        + "\n---stack bottom---\n\n"
                );
                break;
            case R.id.btn_add_hide:
                FragmentUtils.hideAddFragment(getFragmentManager(),
                        demo0Fragment,
                        addSharedElement(Demo1Fragment.newInstance()),
                        R.id.fragment_container,
                        false,
                        true,
                        sharedElement);
                break;
            case R.id.btn_add_show:
                FragmentUtils.addFragment(getFragmentManager(),
                        addSharedElement(Demo1Fragment.newInstance()),
                        R.id.fragment_container,
                        false,
                        false,
                        sharedElement);
                break;
            case R.id.btn_add_child:
                FragmentUtils.addFragment(getChildFragmentManager(),
                        Demo2Fragment.newInstance(),
                        R.id.child_fragment_container,
                        false,
                        true);
                break;
            case R.id.btn_pop_to_root:
                FragmentUtils.popToFragment(getFragmentManager(),
                        Demo1Fragment.class,
                        true);
                break;
            case R.id.btn_pop_add:
                FragmentUtils.popAddFragment(getFragmentManager(),
                        addSharedElement(Demo2Fragment.newInstance()),
                        R.id.fragment_container,
                        true,
                        sharedElement);
                break;
            case R.id.btn_hide_show:
                Fragment fragment1 = FragmentUtils.findFragment(getFragmentManager(), Demo1Fragment.class);
                if (fragment1 != null) {
                    FragmentUtils.hideShowFragment(this, fragment1);
                } else {
                    ToastUtils.showLongToast("please add demo1 first!");
                }
                break;
            case R.id.btn_replace:
                ((FragmentActivity) getActivity()).rootFragment = FragmentUtils.replaceFragment(this, addSharedElement(Demo3Fragment.newInstance()), false, sharedElement);
                break;
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
