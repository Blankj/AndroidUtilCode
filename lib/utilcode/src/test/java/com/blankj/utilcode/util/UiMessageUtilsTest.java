package com.blankj.utilcode.util;

import android.support.annotation.NonNull;

import org.junit.Test;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/21
 *     desc  : test UiMessageUtils
 * </pre>
 */
public class UiMessageUtilsTest extends BaseTest {

    @Test
    public void singleMessageTest() {
        UiMessageUtils.UiMessageCallback listener = new UiMessageUtils.UiMessageCallback() {
            @Override
            public void handleMessage(@NonNull UiMessageUtils.UiMessage localMessage) {
                System.out.println("receive -> " + localMessage.getId() + ": " + localMessage.getObject());
            }
        };
        UiMessageUtils.getInstance().addListener(listener);

        UiMessageUtils.getInstance().send(1, "msg");

        UiMessageUtils.getInstance().removeListener(listener);

        UiMessageUtils.getInstance().send(1, "msg");
    }

    @Test
    public void multiMessageTest() {
        UiMessageUtils.UiMessageCallback listener = new UiMessageUtils.UiMessageCallback() {
            @Override
            public void handleMessage(@NonNull UiMessageUtils.UiMessage localMessage) {
                switch (localMessage.getId()) {
                    case 1:
                        System.out.println("receive -> 1: " + localMessage.getObject());
                        break;
                    case 2:
                        System.out.println("receive -> 2: " + localMessage.getObject());
                        break;
                    case 4:
                        System.out.println("receive -> 4: " + localMessage.getObject());
                        break;
                }
            }
        };
        UiMessageUtils.getInstance().addListener(listener);

        UiMessageUtils.getInstance().send(1, "msg1");
        UiMessageUtils.getInstance().send(2, "msg2");
        UiMessageUtils.getInstance().send(4, "msg4");

        UiMessageUtils.getInstance().removeListener(listener);

        UiMessageUtils.getInstance().send(1, "msg1");
        UiMessageUtils.getInstance().send(2, "msg2");
        UiMessageUtils.getInstance().send(4, "msg4");
    }
}