package com.blankj.subutil.util.http;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/17
 * </pre>
 */
public abstract class ResponseCallback {
    public abstract void onResponse(Response response);

    public abstract void onFailed(Exception e);
}
