package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class GroupDealCurrencyAdapyer extends CommonAdapter<ExchangeData> {
    private AppCompatImageView iv_img;
    private TextView tv_type;
    private IconFontTextview icf_check;

    public GroupDealCurrencyAdapyer(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_deal_currency, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ExchangeData exchangeData, int position) {
        iv_img = holder.getView(R.id.iv_img);
        tv_type = holder.getView(R.id.tv_type);
        icf_check = holder.getView(R.id.icf_check);
    }
}
