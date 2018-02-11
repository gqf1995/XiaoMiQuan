package com.xiaomiquan.server;

import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.utils.SaveUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;

import static com.xiaomiquan.base.AppConst.httpBaseUrl;
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
    public String sendCodeForRegister = getBaseUrl() + userUrl + "/sendCodeForRegister";
    /**
     * 用户注册
     */
    public String saveUser = getBaseUrl() + userUrl + "/saveUser";
    /**
     * 用户登录
     */
    public String userLogin = getBaseUrl() + userUrl + "/userLogin";
    /**
     * 用户找回密码发送短信
     */
    public String sendCodeForForgotPassWord = getBaseUrl() + userUrl + "/sendCodeForForgotPassWord";
    /**
     * 用户找回密码
     */
    public String retrievePassword = getBaseUrl() + userUrl + "/retrievePassword";
    /**
     * 用户登出
     */
    public String loginOut = getBaseUrl() + userUrl + "/loginOut";
    /**
     * 用户信息修改
     */
    public String editUserInfo = getBaseUrl() + userUrl + "/editUserInfo";
    /**
     * 获取用户主页信息
     */
    public String personCenter = getBaseUrl() + userUrl + "/personCenter";
    /**
     * 版本更新
     */
    public String getlatestversion = getBaseUrl() + "/appversion/getlatestversion";


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
     * 币种资料
     */
    public String getSymbolInfomation = getBaseUrl() + dataUrl + "/getSymbolInfomation";


    /**
     * 自选
     */
    String subsUrl = "/subs";
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String show = getBaseUrl() + subsUrl + "/operate/show";
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String subs = getBaseUrl() + subsUrl + "/operate/subs";//onlykey
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String unsubs = getBaseUrl() + subsUrl + "/operate/unsubs";
    /**
     * 点击横栏上交易所或币种显示信息
     */
    public String marketdata = getBaseUrl() + subsUrl + "/self/marketdata";
    /**
     * 单独订阅/取消
     */
    public String singlesubs = getBaseUrl() + subsUrl + "/operate/singlesubs";
    /**
     * 自定义排序
     */
    public String order = getBaseUrl() + subsUrl + "/operate/order";


    /**
     * websocket
     */
    String apiUrl = "/ws";
    /**
     * 订阅
     */
    public String registerkeys = "http://topcoin.bicoin.com.cn" + apiUrl + "/resubscript";
    /**
     * 取消订阅
     */
    public String unregisterkeys = getBaseUrl() + apiUrl + "/unregisterkeys";

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
     * 获取加入圈子信息
     */
    public String getCircleTopic = getBaseUrl() + "/articleTopic/listByPage";
    /**
     * 创建圈子
     */
    public String creatCircle = getBaseUrl() + "/group/saveGroup";
    /**
     * 修改圈子信息
     */
    public String editCircleInfo = getBaseUrl() + "/group/editGroup";
    /**
     * 生成邀请码
     */
    public String creatCircleCode = getBaseUrl() + "/group/createInviteCode";
    /**
     * 获取用户加入圈子
     */
    public String getMyCircle = getBaseUrl() + groupUrl + "/listGroupByPage";
    /**
     * 获取圈子详情
     */
    public String getCircleInfo = getBaseUrl() + "/group/detail";

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
    public String Circlebanned = getBaseUrl() + "/group/banned";
    /**
     * 解除禁言所有人
     */
    public String CircleancelBanned = getBaseUrl() + "/group/cancelBanned";
    /**
     * 圈子成员
     */
    public String findMember = getBaseUrl() + "/groupMember/findAllMember";
    /**
     * 圈子成员禁言
     */
    public String memberBanned = getBaseUrl() + "/groupMember/banned";
    /**
     * 圈子成员解除禁言
     */
    public String memberuUnBanned = getBaseUrl() + "/groupMember/unbanned";


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
    public String getDetails = getBaseUrl() + "/articleTopic/detail";


    /**
     * 组合跟投
     */
    String demoUrl = "/demo";
    /**
     * 我的组合列表
     */
    public String listDemo = getBaseUrl() + demoUrl + "/listDemo";
    /**
     * 排行榜
     */
    public String top = getBaseUrl() + demoUrl + "/top";
    /**
     * 创建组合
     */
    public String save = getBaseUrl() + demoUrl + "/save";
    /**
     * 申请买卖
     */
    public String deal = getBaseUrl() + demoUrl + "/deal";
    /**
     * 成交历史
     */
    public String listDeal = getBaseUrl() + demoUrl + "/listDeal";
    /**
     * 委托历史
     */
    public String history = getBaseUrl() + demoUrl + "/history";
    /**
     * 持仓明细
     */
    public String listPosition = getBaseUrl() + demoUrl + "/listPosition";
    /**
     * 今日收益
     */
    public String getTodayInfo = getBaseUrl() + demoUrl + "/getTodayInfo";
    /**
     * 分期收益
     */
    public String allRate = getBaseUrl() + demoUrl + "/allRate";
    /**
     * 币种列表
     */
    public String searchCoin = getBaseUrl() + demoUrl + "/searchCoin";
    /**
     * 持有币种列表
     */
    public String myCoin = getBaseUrl() + demoUrl + "/myCoin";
    /**
     * 收益走势
     */
    public String rateTrend = getBaseUrl() + demoUrl + "/rateTrend";
    /**
     * 日均操作次数
     */
    public String getTransCount = getBaseUrl() + demoUrl + "/getTransCount";
    /**
     * 获取余额
     */
    public String getBalance = getBaseUrl() + demoUrl + "/getBalance";
    /**
     * 撤单
     */
    public String cancel = getBaseUrl() + demoUrl + "/cancel";
    /**
     * 关注组合
     */
    public String demoattention = getBaseUrl() + demoUrl + "/attention";
    /**
     * 取消关注组合
     */
    public String cancelAttention = getBaseUrl() + demoUrl + "/cancelAttention";
    /**
     * 我的关注
     */
    public String listAttentionDemo = getBaseUrl() + demoUrl + "/listAttentionDemo";
    /**
     * 修改组合信息
     */
    public String updateDemoBrief = getBaseUrl() + demoUrl + "/updateDemoBrief";
    /**
     * 组合分享
     */
    public String articleSave = getBaseUrl() + demoUrl + "/articleSave";
    /**
     * 组合动态
     */
    public String dynamic = getBaseUrl() + demoUrl + "/dynamic";
    /**
     * 组合详情
     */
    public String getDemoByUserId = getBaseUrl() + demoUrl + "/getDemoByUserId";


    /**
     * 文章
     */
    String articleUrl = "/article";
    /**
     * 文章列表
     */
    public String listArticleByPage = getBaseUrl() + articleUrl + "/listArticleByPage";
    /**
     * 发文章
     */
    public String saveArticle = getBaseUrl() + "/articleTopic/save";
    /**
     * 广场导入文章
     */
    public String dealSquareArticle = getBaseUrl() + "/articleTopic/reprintedSquare";
    /**
     * 圈子导入文章
     */
    public String dealCircleArticle = getBaseUrl() + "/articleTopic/reprintedGroup";

    /**
     * 发表评论
     */
    public String saveComment = getBaseUrl() + "/comment/saveComment";
    /**
     * 点赞
     */
    public String savePraise = getBaseUrl() + "/praise/praiseOrCancel";
    /**
     * 获取评论
     */
    public String getComment = getBaseUrl() + "/comment/listComment";

    /**
     * 获取广场朋友圈
     */
    public String getSquareLive = getBaseUrl() + "/articleTopic/listByPage";
    /**
     * 获取大V
     */
    public String getBigVlist = getBaseUrl() + "/user/listBigVByPage";
    /**
     * 关注
     */
    public String attention = getBaseUrl() + "/userAttention/save";
    /**
     * 取消关注
     */
    public String attentiondelete = getBaseUrl() + "/userAttention/delete";


    /**
     * 获取文章
     */
    public String getArticle = getBaseUrl() + "/articleTopic/listArticleByPage";
    /**
     * 获取文章
     */
    public String getCircleMore = getBaseUrl() + "/group/listMoreGroupByPage";
    /**
     * 获取用户主页信息
     */
    public String getUserInfo = getBaseUrl() + "/user/personCenter";
    /**
     * 获取粉丝列表
     */
    public String getFans = getBaseUrl() + "/userAttention/attentionMyList";
    /**
     * 获取关注列表
     */
    public String getAttention = getBaseUrl() + "/user/listMyAttentionByPage";

    /**
     * 获取资讯
     */
    public String getNews = getBaseUrl() + "/news/list";
    /**
     * 利好
     */
    public String saveNews = getBaseUrl() + "/news/like";
    /**
     * 获取评论回复
     */
    public String getNewsComment = getBaseUrl() + "/news/comment/list";
    /**
     * 评论回复
     */
    public String reComment = getBaseUrl() + "/news/comment/add";


    String ip = "http://192.168.0.25:8080";

    /**
     * 战队
     */
    String gameTeamUrl = "/gameTeam";
    /**
     * 创建战队
     */
    public String createGameTeam = getBaseUrl() + gameTeamUrl + "/createGameTeam";
    /**
     * 广场
     */
    public String getSquareTeamGame = getBaseUrl() + gameTeamUrl + "/getSquareTeamGame";
    /**
     * 大赛
     */
    public String getBigGame = getBaseUrl() + gameTeamUrl + "/getBigGame";
}
