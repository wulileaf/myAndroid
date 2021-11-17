package com.example.h5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.h5.hybridTool.StringTool;

import androidx.annotation.Nullable;

public class FrmActivity extends Activity {

    private Context m_Context;
    private int m_Battery;
    public final static String APPNAME = "h5.apk";
    public final static String APPVERSION = "android1.0";// 稽核：android3.2,新大王：android1.9,欧普：android3.8,韩后android2.9,CKandroid3.1
    public final static String HTMLVERSION = "93";// 这个值是对应解压H5的-->>>下载的Apk里面自带的H5

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContext();
    }

    // 是否是飞行模式
    // Build.VERSION.SDK_INT 获取Android SDK的版本号
    // =1即为开启了飞行模式，=0即为没有开启飞行模式
    @SuppressLint("NewApi")
    public boolean getAirplaneMode(Context context) {
        String isAirplaneMode = "0";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            // 4.2或4.2以上
            return Settings.Global.getInt(getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    public void hideSystemStatusBar(boolean flag) {
        /*
         * int flag = 0; 这种方法只能隐藏部分状态栏的icon flag = View.STATUS_BAR_HIDDEN;
         * this.getWindow().getDecorView().setSystemUiVisibility(flag);
         * this.getWindow().getDecorView().requestLayout();
         */
        if (!flag) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// 强制显示状态栏
            /*
             * getWindow().setFlags(WindowManager.LayoutParams.
             * FLAG_FORCE_NOT_FULLSCREEN,
             * WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
             */
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 出去状态栏
        }
    }

    // 动态注册广播
    public int Battery() {
        BatteryReceiver receiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);// 监听电池变化的广播
        registerReceiver(receiver, filter);
        return m_Battery;
    }

    // 广播接受器
    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int current = intent.getExtras().getInt("level");// 获得当前电量
            int total = intent.getExtras().getInt("scale");// 获得总电量
            m_Battery = current * 100 / total;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = getDensity(context);
        return (int) (dipValue * scale + 0.5f);
    }

    // 获取手机大小，宽度，字体缩放和其一些其他信息
    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (context != null) {
            return getResources(context).getDisplayMetrics();
        }
        return null;
    }

    // 获取系统文件资源
    public static Resources getResources(Context context) {
        if (context != null) {
            return context.getResources();
        }
        return null;
    }

    // 获取手机大小，宽度，字体缩放和其一些其他信息
    public static float getDensity(Context context) {
        if (context != null) {
            return getDisplayMetrics(context).density;
        }
        return 0;
    }

    // 打电话功能
    public void sendCall(String strNumber) {
        if (!StringTool.isEmpty(strNumber)) {
            Uri uri = Uri.parse("tel:" + strNumber);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
        } else {
//            CToast.showMsg(getContext(), "电话号码不能为空");
        }
    }

    // 打开网址功能
    public void openBrowser(final String downUrl) {
        if (!StringTool.isEmpty(downUrl)) {
            Uri uri = Uri.parse(downUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
//            CToast.showMsg(getContext(), "网址不能为空");
        }
    }

    private void initContext() {
        m_Context = FrmActivity.this;
    }

    // 获取上下文对象
    public Context getContext() {
        return m_Context;
    }
}
