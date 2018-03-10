package com.fivefivelike.mybaselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.sonic.SonicImpl;
import com.fivefivelike.mybaselibrary.sonic.SonicJavaScriptInterface;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebClientBase;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.LineSpinFadeLoaderIndicator;

import static com.fivefivelike.mybaselibrary.sonic.SonicJavaScriptInterface.PARAM_CLICK_TIME;

/**
 * Created by 郭青枫 on 2018/3/4 0004.
 */

public class BaseWebFragment extends BaseFragment<BaseWebViewDelegate> {
    private SonicImpl mSonicImpl;
    private String url = "http://47.96.180.179:1904/gameTeam/showWebViewIndex";
    protected AgentWeb mAgentWeb;
    WebLinsener webLinsener;
    BridgeWebView mBridgeWebView;
    AVLoadingIndicatorView indicatorView;

    public void setWebLinsener(WebLinsener webLinsener) {
        this.webLinsener = webLinsener;
    }

    public interface WebLinsener {
        void onLoadEndPage();

        void onLoadTitle(String title);
    }

    public void setmAgentWeb(AgentWeb mAgentWeb) {
        this.mAgentWeb = mAgentWeb;
    }


    public void loadUrl(String url) {
        this.url = url;
        mAgentWeb.getWebCreator().getWebView().loadUrl(url);
        indicatorView.show();
    }

    public boolean goBack() {
        return mAgentWeb.back();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        url = getArguments().getString("url", "http://47.96.180.179:1904/gameTeam/showWebViewIndex");
        mBridgeWebView = new BridgeWebView(viewDelegate.viewHolder.root.getContext());//getActivity());
        indicatorView = new AVLoadingIndicatorView(viewDelegate.viewHolder.root.getContext());//尾部加载中状态
        indicatorView.setIndicator(new LineSpinFadeLoaderIndicator());
        indicatorView.setIndicatorColor(CommonUtils.getColor(R.color.color_font4));
        //AgentWebConfig.syncCookie(url, "token=" + "44cf54dbdcbeb90c2e448655a2e54f5c");
        // 1. 首先创建SonicImpl
        mSonicImpl = new SonicImpl(url, this.getContext());
        // 2. 调用 onCreateSession
        mSonicImpl.onCreateSession();
        //3. 创建AgentWeb ，注意创建AgentWeb的时候应该使用加入SonicWebViewClient中间件
        //        super.onViewCreated(view, savedInstanceState); // 创建 AgentWeb 注意的 go("") 传入的 mUrl 应该null 或者""
        //viewDelegate.viewHolder.mBridgeWebView = new BridgeWebView(getActivity());
        if (mAgentWeb == null) {
            mAgentWeb = AgentWeb.with(this)//
                    .setAgentWebParent((ViewGroup) viewDelegate.viewHolder.rootView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//
                    .closeIndicator()
                    //.setAgentWebWebSettings(getSettings())//
                    .setWebViewClient(new BridgeWebViewClient(mBridgeWebView))
                    .setWebChromeClient(mWebChromeClient)
                    .setWebView(mBridgeWebView)
                    .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                    .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
                    .createAgentWeb()//
                    .ready()//
                    .go("");
        }
        WebView webView = mAgentWeb.getWebCreator().getWebView();
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        viewDelegate.viewHolder.root.addView(indicatorView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) indicatorView.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_80px);
        layoutParams.width = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_80px);
        indicatorView.setLayoutParams(layoutParams);
        //4. 注入 JavaScriptInterface
        mAgentWeb.getJsInterfaceHolder().addJavaObject("sonic",
                new SonicJavaScriptInterface(mSonicImpl.getSonicSessionClient(),
                        new Intent()
                                .putExtra(PARAM_CLICK_TIME, getArguments().getLong(PARAM_CLICK_TIME))
                                .putExtra("loadUrlTime", System.currentTimeMillis())));
        //5. 最后绑定AgentWeb
        mSonicImpl.bindAgentWeb(mAgentWeb);
    }

    public AgentWeb getmAgentWeb() {
        return mAgentWeb;
    }

    public BridgeWebView getmBridgeWebView() {
        //外部获取 绑定交互事件
        return mBridgeWebView;
    }

    public static BaseWebFragment newInstance(
            String url) {
        BaseWebFragment newFragment = new BaseWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //销毁SonicSession
        mAgentWeb.getWebLifeCycle().onDestroy();
        if (mSonicImpl != null) {
            mSonicImpl.destrory();
        }
    }

    //在步骤3的时候应该传入给AgentWeb
    public MiddlewareWebClientBase getMiddlewareWebClient() {
        return mSonicImpl.createSonicClientMiddleWare();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //  super.onProgressChanged(view, newProgress);
            Log.i("BaseWebFragment", "onProgressChanged:" + newProgress + "  view:" + view);
            if (newProgress == 100) {
                if (webLinsener != null) {
                    webLinsener.onLoadEndPage();
                }
                indicatorView.hide();
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (webLinsener != null) {
                webLinsener.onLoadTitle(title);
            }
        }
    };


    @Override
    protected Class getDelegateClass() {
        return BaseWebViewDelegate.class;
    }
}
