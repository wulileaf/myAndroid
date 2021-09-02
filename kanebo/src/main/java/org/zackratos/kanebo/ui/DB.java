package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.App;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.greendao.DaoSession;
import org.zackratos.kanebo.greendao.TestBean;
import org.zackratos.kanebo.greendao.TestBeanDao;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


// 数据库
// 参考资料链接：
//DaoSession.clear();//清除所有数据库表的缓存
//dao.detachAll()；//清除某个表的所有缓存

// 创建表步骤
// 1
//@Entity(nameInDb = "product")
//public class ProductBean {
//
//    @Id(autoincrement = true)
//    private Long id;
//    private String serverId;
//    private String productId;
//    private String productCode;

// 2
// Build--Make Project


public class DB extends BaseActivity {

    // 一个Bean类对应一个Dao
    Query<TestBean> userQuery;// Bean类
    TestBeanDao userDao;// Dao类


    @BindView(R.id.charu)
    Button charu;
    @BindView(R.id.shanchu)
    Button shanchu;
    @BindView(R.id.xiugai)
    Button xiugai;
    @BindView(R.id.chaxun)
    Button chaxun;

    @Override
    protected int initView() {
        return R.layout.atc_database;
    }

    @Override
    protected void initData() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        userDao = daoSession.getTestBeanDao();// 获取数据库对象
        // 返回实体集合升序排列
        userQuery = userDao.queryBuilder().orderAsc(TestBeanDao.Properties.Id).build();

//        insertUser();
//        List<TestBean> users = queryList();
//        Toast.makeText(this, users.get(0).getName(), Toast.LENGTH_SHORT).show();
    }

    // 插入一条数据
    private void insertUser() {
//        TestBean user = new TestBean(null, "ash", "男", 26, 50000);
//        userDao.insert(user);
    }

    // 查询数据
    private List<TestBean> queryList() {
        List<TestBean> users = userQuery.list();
        for (int i = 0; i < users.size(); i++) {
            Log.i("DBGao", users.get(i).getName() + "-------" + users.get(i).getSex() + "----------" + users.get(i).getId());
        }
        return users;
    }

    // 删除数据
//    mBeanDao.delete(new Bean(100l, date, 12+"",10));删除一条数据，按对象
//    mBeanDao.deleteByKey();删除一条数据,根据Key 也就是id
//    mBeanDao.deleteAll();删除所有数据
//    mBeanDao.deleteInTx();删除一组数据(对象)
//    mBeanDao.deleteByKeyInTx();删除一组数据(根据id)
    private void deleteUser() {
        userDao.deleteByKey(1l);

    }

    // 修改数据
    private void updateUser() {
        //查询id是1位置的数据
//        TestBean user = userDao.load(1l);
//        //对其进行修改
//        user.setName("~~");
//        userDao.update(user);
    }

    // 条件查询
    // eq就是=的意思
    // 方式1
//    UserInfoTable infoTable = tableDao.queryBuilder().
//            where(UserInfoTableDao.Properties.Id.eq(userId)).unique();
    // 方式2
//    UserInfoTable infoTable2 =   tableDao.queryBuilder().
//            where(new WhereCondition.PropertyCondition(UserInfoTableDao.Properties.Id, "=?", userId)).unique();
    private List<TestBean> queryByName(String name, String sex) {
        QueryBuilder<TestBean> builder = userDao.queryBuilder();
        Query<TestBean> query = builder
                .where(TestBeanDao.Properties.Name.eq(name), TestBeanDao.Properties.Sex.eq(sex))
                .build();
        List<TestBean> list = query.list();
        return list;
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }


    //    save()、insert()、insertOrReplace()的区别：
//    方法	说明
//    save()	根据主键id判断是更新还是新增
//    insert()	单纯的保存，什么都没有
//    insertOrReplace()	有就更新，没有就新增
    @OnClick({R.id.charu, R.id.shanchu, R.id.xiugai, R.id.chaxun})
    public void click(View view) {
        switch (view.getId()) {
            // 如果id等于null，那么插入数据库中的数据的id会自动自己增加
            // 如果根据ID去删除对应表中的数据，其他数据的id并不会发生改变
            case R.id.charu:// 插入（增加） 测试OK
                TestBean user = new TestBean(null, "leaf", "男", 30, 400000);
                userDao.insert(user);
                break;
                // 删除的思路，还可以先查询，在删除
            case R.id.shanchu:// 删除
//                TestBean testBean = new TestBean();
//                userDao.delete();
                userDao.deleteByKey(2l);
//                queryList();
                break;
            case R.id.xiugai:// 修改
                TestBean userData = userDao.load(1l);
                userData.setName("~~");
                userDao.update(userData);
//                queryList();
                break;
            case R.id.chaxun:// 查询  测试OK
//                List<TestBean> users = userQuery.list();
                List<TestBean> users = queryByName("光平", "女");
                Log.i("DBGao", users.get(0).getName() + users.get(0).getId());
                break;
            default:
                break;
        }
    }


}
