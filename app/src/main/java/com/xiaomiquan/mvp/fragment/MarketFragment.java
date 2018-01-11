package com.xiaomiquan.mvp.fragment;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.MarketBinder;
import com.xiaomiquan.mvp.delegate.MarketDelegate;

public class MarketFragment extends BaseDataBindFragment<MarketDelegate, MarketBinder> {

    @Override
    protected Class<MarketDelegate> getDelegateClass() {
        return MarketDelegate.class;
    }

    @Override
    public MarketBinder getDataBinder(MarketDelegate viewDelegate) {
        return new MarketBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setSubTitle(CommonUtils.getString(R.string.ic_zhankai)).setTitle(CommonUtils.getString(R.string.str_title_market)).setShowBack(true));
        viewDelegate.setBackIconFontText(CommonUtils.getString(R.string.ic_zhankai));
        initBarClick();
    }

    private void initBarClick() {
        viewDelegate.getmToolbarBackLin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //搜索

            }
        });
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //钟

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
