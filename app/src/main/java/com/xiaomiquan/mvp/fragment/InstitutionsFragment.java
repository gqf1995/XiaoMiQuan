package com.xiaomiquan.mvp.fragment;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.mpchart.ConstantTest;
import com.xiaomiquan.mvp.activity.MarketDetailsActivity;
import com.xiaomiquan.mvp.databinder.InstitutionsBinder;
import com.xiaomiquan.mvp.delegate.InstitutionsDelegate;
import com.xiaomiquan.widget.chart.CoinLineDraw;

import org.json.JSONException;
import org.json.JSONObject;

public class InstitutionsFragment extends BaseDataBindFragment<InstitutionsDelegate, InstitutionsBinder> {

    @Override
    protected Class<InstitutionsDelegate> getDelegateClass() {
        return InstitutionsDelegate.class;
    }

    @Override
    public InstitutionsBinder getDataBinder(InstitutionsDelegate viewDelegate) {
        return new InstitutionsBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getOffLineData();
        viewDelegate.viewHolder.lin_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(MarketDetailsActivity.class).startAct();
            }
        });
    }
    private void getOffLineData() {
           /*方便测试，加入假数据*/
        DataParse mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.KLINEURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseKLine(object);
        CoinLineDraw coinLineDraw = new CoinLineDraw();
        coinLineDraw.setData(mData, viewDelegate.viewHolder.linechart);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
