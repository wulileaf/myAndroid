package org.zackratos.kanebo;

import android.app.Application;
import android.content.IntentFilter;
import android.util.Log;

//import com.igexin.sdk.IUserLoggerInterface;
//import com.igexin.sdk.PushManager;
import com.arialyy.aria.core.Aria;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.xuexiang.xui.XUI;

import org.greenrobot.greendao.database.Database;
import org.zackratos.basemode.mvp.BaseApplication;
import org.zackratos.kanebo.broadcastHandle.BroadCastHandle;
import org.zackratos.kanebo.greendao.DaoMaster;
import org.zackratos.kanebo.greendao.DaoSession;
import org.zackratos.kanebo.tools.SQLiteOpenHelper;

import cn.jpush.android.api.JPushInterface;

public class App extends BaseApplication {

    private DaoSession daoSession;
    private BroadCastHandle broadCastHandle = new BroadCastHandle();

    @Override
    public void onCreate() {
        super.onCreate();
        //参数1：上下文
        //参数2：String数据库名字
        // GreenDao数据库初始化
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "opple");
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this, "opple");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        // 注册网络变化监听
        broadCastHandle.initNetworChanges();

        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志

//        com.igexin.sdk.PushManager.getInstance().initialize(this);
//        com.igexin.sdk.PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
//            @Override
//            public void log(String s) {
//                Log.i("PUSH_LOG",s);
//            }
//        });

        JPushInterface.setDebugMode(true);// 初始化极光
        JPushInterface.init(this);// 初始化极光


        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);

        // Aria注册
        Aria.download(this).register();
    }

    // 获取GreenDao数据库存储对象
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
