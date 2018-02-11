package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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

public class ExchangeMarketAdapter extends CommonAdapter<ExchangeData> {
    //implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>
    int[] bgIds = {R.drawable.ic_value_bg1, R.drawable.ic_value_bg2, R.drawable.ic_value_bg3, R.drawable.ic_value_bg4, R.drawable.ic_value_bg5};
    boolean isFirst = false;
    TextView tv_coin_type;
    TextView tv_coin_price;
    TextView tv_coin_probably;
    TextView tv_gains;
    TextView tv_coin_market_value;
    LinearLayout lin_root;
    RoundedImageView ic_piv;
    FrameLayout fl_root;
    TextView tv_name;
    TextView tv_coin_unit;
    DecelerateInterpolator decelerateInterpolator;
    boolean isRedRise = false;

    public void checkRedRise(RecyclerView.Adapter adapter) {
        if (isRedRise != UserSet.getinstance().isRedRise()) {
            adapter.notifyDataSetChanged();
        }
        isRedRise = UserSet.getinstance().isRedRise();
    }

    public ExchangeMarketAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_exchange_coin, datas);
        decelerateInterpolator = new DecelerateInterpolator();
        isRedRise = UserSet.getinstance().isRedRise();
    }


    @Override
    protected void convert(final ViewHolder holder, ExchangeData s, final int position) {
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
        tv_coin_market_value.setText(CommonUtils.getString(R.string.str_amount) + BigUIUtil.getinstance().bigAmount(s.getVolume()));

        List<String> strings = BigUIUtil.getinstance().rateTwoPrice(s.getLast(), s.getSymbol(), s.getUnit());
        if (TextUtils.isEmpty(strings.get(0))) {
            tv_coin_price.setText("--");
        } else {
            tv_coin_price.setText(Html.fromHtml(strings.get(0)));
        }
        tv_coin_probably.setText(Html.fromHtml(strings.get(1)));
        if (TextUtils.isEmpty(strings.get(1))) {
            tv_coin_probably.setVisibility(View.GONE);
        } else {
            tv_coin_probably.setVisibility(View.VISIBLE);
        }
        tv_coin_price.setTextColor(CommonUtils.getColor(R.color.color_font1));
        tv_coin_probably.setTextColor(CommonUtils.getColor(R.color.color_font2));
        tv_gains.setText(BigUIUtil.getinstance().changeAmount(s.getChange()) + "%");
        if (!TextUtils.isEmpty(s.getChange())) {
            if (new BigDecimal(s.getChange()).compareTo(new BigDecimal("0")) == 1) {
                ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
                tv_gains.setText("+"+BigUIUtil.getinstance().changeAmount(s.getChange()) + "%");
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
                        TextView tv_coin_price_color = holder.getView(R.id.tv_coin_price);
                        TextView tv_coin_probably_color = holder.getView(R.id.tv_coin_probably);
                        BigUIUtil.getinstance().anim(s.getUnit(),tv_coin_price_color, oldData.getLast(), s.getLast(), CommonUtils.getColor(R.color.color_font1), s.getOnlyKey());
                        BigUIUtil.getinstance().anim(s.getUnit(),tv_coin_probably_color, oldData.getLast(), s.getLast(), CommonUtils.getColor(R.color.color_font2), s.getOnlyKey());
                    }
                }
            }
        }
    }


    Map<Integer, ExchangeData> exchangeDataMap;

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public void updataOne(int position, ExchangeData data) {
        if (mDatas.size() > 0) {
            if (exchangeDataMap == null) {
                exchangeDataMap = new LinkedHashMap<>();
            }
            if (!data.getOnlyKey().equals(getDatas()
                    .get(position).getOnlyKey())) {
                return;
            }
            boolean isSameChange = false;
            boolean isSameLast;
            //涨幅 和 价格 如果为空则不变
            if (TextUtils.isEmpty(data.getChange())) {
                data.setChange(getDatas().get(position).getChange());
                isSameChange = true;
            } else {
                if (getDatas().get(position).getChange().equals(data.getChange())) {
                    isSameChange = true;
                }
            }
            if (TextUtils.isEmpty(data.getLast())) {
                data.setLast(getDatas().get(position).getLast());
                isSameLast = true;
            } else {
                if (getDatas().get(position).getLast().equals(data.getLast())) {
                    isSameLast = true;
                } else {
                    isSameLast = false;
                }
            }
            if (isSameChange && isSameLast) {
                return;
            }
            exchangeDataMap.put(position, getDatas().get(position));
            getDatas().set(position, data);
            notifyItemChanged(position);
        }
    }

    public void setDatas(List<ExchangeData> datas) {
        getDatas().clear();
        getDatas().addAll(datas);
        this.notifyDataSetChanged();
    }

}