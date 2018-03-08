package com.xiaomiquan.mvp.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.base.BaseWebFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.just.agentweb.AgentWebConfig;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupBaseDeal;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.HisAccountActivity;
import com.xiaomiquan.mvp.activity.group.SimulatedTradingActivity;
import com.xiaomiquan.mvp.databinder.HomeBinder;
import com.xiaomiquan.mvp.delegate.HomeDelegate;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fivefivelike.mybaselibrary.utils.glide.GlideUtils.BASE_URL;

public class HomeFragment extends BaseDataBindFragment<HomeDelegate, HomeBinder> {

    BaseWebFragment baseWebFragment;
    String url = "http://47.96.180.179:1904/gameTeam/showWebViewIndex";
    BridgeWebView mBridgeWebView;

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
        addRequest(binder.listDemo(this));
        viewDelegate.viewHolder.btn_his_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datas != null) {
                    SimulatedTradingActivity.startAct(getActivity(), datas, 0, true);
                }
            }
        });
        viewDelegate.viewHolder.btn_my_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datas != null) {
                    HisAccountActivity.startAct(getActivity(), datas.get(0).getId());
                }
            }
        });
        if (SingSettingDBUtil.getUserLogin() != null) {
            AgentWebConfig.syncCookie(url, "token=" + "44cf54dbdcbeb90c2e448655a2e54f5c");
            //AgentWebConfig.syncCookie(url, "token=" + SaveUtil.getInstance().getString("token"));
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().findFragmentByTag("BaseWebFragment") == null) {
            baseWebFragment = BaseWebFragment.newInstance(url);
            transaction.add(R.id.fl_web, baseWebFragment, "BaseWebFragment");
        } else {
            baseWebFragment = (BaseWebFragment) getChildFragmentManager().findFragmentByTag("BaseWebFragment");
            transaction.show(baseWebFragment);
        }
        transaction.commitAllowingStateLoss();
        bridgeWeb();

    }


    private void bridgeWeb() {
        mBridgeWebView = baseWebFragment.getmBridgeWebView();

    }

    @Override
    protected void clickRightIv() {
        super.clickRightIv();
        //消息

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
        if (SingSettingDBUtil.getUserLogin() != null) {
            GlideUtils.loadImage(SingSettingDBUtil.getUserLogin().getAvatar(), ic_pic);
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
    }


    List<GroupItem> datas;

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                datas = GsonUtil.getInstance().toList(data,GroupItem.class);
                break;
        }
    }

}
