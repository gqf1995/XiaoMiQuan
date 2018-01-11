package com.xiaomiquan.mvp.fragment;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.mpchart.ConstantTest;
import com.xiaomiquan.mvp.databinder.UserBinder;
import com.xiaomiquan.mvp.delegate.UserDelegate;
import com.xiaomiquan.widget.chart.CoinLineDraw;

import org.json.JSONException;
import org.json.JSONObject;

public class UserFragment extends BaseDataBindFragment<UserDelegate, UserBinder> {

    @Override
    protected Class<UserDelegate> getDelegateClass() {
        return UserDelegate.class;
    }

    @Override
    public UserBinder getDataBinder(UserDelegate viewDelegate) {
        return new UserBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getOffLineData();
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
        CoinLineDraw coinLineDraw=new CoinLineDraw();
        coinLineDraw.setData(mData,viewDelegate.viewHolder.linechart);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
