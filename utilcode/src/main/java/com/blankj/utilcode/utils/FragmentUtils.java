package com.blankj.utilcode.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/1/17
 *     desc  :
 * </pre>
 */
public class FragmentUtils {

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Fragment replaceFragment(FragmentManager fragmentManager,
                                           int container,
                                           Fragment newFragment) {
        return replaceFragment(fragmentManager, container, newFragment, false);
    }

    public static Fragment replaceFragment(FragmentManager fragmentManager,
                                           int container,
                                           Fragment newFragment,
                                           boolean addToBackStack) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getClass().getSimpleName();
        if (newFragment != null) {
            transaction.replace(container, newFragment, tag);
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        return newFragment;
    }

    public static Fragment replaceFragment(FragmentManager fragmentManager,
                                           int container,
                                           Class<? extends Fragment> newFragment,
                                           Bundle args) {
        return replaceFragment(fragmentManager, container, newFragment, args, false);
    }

    public static Fragment replaceFragment(FragmentManager fragmentManager,
                                           int container, Class<? extends Fragment> newFragment,
                                           Bundle args, boolean addToBackStack) {
        Fragment fragment = null;
        // 构造新的Fragment
        try {
            fragment = newFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            // 设置参数
            if (args != null && !args.isEmpty()) {
                final Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
            // 替换
            return replaceFragment(fragmentManager, container, fragment, addToBackStack);
        } else {
            return null;
        }
    }


    public static Fragment switchFragment(FragmentManager fragmentManager,
                                          int container,
                                          Fragment currentFragment,
                                          Class<? extends Fragment> newFragment,
                                          Bundle args) {
        return switchFragment(fragmentManager, container, currentFragment, newFragment, args, false);
    }

    /**
     * @param fragmentManager
     * @param container
     * @param currentFragment
     * @param newFragment
     * @param args            新Fragment的参数
     * @param addToBackStack  这个操作是否加入栈中，如果要实现类似返回效果，则需要�?
     * @return 新显示的Fragment
     */
    public static Fragment switchFragment(FragmentManager fragmentManager,
                                          int container,
                                          Fragment currentFragment,
                                          Class<? extends Fragment> newFragment,
                                          Bundle args,
                                          boolean addToBackStack) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getSimpleName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        // 如果在栈中找到相应的Fragment，则显示，否则重新生成一�?
        if (fragment != null) {
            if (fragment != currentFragment) {
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.show(fragment);
                if (addToBackStack) {
                    transaction.addToBackStack(null);
                }
                transaction.commitAllowingStateLoss();
            } else {
                fragment.getArguments().putAll(args);
            }

            return fragment;
        } else {
            try {
                fragment = newFragment.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 为新的Fragment添加参数
        if (fragment != null) {
            if (args != null && !args.isEmpty()) {
                final Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
        }
        // 显示新的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.add(container, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }
}
