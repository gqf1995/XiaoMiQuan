package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circledialog.res.drawable.RadiuBg;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CoinMarketAdapter extends CommonAdapter<ExchangeData> {


    private TextView tv_num;
    private TextView tv_coin_type;
    private TextView tv_coin_market_value;
    private TextView tv_coin_price;
    private RoundedImageView ic_piv;
    private TextView tv_gains;
    private LinearLayout lin_root;
    private FrameLayout fl_root;
    private TextView tv_coin_probably;
    private TextView tv_coin_unit;

    boolean isRedRise = false;

    public void checkRedRise(RecyclerView.Adapter adapter) {
        if (isRedRise != UserSet.getinstance().isRedRise()) {
            adapter.notifyDataSetChanged();
        }
        isRedRise = UserSet.getinstance().isRedRise();
    }

    public CoinMarketAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_coin_market, datas);
        isRedRise = UserSet.getinstance().isRedRise();
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

        tv_num.setText(s.getRank());
        tv_coin_market_value = holder.getView(R.id.tv_coin_market_value);
        tv_coin_type.setText(s.getSymbol());
        tv_coin_unit.setText(s.getName());


        String s1 = BigUIUtil.getinstance().rateMarketPrice(s.getPriceUsd(), s.getSymbol(), UserSet.getinstance().getUSDUnit());
        String s2 = BigUIUtil.getinstance().rateOnePrice(s.getMarketCapUsd(), s.getSymbol(), UserSet.getinstance().getUSDUnit());
        if (TextUtils.isEmpty(s2)) {
            tv_coin_market_value.setText(CommonUtils.getString(R.string.str_market_value) + "  ");
        } else {
            //s2 = s2.substring(1, s2.length());
            tv_coin_market_value.setText(Html.fromHtml(CommonUtils.getString(R.string.str_market_value) + "  " + s2));
        }
        if (TextUtils.isEmpty(s1)) {
            tv_coin_price.setText("--");
        } else {
            tv_coin_price.setText(Html.fromHtml(s1));
        }
        tv_coin_price.setTextColor(CommonUtils.getColor(R.color.color_font1));
        tv_coin_probably.setVisibility(View.GONE);

        tv_gains.setText(BigUIUtil.getinstance().changeAmount(s.getPercentChange24h()) + "%");
        ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
        if (!TextUtils.isEmpty(s.getPercentChange24h())) {
            if (new BigDecimal(s.getPercentChange24h()).compareTo(new BigDecimal("0")) == 1) {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
                tv_gains.setText(BigUIUtil.getinstance().changeAmount("+" + s.getPercentChange24h()) + "%");
            } else {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getDropColor()), 10, 10, 10, 10));
            }
        } else {
            ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
        }


        if (!isFirst) {
            if (exchangeDataMap != null) {
                ExchangeData oldData = null;
                Iterator it = exchangeDataMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    ExchangeData val = (ExchangeData) entry.getValue();
                    if (val.getOnlyKey().equals(s.getOnlyKey())) {
                        oldData = val;
                        exchangeDataMap.remove(val);
                        break;
                    }
                }
                if (oldData != null) {
                    if (s.getOnlyKey().equals(oldData.getOnlyKey())) {
                        BigUIUtil.getinstance().anim(s.getUnit(),
                                (TextView) holder.getView(R.id.tv_coin_price),
                                oldData.getPriceUsd(), s.getPriceUsd(),
                                CommonUtils.getColor(R.color.color_font1),
                                s.getOnlyKey(),position,
                                (TextView) holder.getView(R.id.tv_coin_price).getTag());
                    }
                }
            }
        }


    }

    boolean isFirst = false;
    Map<Integer, ExchangeData> exchangeDataMap;

    public void updataOne(int position, ExchangeData data) {
        if (mDatas.size() > 0) {
            if (exchangeDataMap == null) {
                exchangeDataMap = new LinkedHashMap<>();
            }
            if (!data.getOnlyKey().equals(getDatas()
                    .get(position).getOnlyKey())) {
                return;
            }
            boolean isSameLast;
            if (TextUtils.isEmpty(data.getPriceUsd())) {
                data.setLast(getDatas().get(position).getPriceUsd());
                isSameLast = true;
            } else {
                if (getDatas().get(position).getPriceUsd().equals(data.getPriceUsd())) {
                    isSameLast = true;
                } else {
                    isSameLast = false;
                }
            }
            if (isSameLast) {
                return;
            }
            exchangeDataMap.put(position, getDatas().get(position));
            getDatas().set(position, data);
            this.notifyItemChanged(position);
        }
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public void setDatas(List<ExchangeData> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }


}