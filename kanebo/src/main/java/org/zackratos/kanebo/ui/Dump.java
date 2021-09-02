package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;


// 测试跳动加载框
// https://github.com/zzz40500/android-shapeLoadingView
public class Dump extends BaseActivity {

    @Override
    protected int initView() {
        return R.layout.act_dump;
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

}
