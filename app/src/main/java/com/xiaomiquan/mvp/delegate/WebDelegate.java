package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.webkit.WebView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class WebDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web;
    }


    public static class ViewHolder {
        public View rootView;
        public WebView webView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.webView = (WebView) rootView.findViewById(R.id.webView);
        }

    }
}