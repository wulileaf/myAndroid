package com.example.h5;

import android.app.Application;
import android.widget.TextView;

import org.zackratos.basemode.mvp.BaseApplication;

public class App extends BaseApplication {

    private static App mInstance = null;
    public boolean m_bKeyRight = true;
    // public BMapManager mBMapManager = null;
    private String type = null;
    private Object object = null;
    // 错误标记
    // private static String TAG = "locInfo";
    // 定位时间间隔
    private int myLocationTime = 1000 * 5;// LocationClientOption.MIN_SCAN_SPAN;
    // 是否启动了定位API
    private boolean isOpenLocation = false;
    private int getCount = 0;
    // 地理位置
//    private LocationClientOption option = null;
    public TextView locTv;
    //    private LocationClient mLocationClient = null; // 定位类
//    private MyReceiveListenner mListenner = null; // new MyReceiveListenner();
    private String[] mStrGps = new String[5];

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        mInstance = this;
    }

    private void init() {
//        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
    }
}
