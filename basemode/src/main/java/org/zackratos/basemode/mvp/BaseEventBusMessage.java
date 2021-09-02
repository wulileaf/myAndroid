package org.zackratos.basemode.mvp;


/**
 * @Date 2017/10/31
 * @author leaf
 * @version 1.0
 * @Note 为EventBus传递信息使用
 */

public class BaseEventBusMessage {

    private String mMsg;

    public BaseEventBusMessage(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
