package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class LabelHistoryTradingAdapter extends CommonAdapter<String> {


    private TextView tv_type;
    private TextView tv_currency;
    private TextView tv_entrust_price;
    private TextView tv_num;
    private TextView tv_deal_time;

    public LabelHistoryTradingAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_label_history_trading, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_type = holder.getView(R.id.tv_type);
        tv_currency = holder.getView(R.id.tv_currency);
        tv_entrust_price = holder.getView(R.id.tv_entrust_price);
        tv_num = holder.getView(R.id.tv_num);
        tv_deal_time = holder.getView(R.id.tv_deal_time);
    }

}