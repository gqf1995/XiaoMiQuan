package com.xiaomiquan.greenDB;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.xiaomiquan.entity.bean.CoinMarketValue;
import com.xiaomiquan.entity.bean.kline.KLineBean;
import com.xiaomiquan.entity.bean.UserLogin;

import com.xiaomiquan.greenDB.CoinMarketValueDao;
import com.xiaomiquan.greenDB.KLineBeanDao;
import com.xiaomiquan.greenDB.UserLoginDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig coinMarketValueDaoConfig;
    private final DaoConfig kLineBeanDaoConfig;
    private final DaoConfig userLoginDaoConfig;

    private final CoinMarketValueDao coinMarketValueDao;
    private final KLineBeanDao kLineBeanDao;
    private final UserLoginDao userLoginDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        coinMarketValueDaoConfig = daoConfigMap.get(CoinMarketValueDao.class).clone();
        coinMarketValueDaoConfig.initIdentityScope(type);

        kLineBeanDaoConfig = daoConfigMap.get(KLineBeanDao.class).clone();
        kLineBeanDaoConfig.initIdentityScope(type);

        userLoginDaoConfig = daoConfigMap.get(UserLoginDao.class).clone();
        userLoginDaoConfig.initIdentityScope(type);

        coinMarketValueDao = new CoinMarketValueDao(coinMarketValueDaoConfig, this);
        kLineBeanDao = new KLineBeanDao(kLineBeanDaoConfig, this);
        userLoginDao = new UserLoginDao(userLoginDaoConfig, this);

        registerDao(CoinMarketValue.class, coinMarketValueDao);
        registerDao(KLineBean.class, kLineBeanDao);
        registerDao(UserLogin.class, userLoginDao);
    }
    
    public void clear() {
        coinMarketValueDaoConfig.clearIdentityScope();
        kLineBeanDaoConfig.clearIdentityScope();
        userLoginDaoConfig.clearIdentityScope();
    }

    public CoinMarketValueDao getCoinMarketValueDao() {
        return coinMarketValueDao;
    }

    public KLineBeanDao getKLineBeanDao() {
        return kLineBeanDao;
    }

    public UserLoginDao getUserLoginDao() {
        return userLoginDao;
    }

}
