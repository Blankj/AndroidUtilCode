package com.blankj.utilcode.pkg.feature.bus;

import java.io.Serializable;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/08
 *     desc  :
 * </pre>
 */
public class PusNotifyData implements Serializable {

    private String pushType;
    private String pushID;

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

}
