package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiaomiquan.R;
import com.xiaomiquan.base.UserSet;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ExchangeMarketAdapter extends CommonAdapter<ExchangeData> {

    int[] bgIds = {R.drawable.ic_value_bg1, R.drawable.ic_value_bg2, R.drawable.ic_value_bg3, R.drawable.ic_value_bg4, R.drawable.ic_value_bg5};

    private RoundedImageView iv_pic;
    private FontTextview tv_coin_type;
    private FontTextview tv_coin_name;
    private FontTextview tv_coin_price;
    private FontTextview tv_coin_probably;
    private FontTextview tv_gains;
    private CardView card_root;
    private FontTextview tv_coin_market_value;
    private RoundedImageView iv_bg;
    private LinearLayout lin_root;

    public ExchangeMarketAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_coin_market, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ExchangeData s, final int position) {
        iv_pic = holder.getView(R.id.iv_pic);
        tv_coin_type = holder.getView(R.id.tv_coin_type);
        tv_coin_name = holder.getView(R.id.tv_coin_name);
        tv_coin_price = holder.getView(R.id.tv_coin_price);
        tv_coin_probably = holder.getView(R.id.tv_coin_probably);
        iv_bg = holder.getView(R.id.iv_bg);
        tv_gains = holder.getView(R.id.tv_gains);
        card_root = holder.getView(R.id.card_root);
        tv_coin_market_value = holder.getView(R.id.tv_coin_market_value);
        iv_bg.setImageResource(bgIds[position % 5]);
        iv_pic.setImageResource(R.drawable.bitcoin);

        tv_coin_type.setText(s.getSymbol() + "/" + s.getUnit());
        tv_coin_name.setText(CommonUtils.getString(R.string.str_global_network) + s.getSymbol());
        tv_coin_market_value.setText(CommonUtils.getString(R.string.str_market_value));
        tv_coin_price.setText(s.getLast().toString());

        tv_coin_probably.setText("≈" + s.getChoicePrice());
        tv_coin_probably.setVisibility(TextUtils.isEmpty(s.getChoicePrice()) ? View.GONE : View.VISIBLE);

        tv_coin_probably.setText(s.getChoicePrice());
        if (!TextUtils.isEmpty(s.getChange())) {
            if (new BigDecimal(s.getChange()).compareTo(new BigDecimal("0")) == 1) {
                tv_gains.setText(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
            } else {
                tv_gains.setText(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
            }
        }
        tv_gains.setText(s.getChange() + "%");


    }

}