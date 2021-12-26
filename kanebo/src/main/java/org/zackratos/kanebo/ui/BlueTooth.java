package org.zackratos.kanebo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import androidx.annotation.NonNull;

// 蓝牙
public class BlueTooth extends BaseActivity {

    @Override
    protected int initView() {
        return R.layout.atc_bluetooth;
    }

    @Override
    protected void initData() {
        // 开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();// 开启事务
                message.what = 1;
                message.obj = "UserCode";
                myHandler.sendMessage(message);// ?
            }
        });

    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    // ?
    @SuppressLint("HandlerLeak")
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // 进行主UI刷新
                    break;
            }
        }
    };

}
