package com.blankj.utilcode.util.http;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/17
 * </pre>
 */
public abstract class ResponseCallback {
    public abstract void onResponse(Response response);

    public abstract void onFailed(Exception e);
}
