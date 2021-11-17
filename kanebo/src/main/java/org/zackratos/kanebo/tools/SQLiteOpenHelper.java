package org.zackratos.kanebo.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;
import org.zackratos.kanebo.greendao.DaoMaster;
import org.zackratos.kanebo.greendao.DictionaryDao;
import org.zackratos.kanebo.greendao.ProductBeanDao;
import org.zackratos.kanebo.greendao.TestBeanDao;
import org.zackratos.kanebo.greendao.b_act_mainDao;

// 数据库升级管理工具
public class SQLiteOpenHelper extends DaoMaster.OpenHelper {

    public SQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, b_act_mainDao.class, DictionaryDao.class, ProductBeanDao.class, TestBeanDao.class);

    }
}
