package com.xiaomiquan.mvp.activity.market;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.CoinIndexBinder;
import com.xiaomiquan.mvp.delegate.CoinIndexDelegate;

public class CoinIndexActivity extends BaseDataBindActivity<CoinIndexDelegate, CoinIndexBinder> {

    @Override
    protected Class<CoinIndexDelegate> getDelegateClass() {
        return CoinIndexDelegate.class;
    }

    @Override
    public CoinIndexBinder getDataBinder(CoinIndexDelegate viewDelegate) {
        return new CoinIndexBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_coin_index)));

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
