package com.xiaomiquan.server;

import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.utils.SaveUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;

import static com.xiaomiquan.base.AppConst.httpBaseUrl;
import static com.xiaomiquan.base.AppConst.httpBaseUrl2;
import static com.xiaomiquan.base.AppConst.httpBaseUrl3;
import static com.xiaomiquan.base.AppConst.httpBaseUrl4;
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

    public static void setBaseUrl(String url) {
        SaveUtil.getInstance().saveString("baseUrl", url);
        ToastUtil.show(url);
        httpUrl = null;
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
    public String sendCodeForRegister = httpBaseUrl3 + userUrl + "/sendCodeForRegister";
    /**
     * 用户注册
     */
    public String saveUser = httpBaseUrl3 + userUrl + "/saveUser";
    /**
     * 用户登录
     */
    public String userLogin = httpBaseUrl4 + userUrl + "/userLogin";
    /**
     * 用户找回密码发送短信
     */
    public String sendCodeForForgotPassWord = httpBaseUrl3 + userUrl + "/sendCodeForForgotPassWord";
    /**
     * 用户找回密码
     */
    public String retrievePassword = httpBaseUrl3 + userUrl + "/retrievePassword";
    /**
     * 用户登出
     */
    public String loginOut = httpBaseUrl3 + userUrl + "/loginOut";


    /**
     * 行情
     */
    String dataUrl = "/data";

    /**
     * 获得所有库中拥有的交易所列表
     */
    public String getAllEXchange = httpBaseUrl + dataUrl + "/getAllEXchange";
    /**
     * 获得所有市值信息 未做排序
     */
    public String getAllMarketCaps = httpBaseUrl + dataUrl + "/getAllMarketCaps";
    /**
     * 获得所有市值和24小时价格走势信息 未做排序
     */
    public String getAllMarketCapVo = httpBaseUrl + dataUrl + "/getAllMarketCapVo";
    /**
     * 根据某个币种全称查询币种市值信息
     */
    public String getMarketCapById = httpBaseUrl + dataUrl + "/getMarketCapById";
    /**
     * 获得目前库中最新的Key对
     */
    public String getAllOnlyKeys = httpBaseUrl + dataUrl + "/getAllOnlyKeys";
    /**
     * 根据交易所名称获得相关信息
     */
    public String getAllMarketByExchange = httpBaseUrl + dataUrl + "/getAllMarketByExchange";
    /**
     * 根据币种名称获得相关信息
     */
    public String getAllMarketBySymbol = httpBaseUrl + dataUrl + "/getAllMarketBySymbol";
    /**
     * 获取K线
     */
    public String getKlineByOnlyKey = httpBaseUrl + dataUrl + "/getKlineByOnlyKey";
    /**
     * 获取汇率
     */
    public String getAllPriceRate = httpBaseUrl + dataUrl + "/getAllPriceRate";


    /**
     * 自选
     */
    String subsUrl = "/subs";
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String show = httpBaseUrl3 + subsUrl + "/operate/show";
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String subs = httpBaseUrl3 + subsUrl + "/operate/subs";//onlykey
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String unsubs = httpBaseUrl3 + subsUrl + "/operate/unsubs";
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String marketdata = httpBaseUrl3 + subsUrl + "/self/marketdata";
    /**
     * 单独订阅/取消
     */
    public String singlesubs = httpBaseUrl3 + subsUrl + "/operate/singlesubs";
    /**
     * 自定义排序
     */
    public String order = httpBaseUrl3 + subsUrl + "/operate/order";


    /**
     * websocket
     */
    String apiUrl = "/api";
    /**
     * 订阅
     */
    public String registerkeys = httpBaseUrl2 + apiUrl + "/registerkeys";
    /**
     * 取消订阅
     */
    public String unregisterkeys = httpBaseUrl2 + apiUrl + "/unregisterkeys";


    /**
     * 圈子
     */
    String groupUrl = "/group";

    /**
     * 获取加入圈子信息
     */
    public String getMyCircleInfo = httpBaseUrl4 + groupUrl + "/listGroupByPage";
    /**
     * 获取加入圈子信息
     */
    public String getCircleTopic = httpBaseUrl4 + "/articleTopic/listByPage";
    /**
     * 创建圈子
     */
    public String creatCircle = httpBaseUrl4 + "/group/saveGroup";
    /**
     * 修改圈子信息
     */
    public String editCircleInfo = httpBaseUrl4 + "/group/editGroup";
    /**
     * 生成邀请码
     */
    public String creatCircleCode = httpBaseUrl4 + "/group/createInviteCode";


    /**
     * 圈子申请
     */
    String groupMemberUrl = "/groupMember";

    /**
     * 申请加入圈子
     */
    public String joinCircle = httpBaseUrl4 + groupMemberUrl + "/applyJoinGroup";
    /**
     * 圈子禁言所有人
     */
    public String Circlebanned = httpBaseUrl4 + "/group/banned";
    /**
     * 解除禁言所有人
     */
    public String CircleancelBanned = httpBaseUrl4 + "/group/cancelBanned";
    /**
     * 圈子成员
     */
    public String findMember = httpBaseUrl4 + "/groupMember/findAllMember";
    /**
     * 圈子成员禁言
     */
    public String memberBanned = httpBaseUrl4 + "/groupMember/banned";
    /**
     * 圈子成员解除禁言
     */
    public String memberuUnBanned = httpBaseUrl4 + "/groupMember/unbanned";


    /**
     * 同意审核入圈申请
     */
    public String agreeCircleApply = httpBaseUrl4 + groupMemberUrl + "/agreeJoinApply";
    /**
     * 邀请好友入圈
     */
    public String getSomeBodyIn = httpBaseUrl4 + groupMemberUrl + "/getSomeBodyIn";

    /**
     * 获得帖子
     */
    public String getUsertopic = httpBaseUrl4 + "/articleTopic/listForGroupByPage";

    /**
     * 发帖子
     */
    public String saveArticle = httpBaseUrl4 + "/articleTopic/save";
    /**
     * 发帖子
     */
    public String getDetails = httpBaseUrl4 + "/articleTopic/detail";


    /**
     * 文章
     */
    String articleUrl = "/article";
    /**
     * 文章列表
     */
    public String listArticleByPage = httpBaseUrl4 + articleUrl + "/listArticleByPage";

    /**
     * 发表评论
     */
    public String saveComment = httpBaseUrl4 + "/comment/saveComment";
    /**
     * 点赞
     */
    public String savePraise = httpBaseUrl4 + "/praise/praiseOrCancel";

    /**
     * 获取广场朋友圈
     */
    public String getSquareLive = httpBaseUrl4 + "/articleTopic/listByPage";
    /**
     * 获取大V
     */
    public String getBigVlist = httpBaseUrl4 + "/user/listBigVByPage";
    /**
     * 关注
     */
    public String attention = httpBaseUrl4 + "/userAttention/save";


    /**
     * 获取文章
     */
    public String getArticle = httpBaseUrl4 + "/articleTopic/listArticleByPage";
    /**
     * 获取文章
     */
    public String getCircleMore = httpBaseUrl4 + "/group/listMoreGroupByPage";
    /**
     * 获取用户主页信息
     */
    public String getUserInfo = httpBaseUrl4 + "/user/personCenter";
    /**
     * 获取粉丝列表
     */
    public String getFans = httpBaseUrl4 + "/userAttention/attentionMyList";
    /**
     * 获取关注列表
     */
    public String getAttention = httpBaseUrl4 + "/user/listMyAttentionByPage";


}
