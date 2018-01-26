package com.xiaomiquan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.circledialog.res.drawable.RadiuBg;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ExchangeMarketAdapter extends CommonAdapter<ExchangeData> {
    //implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>
    int[] bgIds = {R.drawable.ic_value_bg1, R.drawable.ic_value_bg2, R.drawable.ic_value_bg3, R.drawable.ic_value_bg4, R.drawable.ic_value_bg5};
    private String defaultUnit;

    //设置汇率
    public void setDefaultUnit(String defaultUnit) {
        this.defaultUnit = defaultUnit;
        this.notifyDataSetChanged();
    }

    public ExchangeMarketAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_exchange_coin, datas);
        defaultUnit = UserSet.getinstance().getUnit();
    }

    @Override
    protected void convert(ViewHolder holder, ExchangeData s, final int position) {

        FontTextview tv_coin_type;
        FontTextview tv_coin_price;
        FontTextview tv_coin_probably;
        FontTextview tv_gains;
        FontTextview tv_coin_market_value;
        LinearLayout lin_root;
        RoundedImageView ic_piv;
        FrameLayout fl_root;
        FontTextview tv_name;
        FontTextview tv_coin_unit;


        tv_coin_type = holder.getView(R.id.tv_coin_type);
        tv_coin_price = holder.getView(R.id.tv_coin_price);
        tv_coin_probably = holder.getView(R.id.tv_coin_probably);
        tv_gains = holder.getView(R.id.tv_gains);
        ic_piv = holder.getView(R.id.ic_piv);
        fl_root = holder.getView(R.id.fl_root);
        lin_root = holder.getView(R.id.lin_root);
        tv_name = holder.getView(R.id.tv_name);
        tv_coin_market_value = holder.getView(R.id.tv_coin_market_value);
        tv_coin_unit = holder.getView(R.id.tv_coin_unit);
        ic_piv.setEnabled(false);

        tv_coin_market_value = holder.getView(R.id.tv_coin_market_value);
        tv_name.setText(s.getExchange());
        tv_coin_type.setText(s.getSymbol());
        tv_coin_unit.setText(s.getUnit());
        tv_coin_market_value.setText(CommonUtils.getString(R.string.str_amount) + BigUIUtil.getinstance().bigAmount(s.getAmount()));
        tv_coin_price.setText(s.getLast());

        List<String> strings = BigUIUtil.getinstance().rateTwoPrice(s.getLast(),s.getSymbol(),s.getUnit());
        if(TextUtils.isEmpty(strings.get(0))){
            tv_coin_price.setText("--");
        }else {
            tv_coin_price.setText(strings.get(0));
        }
        tv_coin_probably.setText(strings.get(1));
        if (TextUtils.isEmpty(strings.get(1))) {
            tv_coin_probably.setVisibility(View.GONE);
        } else {
            tv_coin_probably.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(s.getChange())) {
            if (new BigDecimal(s.getChange()).compareTo(new BigDecimal("0")) == 1) {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
            } else {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getDropColor()), 10, 10, 10, 10));
            }
        }
        ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
        tv_gains.setText(BigUIUtil.getinstance().changeAmount(s.getChange()) + "%");


    }

    public void updataOne(int position, ExchangeData data) {
        mDatas.set(position, data);
        this.notifyItemChanged(position);
    }

    public void setDatas(List<ExchangeData> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

}