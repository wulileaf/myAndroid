package org.zackratos.kanebo;

import android.content.IntentFilter;
import org.greenrobot.greendao.database.Database;
import org.zackratos.basemode.mvp.BaseApplication;
import org.zackratos.kanebo.greendao.DaoMaster;
import org.zackratos.kanebo.greendao.DaoSession;

public class App extends BaseApplication {

    private DaoSession daoSession;
    private IntentFilter intentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        //参数1：上下文
        //参数2：String数据库名字
        // GreenDao数据库初始化
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "opple");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        // 注册网络变化监听
        initNetworChanges();
    }

    // 注册网络变化监听
    public IntentFilter initNetworChanges() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        return intentFilter;
    }

    // 获取GreenDao数据库存储对象
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
