package com.example.leaf;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

// 这是一个抽象类
abstract class BaseActivity extends Activity {
    protected BasePresenter basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        createPresenter();
        basePresenter.loadData();
//        name.getNameCode();
    }

    protected abstract void initContentView();

    protected abstract void createPresenter();

    protected void netWorkChange() {
        Log.i("Login", "netWorkChange-------");
    }

}
