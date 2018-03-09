package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.HoldDetail;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class PositionDetailAdapter extends CommonAdapter<HoldDetail> {


    private TextView tv_name;
    private TextView tv_position_scale;
    private TextView tv_position_price;
    private TextView tv_now_price;
    private TextView tv_rate;

    public PositionDetailAdapter(Context context, List<HoldDetail> datas) {
        super(context, R.layout.adapter_position_detail, datas);
    }

    public void setDatas(List<HoldDetail> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, HoldDetail s, int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_position_scale = holder.getView(R.id.tv_position_scale);
        tv_position_price = holder.getView(R.id.tv_position_price);
        tv_now_price = holder.getView(R.id.tv_now_price);
        tv_rate = holder.getView(R.id.tv_rate);

        tv_name.setText(s.getSymbol());
        tv_position_scale.setText(s.getCount() + s.getPositionRateAfter() + "%");
        tv_position_price.setText("$" + BigUIUtil.getinstance().bigPrice(s.getPrice()));
        tv_now_price.setText("$" + BigUIUtil.getinstance().bigPrice(s.getCurrPrice()));
        tv_rate.setText(s.getRate() + "%");

    }

}
