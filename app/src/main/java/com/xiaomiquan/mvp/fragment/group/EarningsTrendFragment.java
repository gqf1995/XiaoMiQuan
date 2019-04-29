package com.xiaomiquan.mvp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.entity.bean.group.EarningsMovements;
import com.xiaomiquan.mvp.databinder.group.EarningsTrendBinder;
import com.xiaomiquan.mvp.delegate.group.EarningsTrendDelegate;
import com.xiaomiquan.utils.BigUIUtil;

public class EarningsTrendFragment extends BaseDataBindFragment<EarningsTrendDelegate, EarningsTrendBinder> {

    @Override
    protected Class<EarningsTrendDelegate> getDelegateClass() {
        return EarningsTrendDelegate.class;
    }

    @Override
    public EarningsTrendBinder getDataBinder(EarningsTrendDelegate viewDelegate) {
        return new EarningsTrendBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        id = getArguments().getString("id");
        if(!TextUtils.isEmpty(id)) {
            addRequest(binder.allRate(id, this));
            addRequest(binder.getTodayInfo(id, this));
            addRequest(binder.rateTrend(id, this));
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //今日收益&日均操作次数
                BigUIUtil.getinstance().rateTextView(Double.parseDouble(GsonUtil.getInstance().getValue(data, "todayRate")), viewDelegate.viewHolder.tv_today_earnings);
                viewDelegate.viewHolder.tv_daily_operation.setText(GsonUtil.getInstance().getValue(data, "count"));
                addRequest(binder.rateTrend(id, this));
                break;
            case 0x124:
                //分期收益
                viewDelegate.initRate(data);
                break;
            case 0x125:
                //收益走势
                EarningsMovements earningsMovements = GsonUtil.getInstance().toObj(data, EarningsMovements.class);
                viewDelegate.initEarningsMovements(earningsMovements);
                addRequest(binder.allRate(id, this));
                break;
        }
    }

    public static EarningsTrendFragment newInstance(
            String id
    ) {
        EarningsTrendFragment newFragment = new EarningsTrendFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("id")) {
            id = savedInstanceState.getString("id");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", id);
    }

}
