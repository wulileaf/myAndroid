package org.zackratos.basemode.mvp;

import android.app.Application;
import android.content.Context;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.db.table.TableEntity;
import org.xutils.x;



/**
 * @Date 2017/9/4
 * @author 丁志祥
 * @version 1.0
 * @Note 初始化xUtils3，初始化xUtils3数据库，添加okHttp，获取全局context
 */
public class BaseApplication extends Application {

    private static BaseApplication mContext;
    public static DbManager db;//初始化数据库管理器
    private DbManager.DaoConfig daoConfig;
    private TableEntity entity;

    // -----------------------------GreenDao-----------------------
//    private DaoMaster.DevOpenHelper mHelper;
//    private SQLiteDatabase sdb;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;
    // ------------------------------------------------------------


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        ApplicationHelper.getInstance().getApplicationContext();

        initXutils();
//        initSmallVideo(this);

        // 初始化OKHttp
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//        OkHttpUtils.initClient(okHttpClient);

//        setDatabase();


        // 应用奔溃日志记录(2019/12/31)
        CrashHandler.getInstance().init(getApplicationContext());

        // 初始化二维码扫描界面
        ZXingLibrary.initDisplayOpinion(this);
    }

    /*** 单例模式 ***/
//    public static BaseApplication getInstances() {
//        return instances;
//    }

    /* 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。*/

//    private void setDatabase() {
//        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
//        db = mHelper.getWritableDatabase();
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return sdb;
//    }

    /**
     * 初始化xUtils3
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
        LogUtil.customTagPrefix = "as";
        initXtilsDB();
    }

    /**
     * 初始化xUtils3数据库
     */
    // 判断是否需要升级数据库
//    private void dbVersionControll(int oldVersion,int newVersion){
//        // 使用for实现跨版本升级数据库
//        for (int i = oldVersion; i < newVersion; i++) {
//            switch (i) {
//                case 1:{
//                    upgradeToVersion2(db);
//                }
//                break;
//                default:
//                    break;
//            }
//        }
//    }


    private void initXtilsDB() {
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("AS")
                .setAllowTransaction(false).setDbOpenListener(new DbManager.DbOpenListener() {
            @Override
            public void onDbOpened(DbManager db) {
                // 开启WAL, 对写入加速提升巨大
                db.getDatabase().enableWriteAheadLogging();
            }
        }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            /*
             * 更新数据
             */
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
//                db.addColumn(Sign.class,"test");// 添加新字段
//                db.dropTable(DeliverMailInfo.class);// 删除表
//                db.getTable().tableIsExist()// 创建表
//                alter table ss add(str1,int)
            }
        }).setDbVersion(1);
        db = x.getDb(daoConfig);
    }

    /**
     * 获取全局context
     */
    public static Context getContext() {
        return mContext;
    }

    public static BaseApplication getInstance() {
        return mContext;
    }

    /**
     * 判断这个表是否存在 不存在就创建表
     */
//    public <T> void creatTable(DbManager db, String tableName, Class<T> tableBean) {
//        try {
//            if (!entity.tableIsExist()) { //!tabIsExist(db.getDatabase(), tableName)
//                SqlInfo sqlInfo;
//                sqlInfo = SqlInfoBuilder.buildCreateTableSqlInfo(db
//                        .getTable(tableBean));
//                db.execNonQuery(sqlInfo);
//            }
//        } catch (DbException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }


//    public static void initSmallVideo(Context context) {
//        // 设置拍摄视频缓存路径
//        File dcim = Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        if (DeviceUtils.isZte()) {
//            if (dcim.exists()) {
//                VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
//            } else {
//                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
//                        "/sdcard-ext/")
//                        + "/mabeijianxi/");
//            }
//        } else {
//            VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
//        }
//        VCamera.setDebugMode(true);
//        VCamera.initialize(context);
//    }
}
