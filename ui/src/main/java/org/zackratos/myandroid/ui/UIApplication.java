package org.zackratos.myandroid.ui;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class UIApplication extends Application {


    // -----------------------------GreenDao-----------------------
//    private DaoMaster.DevOpenHelper mHelper;
//    private SQLiteDatabase db;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;
    // -----------------------------------------------------

    @Override
    public void onCreate() {
        super.onCreate();
//        instances = this;
//        setDatabase();
    }

    /*** 单例模式 ***/
//    public static UIApplication getInstances() {
//        return instances;
//    }

     /* 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。*/

//    private void setDatabase() {
//        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
//        db = mHelper.getWritableDatabase();  mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }

}
