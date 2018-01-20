package com.xiaomiquan.mvp.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.base.UserSet;
import com.xiaomiquan.mvp.databinder.WebBinder;
import com.xiaomiquan.mvp.delegate.WebDelegate;
import com.xiaomiquan.server.HttpUrl;
import com.xiaomiquan.utils.UiHeplUtils;

import java.util.HashMap;

public class WebFragment extends BaseDataBindFragment<WebDelegate, WebBinder> {



    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //  super.onProgressChanged(view, newProgress);
        }
    };

    protected WebViewClient mWebViewClient = new WebViewClient() {

        private HashMap<String, Long> timer = new HashMap<>();

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        //
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            if (url.startsWith("intent://") && url.contains("com.youku.phone"))
                return true;
            /*else if (isAlipay(view, url))   //1.2.5开始不用调用该方法了 ，只要引入支付宝sdk即可 ， DefaultWebClient 默认会处理相应url调起支付宝
                return true;*/
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            timer.put(url, System.currentTimeMillis());

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    };

    @Override
    public void onDestroyView() {
        viewDelegate.viewHolder.webView.removeAllViews();
        viewDelegate.viewHolder.webView.destroy();

        super.onDestroyView();
    }

    String type;//网址
    boolean isNight;
    boolean isLogin;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        type = getArguments().getString("type");
        UiHeplUtils.webviewRegister(viewDelegate.viewHolder.webView);
        isNight = UserSet.getinstance().isNight();
        isLogin = TextUtils.isEmpty(HttpUrl.getIntance().getToken());
        viewDelegate.viewHolder.webView.loadUrl(type + binder.getMapWithUid());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNight != UserSet.getinstance().isNight() || isLogin != TextUtils.isEmpty(HttpUrl.getIntance().getToken())) {
            viewDelegate.viewHolder.webView.loadUrl(type + binder.getMapWithUid());
        }
    }

    public boolean goBack() {
        if (viewDelegate.viewHolder.webView.canGoBack()) {
            viewDelegate.viewHolder.webView.goBack();
            return false;
        }
        return true;
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    @Override
    protected Class<WebDelegate> getDelegateClass() {
        return WebDelegate.class;
    }

    @Override
    public WebBinder getDataBinder(WebDelegate viewDelegate) {
        return new WebBinder(viewDelegate);
    }

    public static WebFragment newInstance(
            String type) {
        WebFragment newFragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("type")) {
            type = savedInstanceState.getString("type");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type", type);
    }
}
