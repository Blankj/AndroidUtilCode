package com.blankj.utilcode.util.http;

import java.io.IOException;

public interface Interceptor {

    Response intercept(Chain chain) throws IOException;
}
