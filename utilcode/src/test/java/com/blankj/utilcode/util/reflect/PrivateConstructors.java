package com.blankj.utilcode.util.reflect;

public class PrivateConstructors {

    public final String string;

    private PrivateConstructors() {
        this(null);
    }

    private PrivateConstructors(String string) {
        this.string = string;
    }
}
