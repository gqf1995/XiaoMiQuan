package com.xiaomiquan.mvp.delegate;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.CoinData;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;

import java.math.BigDecimal;
import java.util.List;

public class CoinDetailDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_detail;
    }

    public void initData(CoinData data, ExchangeData exchangeData) {
        //viewHolder.tv_name.setText(e.get);
        StringBuffer name = new StringBuffer("");
        if (TextUtils.isEmpty(data.getNameZh()) && !TextUtils.isEmpty(data.getNameEg())) {
            name.append(data.getNameZh());
        } else if (!TextUtils.isEmpty(data.getNameZh()) && TextUtils.isEmpty(data.getNameEg())) {
            name.append(data.getNameEg());
        } else if (!TextUtils.isEmpty(data.getNameZh()) && !TextUtils.isEmpty(data.getNameEg())) {
            name.append(data.getNameZh()).append(",").append(data.getNameEg());
        } else {
        }
        String s2 = BigUIUtil.getinstance().rateOnePrice(exchangeData.getMarketCapUsd(), UserSet.getinstance().getUSDUnit(), UserSet.getinstance().getUSDUnit());


        viewHolder.tv_name.setText(name.toString());
        viewHolder.lin_rank.setVisibility(TextUtils.isEmpty(exchangeData.getRank()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_market_value.setVisibility(TextUtils.isEmpty(s2) ? View.GONE : View.VISIBLE);
        viewHolder.lin_circulation_amount.setVisibility(TextUtils.isEmpty(data.getExistingCirculationVolume()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_total_amount.setVisibility(TextUtils.isEmpty(data.getMonetaryTotal()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_ico_time.setVisibility(TextUtils.isEmpty(data.getIcoTime()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_ico_cost.setVisibility(TextUtils.isEmpty(data.getIcoCost()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_development_people.setVisibility(TextUtils.isEmpty(data.getInventor()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_browser.setVisibility(TextUtils.isEmpty(data.getBlockBrowserUrl()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_website.setVisibility(TextUtils.isEmpty(data.getOfficalWebsiteUrl()) ? View.GONE : View.VISIBLE);
        viewHolder.lin_wallet_address.setVisibility(TextUtils.isEmpty(data.getWhitePaperUrl()) ? View.GONE : View.VISIBLE);

        viewHolder.tv_rank.setText(exchangeData.getRank());
        viewHolder.tv_market_value.setText(s2);
        viewHolder.tv_circulation_amount.setText(data.getExistingCirculationVolume());
        viewHolder.tv_total_amount.setText(data.getMonetaryTotal());
        viewHolder.tv_ico_time.setText(data.getIcoTime());
        viewHolder.tv_ico_cost.setText(data.getIcoCost());
        viewHolder.tv_development_people.setText(data.getInventor());
        viewHolder.tv_browser.setText(data.getBlockBrowserUrl());
        viewHolder.tv_website.setText(data.getOfficalWebsiteUrl());
        viewHolder.tv_wallet_address.setText(data.getWhitePaperUrl());

        GlideUtils.loadImage(data.getPicUrl(), viewHolder.iv_coin_icon);


        List<String> strings = BigUIUtil.getinstance().rateUSDAndCNY(exchangeData.getPriceUsd(), exchangeData.getSymbol(), UserSet.getinstance().getUSDUnit());

        viewHolder.tv_price_usd.setText(strings.get(0));
        viewHolder.tv_price_cny.setText(strings.get(1));

        String s = strings.get(0);
        String symbol = "";
        if (strings.get(0).contains("$") || strings.get(0).contains("¥")) {
            s = s.substring(1, strings.get(0).length());
            symbol = s.substring(0, 1);
        } else {
            s = exchangeData.getLast();
        }
        StringBuffer stringBuffer = new StringBuffer();
        String end = "";
        if (!TextUtils.isEmpty(exchangeData.getPercentChange24h())) {
            if (new BigDecimal("0").compareTo(new BigDecimal(exchangeData.getPercentChange24h())) == 1) {
                //跌
                stringBuffer.append("- ")
                        .append(symbol + BigUIUtil.getinstance().risePrice(s, exchangeData.getPercentChange24h()))
                        .append("(")
                        .append(BigUIUtil.getinstance().changeAmount(exchangeData.getPercentChange24h()))
                        .append("%) ");
                end = CommonUtils.getString(R.string.ic_Fall);
                viewHolder.tv_change.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
            } else {
                //涨
                stringBuffer.append("+ ")
                        .append(symbol + BigUIUtil.getinstance().risePrice(s, exchangeData.getPercentChange24h()))
                        .append("(+")
                        .append(BigUIUtil.getinstance().changeAmount(exchangeData.getPercentChange24h()))
                        .append("%) ");
                end = CommonUtils.getString(R.string.ic_Climb);
                viewHolder.tv_change.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
            }
        }

        viewHolder.tv_change.setText(stringBuffer.toString() + " " + end);

    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_price_usd;
        public TextView tv_price_cny;
        public TextView tv_change;
        public ImageView iv_coin_icon;
        public RecyclerView rv_global_market;
        public TextView tv_look_more_global_market;
        public TextView tv_content;
        public TextView tv_rank;
        public LinearLayout lin_rank;
        public TextView tv_market_value;
        public LinearLayout lin_market_value;
        public TextView tv_circulation_amount;
        public LinearLayout lin_circulation_amount;
        public TextView tv_total_amount;
        public LinearLayout lin_total_amount;
        public TextView tv_ico_time;
        public LinearLayout lin_ico_time;
        public TextView tv_ico_cost;
        public LinearLayout lin_ico_cost;
        public TextView tv_development_people;
        public LinearLayout lin_development_people;
        public TextView tv_browser;
        public LinearLayout lin_browser;
        public TextView tv_website;
        public LinearLayout lin_website;
        public TextView tv_wallet_address;
        public LinearLayout lin_wallet_address;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_price_usd = (TextView) rootView.findViewById(R.id.tv_price_usd);
            this.tv_price_cny = (TextView) rootView.findViewById(R.id.tv_price_cny);
            this.tv_change = (TextView) rootView.findViewById(R.id.tv_change);
            this.iv_coin_icon = (ImageView) rootView.findViewById(R.id.iv_coin_icon);
            this.rv_global_market = (RecyclerView) rootView.findViewById(R.id.rv_global_market);
            this.tv_look_more_global_market = (TextView) rootView.findViewById(R.id.tv_look_more_global_market);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.tv_rank = (TextView) rootView.findViewById(R.id.tv_rank);
            this.lin_rank = (LinearLayout) rootView.findViewById(R.id.lin_rank);
            this.tv_market_value = (TextView) rootView.findViewById(R.id.tv_market_value);
            this.lin_market_value = (LinearLayout) rootView.findViewById(R.id.lin_market_value);
            this.tv_circulation_amount = (TextView) rootView.findViewById(R.id.tv_circulation_amount);
            this.lin_circulation_amount = (LinearLayout) rootView.findViewById(R.id.lin_circulation_amount);
            this.tv_total_amount = (TextView) rootView.findViewById(R.id.tv_total_amount);
            this.lin_total_amount = (LinearLayout) rootView.findViewById(R.id.lin_total_amount);
            this.tv_ico_time = (TextView) rootView.findViewById(R.id.tv_ico_time);
            this.lin_ico_time = (LinearLayout) rootView.findViewById(R.id.lin_ico_time);
            this.tv_ico_cost = (TextView) rootView.findViewById(R.id.tv_ico_cost);
            this.lin_ico_cost = (LinearLayout) rootView.findViewById(R.id.lin_ico_cost);
            this.tv_development_people = (TextView) rootView.findViewById(R.id.tv_development_people);
            this.lin_development_people = (LinearLayout) rootView.findViewById(R.id.lin_development_people);
            this.tv_browser = (TextView) rootView.findViewById(R.id.tv_browser);
            this.lin_browser = (LinearLayout) rootView.findViewById(R.id.lin_browser);
            this.tv_website = (TextView) rootView.findViewById(R.id.tv_website);
            this.lin_website = (LinearLayout) rootView.findViewById(R.id.lin_website);
            this.tv_wallet_address = (TextView) rootView.findViewById(R.id.tv_wallet_address);
            this.lin_wallet_address = (LinearLayout) rootView.findViewById(R.id.lin_wallet_address);
        }

    }
}