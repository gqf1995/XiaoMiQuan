package com.xiaomiquan.greenDaoUtils;

import android.app.Activity;
import android.content.Intent;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.SaveUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.just.agentweb.AgentWebConfig;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.xiaomiquan.server.HttpUrl;

import java.util.List;


/**
 * Created by 郭青枫 on 2017/10/13.
 * <p>
 * 获取唯一用户信息 工具
 */

public class SingSettingDBUtil {

    private static String isLogin = "";//0 未登陆 //1 已登录

    //跳转登录页
    public static boolean isLogin(Activity activity) {
        checkIsLogin(activity);
        if ("0".equals(isLogin)) {
            return false;
        } else {
            return true;
        }
    }
    //弹出需要登录
    public static boolean isLogin() {
        checkIsLogin();
        if ("0".equals(isLogin)) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
            return false;
        } else {
            return true;
        }
    }

    private static void checkIsLogin() {
        if (getUserLogin() == null) {
            isLogin = "0";
            return;
        }
        isLogin = "1";
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
        //用户登录信息统一 清除
        delectUserLogin();
        //清除消息通知
        DaoManager.getInstance().getDaoSession().getMessageInfoDao().deleteAll();
        isLogin = "";
        HttpUrl.getIntance().delectUidAndToken();
        AgentWebConfig.removeAllCookies();
    }


    //登录信息保存
    public static void setNewUserLogin(UserLogin userLogin) {
        //获取到用户基本信息,保存在数据库
        if (userLogin != null) {
            //插入
            DaoManager.getInstance().getDaoSession().getUserLoginDao().insertOrReplace(userLogin);
            HttpUrl.getIntance().saveUid(userLogin.getId() + "");
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
