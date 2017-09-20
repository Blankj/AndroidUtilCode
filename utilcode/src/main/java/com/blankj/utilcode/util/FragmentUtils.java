package com.blankj.utilcode.util;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
    private static final int TYPE_ADD_HIDE_FRAGMENT  = 0x01 << 1;
    private static final int TYPE_REMOVE_FRAGMENT    = 0x01 << 2;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 3;
    private static final int TYPE_REPLACE_FRAGMENT   = 0x01 << 4;
    private static final int TYPE_ADD_POP_FRAGMENT   = 0x01 << 5;
    private static final int TYPE_HIDE_FRAGMENT      = 0x01 << 6;
    private static final int TYPE_SHOW_FRAGMENT      = 0x01 << 7;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 8;

    private static final String ARGS_ID           = "args_id";
    private static final String ARGS_IS_HIDE      = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @return fragment
     */
    public static Fragment addFragment(@NonNull final FragmentManager fragmentManager,
                                       @NonNull final Fragment fragment,
                                       @IdRes final int containerId) {
        return addFragment(fragmentManager, fragment, containerId, false, false);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isHide          是否隐藏
     * @return fragment
     */
    public static Fragment addFragment(@NonNull final FragmentManager fragmentManager,
                                       @NonNull final Fragment fragment,
                                       @IdRes final int containerId,
                                       final boolean isHide) {
        return addFragment(fragmentManager, fragment, containerId, isHide, false);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isHide          是否隐藏
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment addFragment(@NonNull final FragmentManager fragmentManager,
                                       @NonNull final Fragment fragment,
                                       @IdRes final int containerId,
                                       final boolean isHide,
                                       final boolean isAddStack) {
        putArgs(fragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isHide          是否隐藏
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment addFragment(@NonNull final FragmentManager fragmentManager,
                                       @NonNull final Fragment fragment,
                                       @IdRes final int containerId,
                                       final boolean isHide,
                                       final boolean isAddStack,
                                       SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT, sharedElement);
    }

    /**
     * 先隐藏后新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param hideFragment    要隐藏的fragment
     * @param addFragment     新增的fragment
     * @param isHide          是否隐藏
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment hideAddFragment(@NonNull final FragmentManager fragmentManager,
                                           @NonNull final Fragment hideFragment,
                                           @NonNull final Fragment addFragment,
                                           @IdRes final int containerId,
                                           final boolean isHide,
                                           final boolean isAddStack,
                                           final SharedElement... sharedElement) {
        putArgs(addFragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, hideFragment, addFragment, TYPE_ADD_HIDE_FRAGMENT, sharedElement);
    }

    /**
     * 新增多个fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragments       fragments
     * @param containerId     布局Id
     * @param showIndex       要显示的fragment索引
     * @return 要显示的fragment
     */
    public static Fragment addFragments(@NonNull final FragmentManager fragmentManager,
                                        @NonNull final List<Fragment> fragments,
                                        @IdRes final int containerId,
                                        final int showIndex) {
        for (int i = 0, size = fragments.size(); i < size; ++i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                addFragment(fragmentManager, fragment, containerId, showIndex != i, false);
            }
        }
        return fragments.get(showIndex);
    }

    /**
     * 新增多个fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragments       fragments
     * @param containerId     布局Id
     * @param showIndex       要显示的fragment索引
     * @param lists           共享元素链表
     * @return 要显示的fragment
     */
    public static Fragment addFragments(@NonNull final FragmentManager fragmentManager,
                                        @NonNull final List<Fragment> fragments,
                                        @IdRes final int containerId,
                                        final int showIndex,
                                        @NonNull final List<SharedElement>... lists) {
        for (int i = 0, size = fragments.size(); i < size; ++i) {
            Fragment fragment = fragments.get(i);
            List<SharedElement> list = lists[i];
            if (fragment != null) {
                if (list != null) {
                    putArgs(fragment, new Args(containerId, showIndex != i, false));
                    return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT, list.toArray(new SharedElement[0]));
                }
            }
        }
        return fragments.get(showIndex);
    }

    /**
     * 移除fragment
     *
     * @param fragment fragment
     */
    public static void removeFragment(@NonNull final Fragment fragment) {
        operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_REMOVE_FRAGMENT);
    }

    /**
     * 移除到指定fragment
     *
     * @param fragment      fragment
     * @param isIncludeSelf 是否包括Fragment类自己
     */
    public static void removeToFragment(@NonNull final Fragment fragment, final boolean isIncludeSelf) {
        operateFragment(fragment.getFragmentManager(), isIncludeSelf ? fragment : null, fragment, TYPE_REMOVE_TO_FRAGMENT);
    }

    /**
     * 移除同级别fragment
     */
    public static void removeFragments(@NonNull final FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) removeFragment(fragment);
        }
    }

    /**
     * 移除所有fragment
     */
    public static void removeAllFragments(@NonNull final FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                removeAllFragments(fragment.getChildFragmentManager());
                removeFragment(fragment);
            }
        }
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @return 目标fragment
     */
    public static Fragment replaceFragment(@NonNull final Fragment srcFragment,
                                           @NonNull final Fragment destFragment) {
        return replaceFragment(srcFragment, destFragment, false);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param isAddStack   是否入回退栈
     * @return 目标fragment
     */
    public static Fragment replaceFragment(@NonNull final Fragment srcFragment,
                                           @NonNull final Fragment destFragment,
                                           final boolean isAddStack) {
        if (srcFragment.getArguments() == null) return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0) return null;
        return replaceFragment(srcFragment.getFragmentManager(), destFragment, containerId, isAddStack);
    }

    /**
     * 替换fragment
     *
     * @param srcFragment   源fragment
     * @param destFragment  目标fragment
     * @param isAddStack    是否入回退栈
     * @param sharedElement 共享元素
     * @return 目标fragment
     */
    public static Fragment replaceFragment(@NonNull final Fragment srcFragment,
                                           @NonNull final Fragment destFragment,
                                           final boolean isAddStack,
                                           final SharedElement... sharedElement) {
        if (srcFragment.getArguments() == null) return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0) return null;
        return replaceFragment(srcFragment.getFragmentManager(), destFragment, containerId, isAddStack, sharedElement);
    }

    /**
     * 替换fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment replaceFragment(@NonNull final FragmentManager fragmentManager,
                                           @NonNull final Fragment fragment,
                                           @IdRes final int containerId,
                                           final boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_REPLACE_FRAGMENT);
    }

    /**
     * 替换fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @param sharedElement   共享元素
     * @return fragment
     */
    public static Fragment replaceFragment(@NonNull final FragmentManager fragmentManager,
                                           @NonNull final Fragment fragment,
                                           @IdRes final int containerId,
                                           final boolean isAddStack,
                                           final SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_REPLACE_FRAGMENT, sharedElement);
    }

    /**
     * 出栈fragment
     *
     * @param fragmentManager fragment管理器
     * @return {@code true}: 出栈成功<br>{@code false}: 出栈失败
     */
    public static boolean popFragment(@NonNull final FragmentManager fragmentManager) {
        return fragmentManager.popBackStackImmediate();
    }

    /**
     * 出栈到指定fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragmentClass   Fragment类
     * @param isIncludeSelf   是否包括Fragment类自己
     * @return {@code true}: 出栈成功<br>{@code false}: 出栈失败
     */
    public static boolean popToFragment(@NonNull final FragmentManager fragmentManager,
                                        final Class<? extends Fragment> fragmentClass,
                                        final boolean isIncludeSelf) {
        return fragmentManager.popBackStackImmediate(fragmentClass.getName(), isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
    }

    /**
     * 出栈同级别fragment
     *
     * @param fragmentManager fragment管理器
     */
    public static void popFragments(@NonNull final FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * 出栈所有fragment
     *
     * @param fragmentManager fragment管理器
     */
    public static void popAllFragments(@NonNull final FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) popAllFragments(fragment.getChildFragmentManager());
        }
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * 先出栈后新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment popAddFragment(@NonNull final FragmentManager fragmentManager,
                                          @NonNull final Fragment fragment,
                                          @IdRes final int containerId,
                                          final boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_POP_FRAGMENT);
    }

    /**
     * 先出栈后新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment popAddFragment(@NonNull final FragmentManager fragmentManager,
                                          @NonNull final Fragment fragment,
                                          @IdRes final int containerId,
                                          final boolean isAddStack,
                                          final SharedElement... sharedElements) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_POP_FRAGMENT, sharedElements);
    }

    /**
     * 隐藏fragment
     *
     * @param fragment fragment
     * @return 隐藏的Fragment
     */
    public static Fragment hideFragment(@NonNull final Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, true, args.isAddStack));
        }
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_HIDE_FRAGMENT);
    }

    /**
     * 隐藏同级别fragment
     *
     * @param fragmentManager fragment管理器
     */
    public static void hideFragments(@NonNull final FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) hideFragment(fragment);
        }
    }

    /**
     * 显示fragment
     *
     * @param fragment fragment
     * @return show的Fragment
     */
    public static Fragment showFragment(@NonNull final Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, false, args.isAddStack));
        }
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_SHOW_FRAGMENT);
    }

    /**
     * 显示fragment
     *
     * @param fragment fragment
     * @return show的Fragment
     */
    public static Fragment hideAllShowFragment(@NonNull final Fragment fragment) {
        hideFragments(fragment.getFragmentManager());
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_SHOW_FRAGMENT);
    }

    /**
     * 先隐藏后显示fragment
     *
     * @param hideFragment 需要隐藏的Fragment
     * @param showFragment 需要显示的Fragment
     * @return 显示的Fragment
     */
    public static Fragment hideShowFragment(@NonNull final Fragment hideFragment,
                                            @NonNull final Fragment showFragment) {
        Args args = getArgs(hideFragment);
        hideFragment.getId();
        if (args != null) {
            putArgs(hideFragment, new Args(args.id, true, args.isAddStack));
        }
        args = getArgs(showFragment);
        if (args != null) {
            putArgs(showFragment, new Args(args.id, false, args.isAddStack));
        }
        return operateFragment(showFragment.getFragmentManager(), hideFragment, showFragment, TYPE_SHOW_HIDE_FRAGMENT);
    }

    /**
     * 传参
     *
     * @param fragment fragment
     * @param args     参数
     */
    private static void putArgs(@NonNull final Fragment fragment, final Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    /**
     * 获取参数
     *
     * @param fragment fragment
     */
    private static Args getArgs(@NonNull final Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null || bundle.getInt(ARGS_ID) == 0) return null;
        return new Args(bundle.getInt(ARGS_ID), bundle.getBoolean(ARGS_IS_HIDE), bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    /**
     * 操作fragment
     *
     * @param fragmentManager fragment管理器
     * @param srcFragment     源fragment
     * @param destFragment    目标fragment
     * @param type            操作类型
     * @param sharedElements  共享元素
     * @return destFragment
     */
    private static Fragment operateFragment(@NonNull final FragmentManager fragmentManager,
                                            final Fragment srcFragment,
                                            @NonNull Fragment destFragment,
                                            final int type,
                                            final SharedElement... sharedElements) {
        if (srcFragment == destFragment) return null;
        if (srcFragment != null && srcFragment.isRemoving()) {
            Log.e("FragmentUtils", srcFragment.getClass().getName() + " is isRemoving");
            return null;
        }
        String name = destFragment.getClass().getName();
        Bundle args = destFragment.getArguments();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (sharedElements == null || sharedElements.length == 0) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        } else {
            for (SharedElement element : sharedElements) {// 添加共享元素动画
                ft.addSharedElement(element.sharedElement, element.name);
            }
        }
        switch (type) {
            case TYPE_ADD_HIDE_FRAGMENT:
                ft.hide(srcFragment);
            case TYPE_ADD_FRAGMENT:
                Fragment fragmentByTag = fragmentManager.findFragmentByTag(name);
                if (fragmentByTag != null) {
                    if (fragmentByTag.isAdded()) {
                        ft.remove(fragmentByTag);
                    }
                }
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(destFragment);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                ft.remove(destFragment);
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                List<Fragment> fragments = getFragments(fragmentManager);
                for (int i = fragments.size() - 1; i >= 0; --i) {
                    Fragment fragment = fragments.get(i);
                    if (fragment == destFragment) {
                        if (srcFragment != null) ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                ft.replace(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_ADD_POP_FRAGMENT:
                popFragment(fragmentManager);
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_HIDE_FRAGMENT:
                ft.hide(destFragment);
                break;
            case TYPE_SHOW_FRAGMENT:
                ft.show(destFragment);
                break;
            case TYPE_SHOW_HIDE_FRAGMENT:
                ft.hide(srcFragment).show(destFragment);
                break;
        }
        ft.commitAllowingStateLoss();
        return destFragment;
    }

    /**
     * 获取同级别最后加入的fragment
     *
     * @param fragmentManager fragment管理器
     * @return 最后加入的fragment
     */
    public static Fragment getLastAddFragment(@NonNull final FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, false);
    }

    /**
     * 获取栈中同级别最后加入的fragment
     *
     * @param fragmentManager fragment管理器
     * @return 最后加入的fragment
     */
    public static Fragment getLastAddFragmentInStack(@NonNull final FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, true);
    }

    /**
     * 根据栈参数获取同级别最后加入的fragment
     *
     * @param fragmentManager fragment管理器
     * @param isInStack       是否是栈中的
     * @return 栈中最后加入的fragment
     */
    private static Fragment getLastAddFragmentIsInStack(@NonNull final FragmentManager fragmentManager,
                                                        final boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return null;
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
     * 获取顶层可见fragment
     *
     * @param fragmentManager fragment管理器
     * @return 顶层可见fragment
     */
    public static Fragment getTopShowFragment(@NonNull final FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, false);
    }

    /**
     * 获取栈中顶层可见fragment
     *
     * @param fragmentManager fragment管理器
     * @return 栈中顶层可见fragment
     */
    public static Fragment getTopShowFragmentInStack(@NonNull final FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, true);
    }

    /**
     * 根据栈参数获取顶层可见fragment
     *
     * @param fragmentManager fragment管理器
     * @param parentFragment  父fragment
     * @param isInStack       是否是栈中的
     * @return 栈中顶层可见fragment
     */
    private static Fragment getTopShowFragmentIsInStack(@NonNull final FragmentManager fragmentManager,
                                                        final Fragment parentFragment,
                                                        final boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return parentFragment;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, true);
                    }
                } else {
                    return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, false);
                }
            }
        }
        return parentFragment;
    }

    /**
     * 获取同级别fragment
     *
     * @param fragmentManager fragment管理器
     * @return 同级别的fragments
     */
    public static List<Fragment> getFragments(@NonNull final FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, false);
    }

    /**
     * 获取栈中同级别fragment
     *
     * @param fragmentManager fragment管理器
     * @return 栈中同级别fragment
     */
    public static List<Fragment> getFragmentsInStack(@NonNull final FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, true);
    }

    /**
     * 根据栈参数获取同级别fragment
     *
     * @param fragmentManager fragment管理器
     * @param isInStack       是否是栈中的
     * @return 栈中同级别fragment
     */
    private static List<Fragment> getFragmentsIsInStack(@NonNull final FragmentManager fragmentManager, final boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        List<Fragment> result = new ArrayList<>();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(fragment);
                    }
                } else {
                    result.add(fragment);
                }
            }
        }
        return result;
    }

    /**
     * 获取所有fragment
     *
     * @param fragmentManager fragment管理器
     * @return 所有fragment
     */
    public static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), false);
    }

    /**
     * 获取栈中所有fragment
     *
     * @param fragmentManager fragment管理器
     * @return 所有fragment
     */
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), true);
    }

    /**
     * 根据栈参数获取所有fragment
     * <p>需之前对fragment的操作都借助该工具类</p>
     *
     * @param fragmentManager fragment管理器
     * @param result          结果
     * @param isInStack       是否是栈中的
     * @return 栈中所有fragment
     */
    private static List<FragmentNode> getAllFragmentsIsInStack(@NonNull final FragmentManager fragmentManager,
                                                               final List<FragmentNode> result,
                                                               final boolean isInStack) {
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), true)));
                    }
                } else {
                    result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), false)));
                }
            }
        }
        return result;
    }

    /**
     * 获取目标fragment的前一个fragment
     *
     * @param destFragment 目标fragment
     * @return 目标fragment的前一个fragment
     */
    public static Fragment getPreFragment(@NonNull final Fragment destFragment) {
        FragmentManager fragmentManager = destFragment.getFragmentManager();
        if (fragmentManager == null) return null;
        List<Fragment> fragments = getFragments(fragmentManager);
        boolean flag = false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (flag && fragment != null) {
                return fragment;
            }
            if (fragment == destFragment) {
                flag = true;
            }
        }
        return null;
    }

    /**
     * 查找fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragmentClass   fragment类
     * @return 查找到的fragment
     */
    public static Fragment findFragment(@NonNull final FragmentManager fragmentManager, final Class<? extends Fragment> fragmentClass) {
        return fragmentManager.findFragmentByTag(fragmentClass.getName());
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
        return dispatchBackPress(fragment.getFragmentManager());
    }

    /**
     * 处理fragment回退键
     * <p>如果fragment实现了OnBackClickListener接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p>
     * <p>具体示例见FragmentActivity</p>
     *
     * @param fragmentManager fragment管理器
     * @return 是否消费回退事件
     */
    public static boolean dispatchBackPress(@NonNull final FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
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

    static class Args {
        int     id;
        boolean isHide;
        boolean isAddStack;

        private Args(final int id, final boolean isHide, final boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    public static class SharedElement {
        View   sharedElement;
        String name;

        public SharedElement(final View sharedElement, final String name) {
            this.sharedElement = sharedElement;
            this.name = name;
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
            return fragment.getClass().getSimpleName() + "->" + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }
}
