package com.xiaomiquan.mvp.fragment.circle;

import android.support.v4.app.FragmentTransaction;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.base.BaseWebFragment;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.just.agentweb.AgentWebConfig;
import com.xiaomiquan.R;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.circle.SquareWebBinder;
import com.xiaomiquan.mvp.delegate.circle.SquareWebDelegate;

public class SquareWebFragment extends BasePullFragment<SquareWebDelegate, SquareWebBinder> {

    BaseWebFragment baseWebFragment;
    String url = "http://47.96.180.179:1904/gameTeam/showWebViewIndex";
    BridgeWebView mBridgeWebView;

    @Override
    public SquareWebBinder getDataBinder(SquareWebDelegate viewDelegate) {
        return new SquareWebBinder(viewDelegate);
    }

    @Override
    protected Class<SquareWebDelegate> getDelegateClass() {
        return SquareWebDelegate.class;
    }

    @Override
    protected void refreshData() {
        mBridgeWebView.reload();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        if (SingSettingDBUtil.getUserLogin() != null) {
            AgentWebConfig.syncCookie(url, "token=" + "44cf54dbdcbeb90c2e448655a2e54f5c");
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().findFragmentByTag("BaseWebFragment") == null) {
            baseWebFragment = BaseWebFragment.newInstance(url);
            transaction.add(R.id.fl_root, baseWebFragment, "BaseWebFragment");
        } else {
            baseWebFragment = (BaseWebFragment) getChildFragmentManager().findFragmentByTag("BaseWebFragment");
            transaction.show(baseWebFragment);
        }
        transaction.commitAllowingStateLoss();
        bridgeWeb();
    }

    private void bridgeWeb() {
        mBridgeWebView = baseWebFragment.getmBridgeWebView();
        //        mBridgeWebView.setWebViewClient(new BridgeWebViewClient(mBridgeWebView).onPageFinished(););
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }
}
