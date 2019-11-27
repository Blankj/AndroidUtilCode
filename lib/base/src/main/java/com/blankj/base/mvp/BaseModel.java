package com.blankj.base.mvp;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/02
 *     desc  :
 * </pre>
 */
public abstract class BaseModel {

    private List<ThreadUtils.Task> mTasks = new ArrayList<>();

    public abstract void onCreateModel();

    public abstract void onDestroyModel();

    public <T> ThreadUtils.Task<T> addAutoDestroyTask(ThreadUtils.Task<T> task) {
        if (task == null) return null;
        mTasks.add(task);
        return task;
    }

    void destroy() {
        onDestroyModel();
        for (ThreadUtils.Task task : mTasks) {
            if (task == null) continue;
            task.cancel();
            ToastUtils.showLong("Mvp Task Canceled.");
        }
    }
}
