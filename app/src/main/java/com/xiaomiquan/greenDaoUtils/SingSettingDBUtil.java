package com.xiaomiquan.greenDaoUtils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.utils.SaveUtil;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.xiaomiquan.server.HttpUrl;

import java.util.List;


/**
 * Created by 郭青枫 on 2017/10/13.
 */

public class SingSettingDBUtil {

    private static String isLogin = "";//0 未登陆 //1 已登录

    public static boolean isLogin(Activity activity) {
        checkIsLogin(activity);
        if ("0".equals(isLogin)) {
            return false;
        } else {
            return true;
        }
    }

    private static void checkIsLogin(Activity activity) {
        if (getUserLogin() == null) {
            isLogin = "0";
            activity.startActivity(new Intent(activity, LoginAndRegisteredActivity.class));
            return;
        }
        isLogin = "1";
    }

    public static void logout() {
        delectUserLogin();
        isLogin = "";
        HttpUrl.getIntance().delectUidAndToken();
    }


    //登录信息保存
    public static void setNewUserLogin(UserLogin userLogin) {
        //获取到用户基本信息,保存在数据库
        if (userLogin != null) {
            if (!TextUtils.isEmpty(userLogin.getPhone())) {
//                if (DaoManager.getInstance().getDaoSession().getUserLoginDao().queryBuilder().list().size() != 0) {
//                    //如果有先删除
//                    delectUserLogin();
//                }
                //插入
                DaoManager.getInstance().getDaoSession().getUserLoginDao().insertOrReplace(userLogin);
                HttpUrl.getIntance().saveUid(userLogin.getId() + "");
            }
        }
    }

    //登录信息获取
    public static UserLogin getUserLogin() {
        if (DaoManager.getInstance().getDaoSession().getUserLoginDao().queryBuilder().list() != null) {
            if (DaoManager.getInstance().getDaoSession().getUserLoginDao().queryBuilder().list().size() != 0) {
                List<UserLogin> userLogins = DaoManager.getInstance().getDaoSession().getUserLoginDao().queryBuilder().list();
                for (int i = 0; i < userLogins.size(); i++) {
                    if ((userLogins.get(i).getId() + "").equals(SaveUtil.getInstance().getString("uid"))) {
                        return userLogins.get(i);
                    }
                }
            }
        }
        return null;
    }


    //登录信息删除
    public static void delectUserLogin() {
        if (DaoManager.getInstance().getDaoSession().getUserLoginDao().queryBuilder().list().size() != 0) {
            DaoManager.getInstance().getDaoSession().getUserLoginDao().deleteAll();
        }
    }

}
