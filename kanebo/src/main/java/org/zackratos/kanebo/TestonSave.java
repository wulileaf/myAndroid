package org.zackratos.kanebo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;


// 接口测试
public class TestonSave extends BaseActivity {


    @Override
    protected int initView() {
        return R.layout.test_and;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    // 数据缓存
    // 两个参数的适用于API 21及更高版本 Bundle outState, PersistableBundle outPersistentState
    // 一个参数的对于API小于20 Bundle outState
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String a = "你好";
        outPersistentState.putString("data_key", a);
    }
}
