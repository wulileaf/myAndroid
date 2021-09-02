package org.zackratos.basemode.mvp;


/**
 * @Date 2017/9/1
 * @author leaf
 * @version 1.0
 * @Note 请求接口成功或者失败的方法接口
 */

public interface IDataRequestListener {
    void loadSuccess(Object object);

    void loadError(Object object);
}
