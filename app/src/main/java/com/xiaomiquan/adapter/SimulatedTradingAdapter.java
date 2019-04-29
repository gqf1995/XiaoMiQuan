package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/26 0026.
 */

public class SimulatedTradingAdapter extends CommonAdapter<String> {


    private TextView tv_type;
    private TextView tv_coin;
    private TextView tv_statu;
    private TextView tv_price;
    private TextView tv_num;
    private TextView tv_time;

    public SimulatedTradingAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_simulated_trading, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_type = holder.getView(R.id.tv_type);
        tv_coin = holder.getView(R.id.tv_coin);
        tv_statu = holder.getView(R.id.tv_statu);
        tv_price = holder.getView(R.id.tv_price);
        tv_num = holder.getView(R.id.tv_num);
        tv_time = holder.getView(R.id.tv_time);

    }

}