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
import com.xiaomiquan.base.BigUIUtil;
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
    //implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>
    int[] bgIds = {R.drawable.ic_value_bg1, R.drawable.ic_value_bg2, R.drawable.ic_value_bg3, R.drawable.ic_value_bg4, R.drawable.ic_value_bg5};

    private FontTextview tv_coin_type;
    private FontTextview tv_coin_price;
    private FontTextview tv_coin_probably;
    private FontTextview tv_gains;
    private FontTextview tv_coin_market_value;
    private LinearLayout lin_root;
    private RoundedImageView ic_piv;
    private FrameLayout fl_root;
    private FontTextview tv_name;
    private FontTextview tv_coin_unit;

    public ExchangeMarketAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_exchange_coin, datas);
        setHasStableIds(true);
    }

    @Override
    protected void convert(ViewHolder holder, ExchangeData s, final int position) {
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
        tv_coin_unit.setText("/" + s.getUnit());
        tv_coin_market_value.setText(CommonUtils.getString(R.string.str_amount) + "  " + BigUIUtil.getinstance().bigAmount(s.getVolume()) + "/" + BigUIUtil.getinstance().bigAmount(s.getAmount()));
        tv_coin_price.setText(BigUIUtil.getinstance().bigPrice(s.getLast()));

        tv_coin_probably.setText("≈" + s.getChoicePrice());

        tv_coin_probably.setVisibility(TextUtils.isEmpty(s.getChoicePrice()) ? View.GONE : View.VISIBLE);

        if (!TextUtils.isEmpty(s.getChange())) {
            if (new BigDecimal(s.getChange()).compareTo(new BigDecimal("0")) == 1) {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
            } else {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getDropColor()), 10, 10, 10, 10));
            }
        }
        ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
        tv_gains.setText(s.getChange() + "%");


    }

    public void setDatas(List<ExchangeData> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

}