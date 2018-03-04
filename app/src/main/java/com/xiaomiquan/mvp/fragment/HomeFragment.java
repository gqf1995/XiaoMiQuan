package com.xiaomiquan.mvp.fragment;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.base.BaseWebFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.just.agentweb.AgentWebConfig;
import com.xiaomiquan.R;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.HomeBinder;
import com.xiaomiquan.mvp.delegate.HomeDelegate;

public class HomeFragment extends BaseDataBindFragment<HomeDelegate, HomeBinder> {

    BaseWebFragment baseWebFragment;
    String url = "http://47.96.180.179:1904/gameTeam/showWebViewIndex";
    BridgeWebView mBridgeWebView;

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
        baseWebFragment = BaseWebFragment.newInstance(url);
        viewDelegate.initAddFragment(R.id.fl_root, getChildFragmentManager());
        viewDelegate.addFragment(baseWebFragment);
        initToolBarSearch();
        if (SingSettingDBUtil.getUserLogin() != null) {
            AgentWebConfig.syncCookie(url, "token=" + "44cf54dbdcbeb90c2e448655a2e54f5c");
            //AgentWebConfig.syncCookie(url, "token=" + SaveUtil.getInstance().getString("token"));
        }
        viewDelegate.showFragment(0);
        bridgeWeb();
    }


    private void bridgeWeb() {
        mBridgeWebView = baseWebFragment.getmBridgeWebView();

    }

    //给toolbar添加搜索布局
    private void initToolBarSearch() {
        initToolbar(new ToolbarBuilder()
                .setSubTitle(CommonUtils.getString(R.string.ic_Share))
                .setmToolbarBackColor(CommonUtils.getColor(R.color.white))
                .setShowBack(false));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.color_font1));
        viewDelegate.getFl_content().addView(getActivity().getLayoutInflater().inflate(R.layout.layout_home_top, null));
    }


    @Override
    public void onResume() {
        super.onResume();
        viewDelegate.setStatusBg(R.color.status_bg, true);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
