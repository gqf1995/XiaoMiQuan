package com.xiaomiquan.mvp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.base.BaseWebFragment;
import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.just.agentweb.AgentWebConfig;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.MessageInfo;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.chat.ChatLiveItem;
import com.xiaomiquan.entity.bean.chat.CheckScore;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.greenDB.MessageInfoDao;
import com.xiaomiquan.greenDaoUtils.DaoManager;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.MessageCenterActivity;
import com.xiaomiquan.mvp.activity.chat.ChatLiveListActivity;
import com.xiaomiquan.mvp.activity.chat.GroupChatActivity;
import com.xiaomiquan.mvp.activity.group.HisAccountActivity;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.mvp.databinder.HomeBinder;
import com.xiaomiquan.mvp.delegate.HomeDelegate;
import com.xiaomiquan.server.HttpUrl;
import com.xiaomiquan.widget.CircleDialogHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fivefivelike.mybaselibrary.utils.glide.GlideUtils.BASE_URL;

public class HomeFragment extends BaseDataBindFragment<HomeDelegate, HomeBinder> {

    BaseWebFragment baseWebFragment;
    String url = HttpUrl.getBaseUrl() + "/gameTeam/showWebViewIndex";
    BridgeWebView mBridgeWebView;
    UserLogin userLogin;
    boolean isFirstLoad = true;
    ChatLiveItem chatLiveItem;

    public interface Linsener {
        void openDrawerLayout();
    }

    Linsener linsener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        linsener = (Linsener) activity;
    }

    @Override
    protected Class<HomeDelegate> getDelegateClass() {
        return HomeDelegate.class;
    }

    @Override
    public HomeBinder getDataBinder(HomeDelegate viewDelegate) {
        return new HomeBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolBarSearch();
        datas = new ArrayList<>();
        initUser();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().findFragmentByTag("BaseWebFragment") == null) {
            baseWebFragment = BaseWebFragment.newInstance(url);
            transaction.add(R.id.fl_web, baseWebFragment, "BaseWebFragment");
        } else {
            baseWebFragment = (BaseWebFragment) getChildFragmentManager().findFragmentByTag("BaseWebFragment");
            transaction.show(baseWebFragment);
        }
        transaction.commitAllowingStateLoss();
        baseWebFragment.setWebLinsener(new BaseWebFragment.WebLinsener() {
            @Override
            public void onLoadEndPage() {
                bridgeWeb();
            }

            @Override
            public void onLoadTitle(String title) {

            }
        });


    }

    public void showMessageNum() {
        List<MessageInfo> list = DaoManager.getInstance().getDaoSession().getMessageInfoDao()
                .queryBuilder()
                .where(MessageInfoDao.Properties.IsLook.eq(false))
                .list();
        FrameLayout viewImg1Point = (FrameLayout) viewDelegate.getViewImgPoint();
        viewDelegate.setPointNum(list.size(), viewImg1Point);
    }

    private void bridgeWeb() {
        if (mBridgeWebView == null) {
            mBridgeWebView = baseWebFragment.getmBridgeWebView();
            mBridgeWebView.registerHandler("WebToLocal", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
                    String forward = GsonUtil.getInstance().getValue(data, "forward");
                    if ("bcoin://chatList".equals(forward)) {
                        //跳转聊天室列表
                        startActivity(new Intent(getActivity(), ChatLiveListActivity.class));
                    } else if ("bcoin://virCoin".equals(forward)) {
                        //跳转投资组合
                        ResultDialogEntity resultDialogEntity = new ResultDialogEntity();
                        resultDialogEntity.setCode("0");
                        EventBus.getDefault().post(resultDialogEntity);
                    } else if ("bcoin://chatRoom".equals(forward)) {
                        //点击参与进入具体聊天室
                        String parameters = GsonUtil.getInstance().getValue(data, "parameters");
                        chatLiveItem = GsonUtil.getInstance().toObj(parameters, ChatLiveItem.class);
                        addRequest(binder.checkScore(chatLiveItem.getGroupId(), HomeFragment.this));
                    } else if ("bcoin://showAccount".equals(forward)) {
                        //点击进入某用户组合详情页,参数userId、type标示在详情页面默认打开的tab
                        String parameters = GsonUtil.getInstance().getValue(data, "parameters");
                        String userId = GsonUtil.getInstance().getValue(parameters, "userId");
                        String type = GsonUtil.getInstance().getValue(parameters, "type");
                        HisAccountActivity.startAct(getActivity(), userId, type);
                    } else {
                        //跳转web页面
                        WebActivityActivity.startAct(getActivity(), forward);
                    }
                }
            });
        }
    }

    @Override
    protected void clickRightIv() {
        super.clickRightIv();
        //消息
        if (SingSettingDBUtil.getUserLogin() != null) {
            startActivity(new Intent(getActivity(), MessageCenterActivity.class));
        } else {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad) {
            initUser();
        } else {
            isFirstLoad = false;
        }
    }

    private void initUser() {
        if ((userLogin == null && SingSettingDBUtil.getUserLogin() != null) && !isFirstLoad) {
            //登录完 刷新页面
            userLogin = SingSettingDBUtil.getUserLogin();
            AgentWebConfig.syncCookie(url, "token=" + HttpUrl.getIntance().getToken());
            if (baseWebFragment != null) {
                baseWebFragment.loadUrl(url);
            }
        } else {
            userLogin = SingSettingDBUtil.getUserLogin();
            AgentWebConfig.syncCookie(url, "token=" + HttpUrl.getIntance().getToken());
        }
        //刷新
        initToolBarSearch();
    }


    public CircleImageView ic_pic;
    public FrameLayout fl_pic;

    //给toolbar添加搜索布局
    private void initToolBarSearch() {
        initToolbar(new ToolbarBuilder()
                .setTitle(CommonUtils.getString(R.string.str_comprehensive))
                .setmRightImg1(CommonUtils.getString(R.string.ic_Message))
                .setmToolbarBackColor(CommonUtils.getColor(R.color.white))
                .setShowBack(false));
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_home_top, null);
        viewDelegate.getFl_content().addView(rootView);
        this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
        this.fl_pic = (FrameLayout) rootView.findViewById(R.id.fl_pic);
        if (userLogin != null) {
            GlideUtils.loadImage(userLogin.getAvatar(), ic_pic);
        } else {
            GlideUtils.loadImage(BASE_URL, ic_pic);
        }
        ic_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开抽屉
                linsener.openDrawerLayout();
            }
        });
        showMessageNum();
    }


    List<GroupItem> datas;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            baseWebFragment.loadUrl(url);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                datas = GsonUtil.getInstance().toList(data, GroupItem.class);
                break;
            case 0x124:
                CheckScore checkScore = GsonUtil.getInstance().toObj(data, CheckScore.class);
                if (!checkScore.isJoinGroup()) {
                    CircleDialogHelper.initDefaultToastDialog(getActivity(), CommonUtils.getString(R.string.str_toast_cannot_join_group), null)
                            .show();
                    return;
                }
                GroupChatActivity.startAct(this,
                        chatLiveItem.getGroupId(),
                        chatLiveItem.getGroupName(),
                        chatLiveItem.getAvatar(),
                        chatLiveItem.getTitle(),
                        chatLiveItem.getOnlineTotal() + "",
                        checkScore.isLeader(),
                        checkScore.isCanSpeak(),
                        0x123
                );
                break;
        }
    }

}
