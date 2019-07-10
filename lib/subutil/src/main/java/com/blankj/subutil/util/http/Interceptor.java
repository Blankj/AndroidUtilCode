package com.blankj.subutil.util.http;

import java.io.IOException;

public interface Interceptor {

    Response intercept(Chain chain) throws IOException;
}
