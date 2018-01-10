package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;

import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CoinMarketAdapter extends CommonAdapter<String> {

    private RoundedImageView iv_pic;
    private FontTextview tv_coin_type;
    private FontTextview tv_coin_name;
    private FontTextview tv_coin_price;
    private FontTextview tv_coin_probably;
    private FontTextview tv_gains;
    private CardView card_root;
    private FontTextview tv_coin_market_value;

    public CoinMarketAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_coin_market, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        iv_pic = holder.getView(R.id.iv_pic);
        tv_coin_type = holder.getView(R.id.tv_coin_type);
        tv_coin_name = holder.getView(R.id.tv_coin_name);
        tv_coin_price = holder.getView(R.id.tv_coin_price);
        tv_coin_probably = holder.getView(R.id.tv_coin_probably);
        tv_gains = holder.getView(R.id.tv_gains);
        card_root = holder.getView(R.id.card_root);
        tv_coin_market_value = holder.getView(R.id.tv_coin_market_value);
    }

}