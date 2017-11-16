package com.blankj.utilcode.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/01/17
 *     desc  : Fragment相关工具类
 * </pre>
 */
public final class FragmentUtils {

    private static final int TYPE_ADD_FRAGMENT       = 0x01;
    private static final int TYPE_SHOW_FRAGMENT      = 0x01 << 1;
    private static final int TYPE_HIDE_FRAGMENT      = 0x01 << 2;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_REPLACE_FRAGMENT   = 0x01 << 4;
    private static final int TYPE_REMOVE_FRAGMENT    = 0x01 << 5;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 6;

    private static final String ARGS_ID           = "args_id";
    private static final String ARGS_IS_HIDE      = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId) {
        add(fm, add, containerId, false, false);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param isHide      是否隐藏
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide) {
        add(fm, add, containerId, isHide, false);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param isHide      是否隐藏
     * @param isAddStack  是否入回退栈
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide,
                           final boolean isAddStack) {
        putArgs(add, new Args(containerId, isHide, isAddStack));
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param enterAnim   入场动画
     * @param exitAnim    出场动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param add         要新增的fragment
     * @param isAddStack  是否入回退栈
     * @param enterAnim   入场动画
     * @param exitAnim    出场动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 新增fragment
     *
     * @param fm           fragment管理器
     * @param containerId  布局Id
     * @param add          要新增的fragment
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 新增fragment
     *
     * @param fm           fragment管理器
     * @param containerId  布局Id
     * @param add          要新增的fragment
     * @param isAddStack   是否入回退栈
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     * 新增fragment
     *
     * @param fm             fragment管理器
     * @param add            新增的fragment
     * @param containerId    布局Id
     * @param sharedElements 共享元素
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @NonNull final View... sharedElements) {
        add(fm, add, containerId, false, sharedElements);
    }

    /**
     * 新增fragment
     *
     * @param fm             fragment管理器
     * @param add            新增的fragment
     * @param containerId    布局Id
     * @param isAddStack     是否入回退栈
     * @param sharedElements 共享元素
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @NonNull final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param add         新增的fragment
     * @param containerId 布局Id
     * @param showIndex   要显示的fragment索引
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final List<Fragment> add,
                           @IdRes final int containerId,
                           final int showIndex) {
        add(fm, add.toArray(new Fragment[add.size()]), containerId, showIndex);
    }

    /**
     * 新增fragment
     *
     * @param fm          fragment管理器
     * @param add         新增的fragment
     * @param containerId 布局Id
     * @param showIndex   要显示的fragment索引
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment[] add,
                           @IdRes final int containerId,
                           final int showIndex) {
        for (int i = 0, len = add.length; i < len; ++i) {
            putArgs(add[i], new Args(containerId, showIndex != i, false));
        }
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    /**
     * 显示fragment
     *
     * @param show 要显示的fragment
     */
    public static void show(@NonNull final Fragment show) {
        putArgs(show, false);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_FRAGMENT, null, show);
    }

    /**
     * 显示fragment
     *
     * @param fm fragment管理器
     */
    public static void show(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment show : fragments) {
            putArgs(show, false);
        }
        operateNoAnim(fm, TYPE_SHOW_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    /**
     * 隐藏fragment
     *
     * @param hide 要隐藏的fragment
     */
    public static void hide(@NonNull final Fragment hide) {
        putArgs(hide, true);
        operateNoAnim(hide.getFragmentManager(), TYPE_HIDE_FRAGMENT, null, hide);
    }

    /**
     * 隐藏fragment
     *
     * @param fm fragment管理器
     */
    public static void hide(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment hide : fragments) {
            putArgs(hide, true);
        }
        operateNoAnim(fm, TYPE_HIDE_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param showIndex 要显示的fragment索引
     * @param fragments 要隐藏的fragments
     */
    public static void showHide(final int showIndex, @NonNull final List<Fragment> fragments) {
        showHide(fragments.get(showIndex), fragments);
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param show 要显示的fragment
     * @param hide 要隐藏的fragment
     */
    public static void showHide(@NonNull final Fragment show, @NonNull final List<Fragment> hide) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show,
                hide.toArray(new Fragment[hide.size()]));
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param showIndex 要显示的fragment索引
     * @param fragments 要隐藏的fragments
     */
    public static void showHide(final int showIndex, @NonNull final Fragment... fragments) {
        showHide(fragments[showIndex], fragments);
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param show 要显示的fragment
     * @param hide 要隐藏的fragment
     */
    public static void showHide(@NonNull final Fragment show, @NonNull final Fragment... hide) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }

    /**
     * 先显示后隐藏fragment
     *
     * @param show 要显示的fragment
     * @param hide 要隐藏的fragment
     */
    public static void showHide(@NonNull final Fragment show,
                                @NonNull final Fragment hide) {
        putArgs(show, false);
        putArgs(hide, true);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment) {
        replace(srcFragment, destFragment, false);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param isAddStack   是否入回退栈
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param isAddStack   是否入回退栈
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param isAddStack   是否入回退栈
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack,
                enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment    源fragment
     * @param destFragment   目标fragment
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final View... sharedElements) {
        replace(srcFragment, destFragment, false, sharedElements);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment    源fragment
     * @param destFragment   目标fragment
     * @param isAddStack     是否入回退栈
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               final View... sharedElements) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack, sharedElements);
    }

    /**
     * 替换fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param fragment    fragment
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId) {
        replace(fm, fragment, containerId, false);
    }

    /**
     * 替换fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param fragment    fragment
     * @param isAddStack  是否入回退栈
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * 替换fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param fragment    fragment
     * @param enterAnim   入场动画
     * @param exitAnim    出场动画
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换fragment
     *
     * @param fm          fragment管理器
     * @param containerId 布局Id
     * @param fragment    fragment
     * @param isAddStack  是否入回退栈
     * @param enterAnim   入场动画
     * @param exitAnim    出场动画
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(fm, fragment, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换fragment
     *
     * @param fm           fragment管理器
     * @param containerId  布局Id
     * @param fragment     fragment
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换fragment
     *
     * @param fm           fragment管理器
     * @param containerId  布局Id
     * @param fragment     fragment
     * @param isAddStack   是否入回退栈
     * @param enterAnim    入场动画
     * @param exitAnim     出场动画
     * @param popEnterAnim 入栈动画
     * @param popExitAnim  出栈动画
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * 替换fragment
     *
     * @param fm             fragment管理器
     * @param containerId    布局Id
     * @param fragment       fragment
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final View... sharedElements) {
        replace(fm, fragment, containerId, false, sharedElements);
    }

    /**
     * 替换fragment
     *
     * @param fm             fragment管理器
     * @param containerId    布局Id
     * @param fragment       fragment
     * @param isAddStack     是否入回退栈
     * @param sharedElements 共享元素
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * 出栈fragment
     *
     * @param fm fragment管理器
     */
    public static void pop(@NonNull final FragmentManager fm) {
        pop(fm, true);
    }

    /**
     * 出栈fragment
     *
     * @param fm fragment管理器
     */
    public static void pop(@NonNull final FragmentManager fm,
                           final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate();
        } else {
            fm.popBackStack();
        }
    }

    /**
     * 出栈到指定fragment
     *
     * @param fm          fragment管理器
     * @param popClz      出栈fragment的类型
     * @param isInclusive 是否出栈popClz的fragment
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive) {
        popTo(fm, popClz, isInclusive, true);
    }

    /**
     * 出栈到指定fragment
     *
     * @param fm          fragment管理器
     * @param popClz      出栈fragment的类型
     * @param isInclusive 是否出栈popClz的fragment
     * @param isImmediate 是否立即出栈
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive,
                             final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate(popClz.getName(),
                    isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        } else {
            fm.popBackStack(popClz.getName(),
                    isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }
    }

    /**
     * 出栈所有fragment
     *
     * @param fm fragment管理器
     */
    public static void popAll(@NonNull final FragmentManager fm) {
        popAll(fm, true);
    }

    /**
     * 出栈所有fragment
     *
     * @param fm fragment管理器
     */
    public static void popAll(@NonNull final FragmentManager fm, final boolean isImmediate) {
        while (fm.getBackStackEntryCount() > 0) {
            if (isImmediate) {
                fm.popBackStackImmediate();
            } else {
                fm.popBackStack();
            }
        }
    }

    /**
     * 移除fragment
     *
     * @param remove 要移除的fragment
     */
    public static void remove(@NonNull final Fragment remove) {
        operateNoAnim(remove.getFragmentManager(), TYPE_REMOVE_FRAGMENT, null, remove);
    }

    /**
     * 移除到指定fragment
     *
     * @param removeTo    要移除到的fragment
     * @param isInclusive 是否移除removeTo
     */
    public static void removeTo(@NonNull final Fragment removeTo, final boolean isInclusive) {
        operateNoAnim(removeTo.getFragmentManager(), TYPE_REMOVE_TO_FRAGMENT,
                isInclusive ? removeTo : null, removeTo);
    }

    /**
     * 移除所有fragment
     *
     * @param fm fragment管理器
     */
    public static void removeAll(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        operateNoAnim(fm, TYPE_REMOVE_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }

    private static void putArgs(final Fragment fragment, final Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    private static void putArgs(final Fragment fragment, final boolean isHide) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    private static Args getArgs(final Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        return new Args(bundle.getInt(ARGS_ID, fragment.getId()),
                bundle.getBoolean(ARGS_IS_HIDE),
                bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    private static void operateNoAnim(final FragmentManager fm,
                                      final int type,
                                      final Fragment src,
                                      Fragment... dest) {
        FragmentTransaction ft = fm.beginTransaction();
        operate(type, fm, ft, src, dest);
    }

    private static void operate(final int type,
                                final FragmentManager fm,
                                final FragmentTransaction ft,
                                final Fragment src,
                                final Fragment... dest) {
        if (src != null && src.isRemoving()) {
            Log.e("FragmentUtils", src.getClass().getName() + " is isRemoving");
            return;
        }
        String name;
        Bundle args;
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                for (Fragment fragment : dest) {
                    name = fragment.getClass().getName();
                    args = fragment.getArguments();
                    Fragment fragmentByTag = fm.findFragmentByTag(name);
                    if (fragmentByTag != null) {
                        if (fragmentByTag.isAdded()) {
                            ft.remove(fragmentByTag);
                        }
                    }
                    ft.add(args.getInt(ARGS_ID), fragment, name);
                    if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(fragment);
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                }
                break;
            case TYPE_HIDE_FRAGMENT:
                for (Fragment fragment : dest) {
                    ft.hide(fragment);
                }
                break;
            case TYPE_SHOW_FRAGMENT:
                for (Fragment fragment : dest) {
                    ft.show(fragment);
                }
                break;
            case TYPE_SHOW_HIDE_FRAGMENT:
                ft.show(src);
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        ft.hide(fragment);
                    }
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                name = dest[0].getClass().getName();
                args = dest[0].getArguments();
                ft.replace(args.getInt(ARGS_ID), dest[0], name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        ft.remove(fragment);
                    }
                }
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                for (int i = dest.length - 1; i >= 0; --i) {
                    Fragment fragment = dest[i];
                    if (fragment == dest[0]) {
                        if (src != null) ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    private static void addAnim(final FragmentTransaction ft,
                                final int enter,
                                final int exit,
                                final int popEnter,
                                final int popExit) {
        ft.setCustomAnimations(enter, exit, popEnter, popExit);
    }

    private static void addSharedElement(final FragmentTransaction ft,
                                         final View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : views) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        }
    }

    /**
     * 获取顶部fragment
     *
     * @param fm fragment管理器
     * @return 最后加入的fragment
     */
    public static Fragment getTop(@NonNull final FragmentManager fm) {
        return getTopIsInStack(fm, false);
    }

    /**
     * 获取栈中顶部fragment
     *
     * @param fm fragment管理器
     * @return 最后加入的fragment
     */
    public static Fragment getTopInStack(@NonNull final FragmentManager fm) {
        return getTopIsInStack(fm, true);
    }

    private static Fragment getTopIsInStack(@NonNull final FragmentManager fm,
                                            final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * 获取顶部可见fragment
     *
     * @param fm fragment管理器
     * @return 顶层可见fragment
     */
    public static Fragment getTopShow(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, false);
    }

    /**
     * 获取栈中顶部可见fragment
     *
     * @param fm fragment管理器
     * @return 栈中顶层可见fragment
     */
    public static Fragment getTopShowInStack(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, true);
    }

    private static Fragment getTopShowIsInStack(@NonNull final FragmentManager fm,
                                                final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * 获取同级别的fragment
     *
     * @param fm fragment管理器
     * @return fragment管理器中的fragment
     */
    public static List<Fragment> getFragments(@NonNull final FragmentManager fm) {
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        return fragments;
    }

    /**
     * 获取同级别栈中的fragment
     *
     * @param fm fragment管理器
     * @return fragment管理器栈中的fragment
     */
    public static List<Fragment> getFragmentsInStack(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        List<Fragment> result = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(fragment);
            }
        }
        return result;
    }

    /**
     * 获取所有fragment
     *
     * @param fm fragment管理器
     * @return 所有fragment
     */
    public static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm) {
        return getAllFragments(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm,
                                                      final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                result.add(new FragmentNode(fragment,
                        getAllFragments(fragment.getChildFragmentManager(),
                                new ArrayList<FragmentNode>())));
            }
        }
        return result;
    }

    /**
     * 获取栈中所有fragment
     *
     * @param fm fragment管理器
     * @return 所有fragment
     */
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm) {
        return getAllFragmentsInStack(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm,
                                                             final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(new FragmentNode(fragment,
                        getAllFragmentsInStack(fragment.getChildFragmentManager(),
                                new ArrayList<FragmentNode>())));
            }
        }
        return result;
    }

    /**
     * 查找fragment
     *
     * @param fm      fragment管理器
     * @param findClz 要查找的fragment类型
     * @return 查找到的fragment
     */
    public static Fragment findFragment(@NonNull final FragmentManager fm,
                                        final Class<? extends Fragment> findClz) {
        return fm.findFragmentByTag(findClz.getName());
    }

    /**
     * 处理fragment回退键
     * <p>如果fragment实现了OnBackClickListener接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p>
     * <p>具体示例见FragmentActivity</p>
     *
     * @param fragment fragment
     * @return 是否消费回退事件
     */
    public static boolean dispatchBackPress(@NonNull final Fragment fragment) {
        return fragment.isResumed()
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackClick();
    }

    /**
     * 处理fragment回退键
     * <p>如果fragment实现了OnBackClickListener接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p>
     * <p>具体示例见FragmentActivity</p>
     *
     * @param fm fragment管理器
     * @return 是否消费回退事件
     */
    public static boolean dispatchBackPress(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        if (fragments == null || fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置背景色
     *
     * @param fragment fragment
     * @param color    背景色
     */
    public static void setBackgroundColor(@NonNull final Fragment fragment, @ColorInt final int color) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * 设置背景资源
     *
     * @param fragment fragment
     * @param resId    资源Id
     */
    public static void setBackgroundResource(@NonNull final Fragment fragment, @DrawableRes final int resId) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * 设置背景
     *
     * @param fragment   fragment
     * @param background 背景
     */
    public static void setBackground(@NonNull final Fragment fragment, final Drawable background) {
        ViewCompat.setBackground(fragment.getView(), background);
    }

    /**
     * 获取类名
     *
     * @param fragment fragment
     * @return 类名
     */
    public static String getSimpleName(final Fragment fragment) {
        return fragment == null ? "null" : fragment.getClass().getSimpleName();
    }

    private static class Args {
        int     id;
        boolean isHide;
        boolean isAddStack;

        private Args(final int id, final boolean isHide, final boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    public static class FragmentNode {
        Fragment           fragment;
        List<FragmentNode> next;

        public FragmentNode(final Fragment fragment, final List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }

        @Override
        public String toString() {
            return fragment.getClass().getSimpleName()
                    + "->"
                    + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }
}
