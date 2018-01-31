package com.xiaomiquan.server;

import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.utils.SaveUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;

import static com.xiaomiquan.base.AppConst.httpBaseUrl;
import static com.xiaomiquan.base.AppConst.httpBaseUrl2;
import static com.xiaomiquan.base.AppConst.httpBaseUrl3;
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
    public String userLogin = httpBaseUrl3 + userUrl + "/userLogin";
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
     * 根据币种名称获得相关信息
     */
    public String getAllMarketBySymbol = getBaseUrl() + dataUrl + "/getAllMarketBySymbol";
    /**
     * 获取K线
     */
    public String getKlineByOnlyKey = getBaseUrl() + dataUrl + "/getKlineByOnlyKey";
    /**
     * 获取汇率
     */
    public String getAllPriceRate = getBaseUrl() + dataUrl + "/getAllPriceRate";
    /**
     * 搜索
     */
    public String getAllMarketByExchangeOrSymbol = getBaseUrl() + dataUrl + "/getAllMarketByExchangeOrSymbol";


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
     * 获取未加入圈子信息
     */
    public String getMoreCircle = getBaseUrl() + groupUrl + "/listMoreGroupByPage";
    /**
     * 获取加入圈子信息
     */
    public String getMyCircleInfo = getBaseUrl() + groupUrl + "/listGroupByPage";
    /**
     * 创建圈子
     */
    public String creatCircle = getBaseUrl() + groupUrl + "/saveGroup";


    /**
     * 圈子申请
     */
    String groupMemberUrl = "/groupMember";

    /**
     * 申请加入圈子
     */
    public String joinCircle = getBaseUrl() + groupMemberUrl + "/applyJoinGroup";
    /**
     * 获取待审核入圈申请
     */
    public String joinCircleApply = getBaseUrl() + groupMemberUrl + "/applyJoinGroup";
    /**
     * 同意审核入圈申请
     */
    public String agreeCircleApply = getBaseUrl() + groupMemberUrl + "/agreeJoinApply";
    /**
     * 邀请好友入圈
     */
    public String getSomeBodyIn = getBaseUrl() + groupMemberUrl + "/getSomeBodyIn";

    /**
     * 获得帖子
     */
    public String getUsertopic = getBaseUrl() + "/usertopic/list/group/usertopic";

    /**
     * 发帖子
     */
    public String saveUsertopic = getBaseUrl() + "/usertopic/save";


    /**
     * 文章
     */
    String articleUrl = "/article";
    /**
     * 文章列表
     */
    public String listArticleByPage = getBaseUrl() + articleUrl + "/listArticleByPage";

    /**
     * 发表评论
     */
    public String saveComment = getBaseUrl() + "/comment/save";
    /**
     * 点赞
     */
    public String savePraise = getBaseUrl() + "/praise/save";


    /**
     * 组合跟投
     */
    String demoUrl = "/demo";
    /**
     * 我的组合列表
     */
    public String listDemo = httpBaseUrl3 + demoUrl + "/listDemo";
    /**
     * 创建组合
     */
    public String save = httpBaseUrl3 + demoUrl + "/save";
    /**
     * 申请买卖
     */
    public String deal = httpBaseUrl3 + demoUrl + "/deal";
    /**
     * 成交历史
     */
    public String listDeal = httpBaseUrl3 + demoUrl + "/listDeal";
    /**
     * 委托历史
     */
    public String history = httpBaseUrl3 + demoUrl + "/history";
    /**
     * 持仓明细
     */
    public String listPosition = httpBaseUrl3 + demoUrl + "/listPosition";
    /**
     * 今日收益
     */
    public String getTodayInfo = httpBaseUrl3 + demoUrl + "/getTodayInfo";
    /**
     * 分期收益
     */
    public String adllRate = httpBaseUrl3 + demoUrl + "/adllRate";
    /**
     * 币种列表
     */
    public String searchCoin = httpBaseUrl3 + demoUrl + "/searchCoin";
    /**
     * 收益走势
     */
    public String rateTrend = httpBaseUrl3 + demoUrl + "/rateTrend";
    /**
     * 日均操作次数
     */
    public String getTransCount = httpBaseUrl3 + demoUrl + "/getTransCount";
    /**
     * 获取余额
     */
    public String getBalance = httpBaseUrl3 + demoUrl + "/getBalance";

}
