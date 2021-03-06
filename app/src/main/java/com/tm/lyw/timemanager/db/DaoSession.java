package com.tm.lyw.timemanager.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.tm.lyw.timemanager.db.TimeBean;

import com.tm.lyw.timemanager.db.TimeBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig timeBeanDaoConfig;

    private final TimeBeanDao timeBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        timeBeanDaoConfig = daoConfigMap.get(TimeBeanDao.class).clone();
        timeBeanDaoConfig.initIdentityScope(type);

        timeBeanDao = new TimeBeanDao(timeBeanDaoConfig, this);

        registerDao(TimeBean.class, timeBeanDao);
    }
    
    public void clear() {
        timeBeanDaoConfig.getIdentityScope().clear();
    }

    public TimeBeanDao getTimeBeanDao() {
        return timeBeanDao;
    }

}
