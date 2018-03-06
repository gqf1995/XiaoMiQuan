package com.xiaomiquan.mvp.activity.chat;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.CoinCircleIndexBinder;
import com.xiaomiquan.mvp.delegate.CoinCircleIndexDelegate;

/**
 * 币圈指数
 */
public class CoinCircleIndexActivity extends BaseDataBindActivity<CoinCircleIndexDelegate, CoinCircleIndexBinder> {

    @Override
    protected Class<CoinCircleIndexDelegate> getDelegateClass() {
        return CoinCircleIndexDelegate.class;
    }

    @Override
    public CoinCircleIndexBinder getDataBinder(CoinCircleIndexDelegate viewDelegate) {
        return new CoinCircleIndexBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_coin_circle_index)));

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
