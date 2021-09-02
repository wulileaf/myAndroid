package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

public class BlueTooth extends BaseActivity {

    @Override
    protected int initView() {
        return R.layout.atc_bluetooth;
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
