package com.blankj.bus;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/10
 *     desc  :
 * </pre>
 */
public class BusInfo {

    public String           className;                // 函数所在类名
    public String           funName;                  // 方法名
    public List<ParamsInfo> paramsInfo;               // 参数列表信息
    public boolean          sticky;                   // 是否粘性
    public String           threadMode;               // 线程模式
    public int              priority;                 // 优先级
    public boolean          isParamSizeNoMoreThanOne; // 参数是否不多于 1 个

    public BusInfo(String className, String funName) {
        this.className = className;
        this.funName = funName;
        paramsInfo = new ArrayList<>();
        sticky = false;
        threadMode = "POSTING";
        priority = 0;
        isParamSizeNoMoreThanOne = true;
    }

    @Override
    public String toString() {
        String paramsInfoString = paramsInfo.toString();
        return "{ desc: " + className + "#" + funName +
                "(" + paramsInfoString.substring(1, paramsInfoString.length() - 1) + ")" +
                (!sticky ? "" : ", sticky: true") +
                (threadMode.equals("POSTING") ? "" : ", threadMode: " + threadMode) +
                (priority == 0 ? "" : ", priority: " + priority) +
                (isParamSizeNoMoreThanOne ? "" : ", paramSize: " + paramsInfo.size()) +
                " }";
    }

    public static class ParamsInfo {
        public String className;
        public String name;

        public ParamsInfo(String className, String name) {
            this.className = className;
            this.name = name;
        }

        @Override
        public String toString() {
            return ("".equals(className) ? "" : (className + " " + name));
        }
    }
}
