package com.example.h5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.h5.hybridTool.DateTool;
import com.example.h5.hybridTool.FileTool;
import com.example.h5.hybridTool.Rms;

import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint({"DefaultLocale", "NewApi"})
public class MainActivity extends FrmActivity {

    private HTML5WebView m_WebView;
    private Handler m_Handler;
//    private static final int TAKE_PICTURE = 3021, BARCODE = 3000;
//    private JsonData m_JsonObject;
//    private LocationClient m_LocationClient = null;
//    private MyReceiveListenner m_Listenner = null;
//    private boolean isOpenLocation = false;
//    private int m_GpsCount = 0;
//    private boolean isAirOn = false;
//    private FaceDetector mFaceDetector;
//    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        InitHander();// 初始化线程
        InitWebView();// 初始化浏览器
        InitVIew();// 初始化页面布局
    }

    @SuppressLint("HandlerLeak")
    private void InitHander() {
        m_Handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    default:
                        break;
                }
            }
        };
    }

    private void InitWebView() {
        m_WebView = new HTML5WebView(MainActivity.this);
        Log.i("H55", m_WebView + "");
        /**
         * 添加javascriptInterface
         * 第一个参数：这里需要一个与js映射的java对象
         * 第二个参数：该java对象被映射为js对象后在js里面的对象名，在js中要调用该对象的方法就是通过这个来调用，也就是js调用JavaScriptinterface里面的方法
         */
        // 注入Java对象到WebView中,要想和JS进行通信这一步必须有
        m_WebView.addJavascriptInterface(new JavaScriptinterface(MainActivity.this, m_Handler), "android");// 进行了handler的初始化
        loadUrl();// 读取本地的html文件
    }

    // 其中index.html就是js文件里面的程序的主入口
    // file:///data/user/0/com.example.h5/files/html/index.html
//    file:///data/user/0/com.example.h5/files/html/index.html
    private void loadUrl() {
        String htmlFile = "file://" + FileTool.getHtmlPath(getContext()) + "html/index.html";
        Log.i("H55", htmlFile);
        m_WebView.loadUrl(htmlFile);// 加载html文件
    }

    private void InitVIew() {
        setContentView(m_WebView.getLayout());// 获取html中的布局
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 解除系统锁屏
    }

    @Override
    protected void onRestart() {
        try {
            String baseInfo = Rms.getString(getContext(), Rms.DATA);
            JSONObject base = new JSONObject(baseInfo);
            if (!DateTool.CurrDate().equals(base.getString("login_date"))) {
//                CToast.showMsg(getContext(), "登录超时,请重新登录!");
                loadUrl();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        super.onRestart();
    }

    // 百度地图定位监听
//    public class MyReceiveListenner implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//
//        }
//    }

}