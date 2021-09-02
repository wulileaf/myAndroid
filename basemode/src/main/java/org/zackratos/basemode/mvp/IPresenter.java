package org.zackratos.basemode.mvp;


/**
 * @Date 2017/9/4
 * @author leaf
 * @version 1.0
 * @Note IPresenter接口
 */
public interface IPresenter {
    void onStart();
    void onDestroy();
    void showError(String error);
}
