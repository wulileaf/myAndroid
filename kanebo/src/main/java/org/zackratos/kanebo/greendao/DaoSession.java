package org.zackratos.kanebo.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import org.zackratos.kanebo.bean.b_act_main;
import org.zackratos.kanebo.greendao.Dictionary;
import org.zackratos.kanebo.greendao.ProductBean;
import org.zackratos.kanebo.greendao.TestBean;
import org.zackratos.kanebo.greendao.UserMsg;

import org.zackratos.kanebo.greendao.b_act_mainDao;
import org.zackratos.kanebo.greendao.DictionaryDao;
import org.zackratos.kanebo.greendao.ProductBeanDao;
import org.zackratos.kanebo.greendao.TestBeanDao;
import org.zackratos.kanebo.greendao.UserMsgDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig b_act_mainDaoConfig;
    private final DaoConfig dictionaryDaoConfig;
    private final DaoConfig productBeanDaoConfig;
    private final DaoConfig testBeanDaoConfig;
    private final DaoConfig userMsgDaoConfig;

    private final b_act_mainDao b_act_mainDao;
    private final DictionaryDao dictionaryDao;
    private final ProductBeanDao productBeanDao;
    private final TestBeanDao testBeanDao;
    private final UserMsgDao userMsgDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        b_act_mainDaoConfig = daoConfigMap.get(b_act_mainDao.class).clone();
        b_act_mainDaoConfig.initIdentityScope(type);

        dictionaryDaoConfig = daoConfigMap.get(DictionaryDao.class).clone();
        dictionaryDaoConfig.initIdentityScope(type);

        productBeanDaoConfig = daoConfigMap.get(ProductBeanDao.class).clone();
        productBeanDaoConfig.initIdentityScope(type);

        testBeanDaoConfig = daoConfigMap.get(TestBeanDao.class).clone();
        testBeanDaoConfig.initIdentityScope(type);

        userMsgDaoConfig = daoConfigMap.get(UserMsgDao.class).clone();
        userMsgDaoConfig.initIdentityScope(type);

        b_act_mainDao = new b_act_mainDao(b_act_mainDaoConfig, this);
        dictionaryDao = new DictionaryDao(dictionaryDaoConfig, this);
        productBeanDao = new ProductBeanDao(productBeanDaoConfig, this);
        testBeanDao = new TestBeanDao(testBeanDaoConfig, this);
        userMsgDao = new UserMsgDao(userMsgDaoConfig, this);

        registerDao(b_act_main.class, b_act_mainDao);
        registerDao(Dictionary.class, dictionaryDao);
        registerDao(ProductBean.class, productBeanDao);
        registerDao(TestBean.class, testBeanDao);
        registerDao(UserMsg.class, userMsgDao);
    }
    
    public void clear() {
        b_act_mainDaoConfig.clearIdentityScope();
        dictionaryDaoConfig.clearIdentityScope();
        productBeanDaoConfig.clearIdentityScope();
        testBeanDaoConfig.clearIdentityScope();
        userMsgDaoConfig.clearIdentityScope();
    }

    public b_act_mainDao getB_act_mainDao() {
        return b_act_mainDao;
    }

    public DictionaryDao getDictionaryDao() {
        return dictionaryDao;
    }

    public ProductBeanDao getProductBeanDao() {
        return productBeanDao;
    }

    public TestBeanDao getTestBeanDao() {
        return testBeanDao;
    }

    public UserMsgDao getUserMsgDao() {
        return userMsgDao;
    }

}
