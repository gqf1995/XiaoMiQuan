package com.xiaomiquan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circledialog.res.drawable.RadiuBg;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UiHeplUtils;
import com.xiaomiquan.utils.UserSet;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CoinMarketAdapter extends CommonAdapter<ExchangeData> {


    private TextView tv_num;
    private FontTextview tv_coin_type;
    private FontTextview tv_coin_market_value;
    private FontTextview tv_coin_price;
    private RoundedImageView ic_piv;
    private FontTextview tv_gains;
    private LinearLayout lin_root;
    private FrameLayout fl_root;
    private FontTextview tv_coin_probably;
    private FontTextview tv_coin_unit;

    public CoinMarketAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_coin_market, datas);
    }


    @Override
    protected void convert(ViewHolder holder, ExchangeData s, final int position) {
        tv_coin_type = holder.getView(R.id.tv_coin_type);
        tv_coin_price = holder.getView(R.id.tv_coin_price);
        tv_gains = holder.getView(R.id.tv_gains);
        ic_piv = holder.getView(R.id.ic_piv);
        fl_root = holder.getView(R.id.fl_root);
        lin_root = holder.getView(R.id.lin_root);
        tv_coin_unit = holder.getView(R.id.tv_coin_unit);
        tv_coin_market_value = holder.getView(R.id.tv_coin_market_value);
        tv_num = holder.getView(R.id.tv_num);
        tv_coin_probably = holder.getView(R.id.tv_coin_probably);
        ic_piv.setEnabled(false);

        tv_num.setText(UiHeplUtils.numIntToString(position + 1, 2));
        tv_coin_market_value = holder.getView(R.id.tv_coin_market_value);
        tv_coin_type.setText(s.getSymbol());
        tv_coin_unit.setText(s.getName());



        String s1 = BigUIUtil.getinstance().rateOnePrice(s.getPriceUsd(), s.getSymbol(), UserSet.getinstance().getUSDUnit());
        String s2 = BigUIUtil.getinstance().rateOnePrice(s.getMarketCapUsd(), UserSet.getinstance().getUSDUnit(), UserSet.getinstance().getUSDUnit());
        s2=s2.substring(1,s2.length());
        tv_coin_market_value.setText(CommonUtils.getString(R.string.str_market_value) + "  " + BigUIUtil.getinstance().bigMarkValue(s2));
        if (TextUtils.isEmpty(s1)) {
            tv_coin_price.setText("--");
        } else {
            tv_coin_price.setText(s1);
        }
        tv_coin_probably.setVisibility(View.GONE);


        ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
        if (!TextUtils.isEmpty(s.getPercentChange24h())) {
            if (new BigDecimal(s.getPercentChange24h()).compareTo(new BigDecimal("0")) == 1) {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
            } else {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getDropColor()), 10, 10, 10, 10));
            }
        }
        tv_gains.setText(BigUIUtil.getinstance().changeAmount(s.getPercentChange24h()) + "%");
    }


    public void setDatas(List<ExchangeData> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }


}