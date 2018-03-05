package com.xiaomiquan.utils;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.kline.KLineBean;
import com.xiaomiquan.greenDB.KLineBeanDao;
import com.xiaomiquan.greenDaoUtils.DaoManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/29 0029.
 */

public class KlineDaoUtil {

    public static final int selectNum = 180;

    public static List<KLineBean> getList(String onlyKey, String time) {
        QueryBuilder<KLineBean> kLineBeanQueryBuilder = DaoManager.getInstance().getDaoSession().getKLineBeanDao()
                .queryBuilder()
                .where(KLineBeanDao.Properties.Key.eq(onlyKey + time))
                .orderAsc(KLineBeanDao.Properties.Timestamp);
        int count = (int) kLineBeanQueryBuilder.buildCount().count();
        if (count == 0) {
            return new ArrayList<>();
        } else {
            if (count > selectNum) {
                return kLineBeanQueryBuilder.list();
            } else {
                return kLineBeanQueryBuilder.limit(selectNum)
                        .offset(count - selectNum)
                        .list();
            }
        }
    }

    public static void addKlineList(List<KLineBean> kLineBeans, String onlyKey, String time) {
        for (int i = 0; i < kLineBeans.size(); i++) {
            kLineBeans.get(i).key = onlyKey + time;
            //添加到数据库
            DaoManager.getInstance().getDaoSession().getKLineBeanDao()
                    .save(kLineBeans.get(i));
        }
    }

    public static void delectHistory(String key) {
        String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_kline_value);
        for (int i = 0; i < stringArray.length; i++) {
            List<KLineBean> list = DaoManager.getInstance().getDaoSession().getKLineBeanDao()
                    .queryBuilder()
                    .where(KLineBeanDao.Properties.Key.eq(key + stringArray[i]))
                    .orderAsc(KLineBeanDao.Properties.Timestamp)
                    .list();
            if (list.size() > 0) {
                long time = System.currentTimeMillis()/1000;
                for (int j = 0; j < list.size(); j++) {
                    //删除 540条以前
                    if (i == 0) {
                        time = time - 60 * selectNum * 1;
                    } else if (i == 1) {
                        time = time - 60 * 3 * selectNum * 1;
                    } else if (i == 2) {
                        time = time - 60 * 5 * selectNum * 1;
                    } else if (i == 3) {
                        time = time - 60 * 15 * selectNum * 1;
                    } else if (i == 4) {
                        time = time - 60 * 30 * selectNum * 1;
                    } else if (i == 5) {
                        time = time - 60 * 60 * selectNum * 1;
                    } else if (i == 6) {
                        time = time - 60 * 60 * 2 * selectNum * 1;
                    } else if (i == 7) {
                        time = time - 60 * 60 * 4 * selectNum * 1;
                    } else if (i == 8) {
                        time = time - 60 * 60 * 6 * selectNum * 1;
                    } else if (i == 9) {
                        time = time - 60 * 60 * 12 * selectNum * 1;
                    } else if (i == 10) {
                        time = time - 60 * 60 * 24 * selectNum * 1;
                    } else if (i == 11) {
                        time = time - 60 * 60 * 24 * 3 * selectNum * 1;
                    } else if (i == 12) {
                        time = time - 60 * 60 * 24 * 7 * selectNum * 1;
                    }
                    if (list.get(j).timestamp < time) {
                        DaoManager.getInstance().getDaoSession().getKLineBeanDao().delete(list.get(j));
                    }
                }
            }
        }

    }

    public static void addKline(KLineBean kLineBean, String key) {
        kLineBean.key = key;
        DaoManager.getInstance().getDaoSession().getKLineBeanDao()
                .save(kLineBean);
    }

    public static void updataKline(KLineBean kLineBean, String key) {
        QueryBuilder<KLineBean> qb = DaoManager.getInstance().getDaoSession().getKLineBeanDao().queryBuilder();
        qb.where(KLineBeanDao.Properties.Key.eq(key), KLineBeanDao.Properties.Timestamp.eq(kLineBean.timestamp));
        List<KLineBean> daoDatas = qb.list();
        if (daoDatas.size() > 0) {
            kLineBean.setId(daoDatas.get(0).getId());
            DaoManager.getInstance().getDaoSession().getKLineBeanDao().update(kLineBean);
        }
    }

}
