package com.xiaomiquan.mvp.activity.market;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.mpchart.ConstantTest;
import com.xiaomiquan.mvp.databinder.MarketDetailsBinder;
import com.xiaomiquan.mvp.delegate.MarketDetailsDelegate;
import com.xiaomiquan.widget.chart.KlineDraw;

import org.json.JSONException;
import org.json.JSONObject;

public class MarketDetailsActivity extends BaseDataBindActivity<MarketDetailsDelegate, MarketDetailsBinder> {
    private DataParse mData;
    KlineDraw klineDraw;

    @Override
    protected Class<MarketDetailsDelegate> getDelegateClass() {
        return MarketDetailsDelegate.class;
    }

    @Override
    public MarketDetailsBinder getDataBinder(MarketDetailsDelegate viewDelegate) {
        return new MarketDetailsBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.ic_Star) + " " + CommonUtils.getString(R.string.str_add)));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        getOffLineData();
    }

    private void getOffLineData() {
           /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseKLine(object);
        klineDraw = new KlineDraw();
        klineDraw.setData(this, mData, viewDelegate.viewHolder.combinedchart, viewDelegate.viewHolder.barchart);

        int i = 0;
    }


    @Override
    protected void clickRightTv() {
        super.clickRightTv();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
