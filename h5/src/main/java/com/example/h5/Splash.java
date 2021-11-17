package com.example.h5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.example.h5.hybridTool.FileTool;
import com.example.h5.hybridTool.Rms;
import com.example.h5.hybridTool.StringTool;

public class Splash extends FrmActivity {
    private Handler m_Handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("buzhou", "步骤3---Frm_Splash");
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏系统标题栏
        setContentView(R.layout.splash);
        initHander();// 开启线程接收
//        if (isNew()) {
        // 解压zip文件
        if (!FileTool.UnZip(Splash.this, FileTool.ZIP_NAME)) {
//            CToast.showMsg(this, "系统文件解压失败,请退出重试!");
            Log.i("H55", "系统文件解压失败,请退出重试!");
        } else {
            Rms.putString(Splash.this, Rms.UPDATE_HTML, HTMLVERSION);
        }
//        }
        init();// 初始化
    }

    private boolean isNew() {
        String isNeedUpdate = Rms.getString(Splash.this, Rms.UPDATE_HTML);
        Log.i("wxc", "--------wx----------" + isNeedUpdate);
        if (StringTool.isEmpty(isNeedUpdate)
                || !isNeedUpdate.equals(HTMLVERSION)) {
            Log.i("wxc", "--------wx1----------" + isNeedUpdate);
            return true;
        }
        return false;
    }

    @SuppressLint("HandlerLeak")
    private void initHander() {
        m_Handler = new Handler() {
            // @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        init();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void init() {
        // m_Handler.postDelayed(new Runnable() {
        // @Override
        // public void run() {
        // toHome();
        // }
        // }, 100);
        toHome();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void toHome() {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        Splash.this.startActivity(intent);
        Splash.this.finish();
    }


}
