package com.xiaomiquan.server;

import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.utils.SaveUtil;

import static com.xiaomiquan.base.AppConst.httpBaseUrl;
import static com.xiaomiquan.base.AppConst.httpBaseUrl2;
import static com.xiaomiquan.base.AppConst.isEditUrl;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class HttpUrl {

    static HttpUrl httpUrl = new HttpUrl();

    public static HttpUrl getIntance() {
        if (httpUrl == null) {
            httpUrl = new HttpUrl();
        }
        return httpUrl;
    }

    public static final String baseUrl = httpBaseUrl;

    public static String getBaseUrl() {
        if (isEditUrl) {
            if (!TextUtils.isEmpty(SaveUtil.getInstance().getString("baseUrl"))) {
                return SaveUtil.getInstance().getString("baseUrl");
            }
        }
        return baseUrl;
    }

    public void saveUidAndToken(String uid, String token) {
        SaveUtil.getInstance().saveString("uid", uid + "");
        SaveUtil.getInstance().saveString("token", token + "");
    }

    public void saveUid(String uid) {
        SaveUtil.getInstance().saveString("uid", uid + "");
    }

    public String getUid() {
        return SaveUtil.getInstance().getString("uid");
    }

    public void saveToken(String token) {
        SaveUtil.getInstance().saveString("token", token + "");
    }

    public String getToken() {
        return SaveUtil.getInstance().getString("token");
    }

    public void delectUidAndToken() {
        SaveUtil.getInstance().remove("uid");
        SaveUtil.getInstance().remove("token");
    }

    /**
     * 用户
     */
    String userUrl = "/user";

    /**
     * 用户注册发送验证码
     */
    public String sendCodeForRegister = httpBaseUrl2 + userUrl + "/sendCodeForRegister";
    /**
     * 用户注册
     */
    public String saveUser = httpBaseUrl2 + userUrl + "/saveUser";
    /**
     * 用户登录
     */
    public String userLogin = httpBaseUrl2 + userUrl + "/userLogin";
    /**
     * 用户找回密码发送短信
     */
    public String sendCodeForForgotPassWord = httpBaseUrl2 + userUrl + "/sendCodeForForgotPassWord";
    /**
     * 用户找回密码
     */
    public String retrievePassword = httpBaseUrl2 + userUrl + "/retrievePassword";
    /**
     * 用户登出
     */
    public String loginOut = httpBaseUrl2 + userUrl + "/loginOut";


    /**
     * 行情
     */
    String dataUrl = "/data";

    /**
     * 获得所有库中拥有的交易所列表
     */
    public String getAllEXchange = getBaseUrl() + dataUrl + "/getAllEXchange";
    /**
     * 获得所有市值信息 未做排序
     */
    public String getAllMarketCaps = getBaseUrl() + dataUrl + "/getAllMarketCaps";
    /**
     * 获得所有市值和24小时价格走势信息 未做排序
     */
    public String getAllMarketCapVo = getBaseUrl() + dataUrl + "/getAllMarketCapVo";
    /**
     * 根据某个币种全称查询币种市值信息
     */
    public String getMarketCapById = getBaseUrl() + dataUrl + "/getMarketCapById";
    /**
     * 获得目前库中最新的Key对
     */
    public String getAllOnlyKeys = getBaseUrl() + dataUrl + "/getAllOnlyKeys";
    /**
     * 根据交易所名称获得相关信息
     */
    public String getAllMarketByExchange = getBaseUrl() + dataUrl + "/getAllMarketByExchange";
    /**
     * 获取K线
     */
    public String getKlineByOnlyKey = getBaseUrl() + dataUrl + "/getKlineByOnlyKey";


    /**
     * 圈子
     */
    String groupUrl = "/group";
    /**
     * 获取未加入圈子信息
     */
    public String getMoreCircle=getBaseUrl()+groupUrl+"/listMoreGroupByPage";
    /**
     * 获取加入圈子信息
     */
    public String getMyCircleInfo=getBaseUrl()+groupUrl+"/listGroupByPage";
    /**
     * 创建圈子
     */
    public String creatCircle=getBaseUrl()+groupUrl+"/saveGroup";


    /**
     * 圈子申请
     */
    String groupMemberUrl="/groupMember";

    /**
     * 申请加入圈子
     */
    public String joinCircle=getBaseUrl()+groupMemberUrl+"/applyJoinGroup";
    /**
     * 获取待审核入圈申请
     */
    public String joinCircleApply=getBaseUrl()+groupMemberUrl+"/applyJoinGroup";
    /**
     * 同意审核入圈申请
     */
    public String agreeCircleApply=getBaseUrl()+groupMemberUrl+"/agreeJoinApply";
    /**
     * 邀请好友入圈
     */
    public String getSomeBodyIn=getBaseUrl()+groupMemberUrl+"/getSomeBodyIn";


}
