package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.HoldDetail;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class LabelDetailDealAdapter extends CommonAdapter<HoldDetail> {


    private TextView tv_currency;
    private TextView tv_storage_quantity;
    private TextView tv_storage_price;
    private TextView tv_now_price;
    private TextView tv_earnings_ratio;

    public LabelDetailDealAdapter(Context context, List<HoldDetail> datas) {
        super(context, R.layout.adapter_label_detail, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HoldDetail s, final int position) {
        tv_currency = holder.getView(R.id.tv_currency);
        tv_storage_quantity = holder.getView(R.id.tv_storage_quantity);
        tv_storage_price = holder.getView(R.id.tv_storage_price);
        tv_now_price = holder.getView(R.id.tv_now_price);
        tv_earnings_ratio = holder.getView(R.id.tv_earnings_ratio);


        tv_currency.setText(s.getSymbol());
        tv_storage_quantity.setText(s.getCount());
        tv_storage_price.setText(BigUIUtil.getinstance().bigPrice(s.getPrice()));
        tv_now_price.setText(BigUIUtil.getinstance().bigPrice(s.getCurrPrice()));
        BigUIUtil.getinstance().rateTextView(Double.parseDouble(s.getRate()), tv_earnings_ratio);
    }

}