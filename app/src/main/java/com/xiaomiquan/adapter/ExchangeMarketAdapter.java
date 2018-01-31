package com.xiaomiquan.adapter;

import android.animation.ValueAnimator;
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

    public ExchangeMarketAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_exchange_coin, datas);
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
        } else {
            ic_piv.setBackground(new RadiuBg(CommonUtils.getColor(UserSet.getinstance().getRiseColor()), 10, 10, 10, 10));
        }
        tv_gains.setText(BigUIUtil.getinstance().changeAmount(s.getChange()) + "%");
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
                        boolean isShowAnim = false;
                        List<String> oldStrings = BigUIUtil.getinstance().rateTwoPrice(oldData.getLast(), oldData.getSymbol(), oldData.getUnit());
                        final TextView tv_coin_price_color = holder.getView(R.id.tv_coin_price);
                        final TextView tv_coin_probably_color = holder.getView(R.id.tv_coin_probably);
                        if (!s.getLast().equals(oldData.getLast())) {
                            isShowAnim = true;
                            if (new BigDecimal(s.getLast()).compareTo(new BigDecimal(oldData.getLast())) == 1) {
                                //老价格大于新价格
                                tv_coin_price_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
                                tv_coin_probably_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
                                isShowAnim = true;
                            } else if (new BigDecimal(s.getLast()).compareTo(new BigDecimal(oldData.getLast())) == -1) {
                                tv_coin_price_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
                                tv_coin_probably_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
                                isShowAnim = true;
                            }
                        }
                        //动画
                        if (!TextUtils.isEmpty(oldStrings.get(0)) && !TextUtils.isEmpty(strings.get(0))) {
                            String s1 = oldStrings.get(0);
                            String s2 = strings.get(0);
                            s1 = s1.substring(1, s1.length());
                            s2 = s2.substring(1, s2.length());
                            //对比价格
                            if (new BigDecimal(s1).compareTo(new BigDecimal(s2)) == 1) {
                                //老价格大于新价格
                                tv_coin_price_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
                                isShowAnim = true;
                            } else if (new BigDecimal(s1).compareTo(new BigDecimal(s2)) == -1) {
                                tv_coin_price_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
                                isShowAnim = true;
                            }
                        }
                        if (!TextUtils.isEmpty(oldStrings.get(1)) && !TextUtils.isEmpty(strings.get(1))) {
                            //对比价格
                            String s1 = oldStrings.get(1);
                            String s2 = strings.get(1);
                            s1 = s1.substring(1, s1.length());
                            s2 = s2.substring(1, s2.length());
                            if (new BigDecimal(s1).compareTo(new BigDecimal(s2)) == 1) {
                                //老价格大于新价格
                                isShowAnim = true;
                                tv_coin_probably_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
                            } else if (new BigDecimal(s1).compareTo(new BigDecimal(s2)) == -1) {
                                tv_coin_probably_color.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
                                isShowAnim = true;
                            }
                            isShowAnim = true;
                        }
                        final TextView tv_gains_color = holder.getView(R.id.tv_gains);
                        tv_gains_color.setTextColor(CommonUtils.getColor(R.color.color_blue));
                        if (isShowAnim) {
                            if (animatorMap.containsKey(position)) {
                                Iterator iter = animatorMap.entrySet().iterator();
                                while (iter.hasNext()) {
                                    Map.Entry entry = (Map.Entry) iter.next();
                                    ValueAnimator val = (ValueAnimator) entry.getValue();
                                    int key = (int) entry.getKey();
                                    if (key == position) {
                                        if (val.isRunning()) {
                                            val.cancel();
                                        }
                                        animatorMap.remove(val);
                                    }
                                }
                            }
                            ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
                            anim.setDuration(1000);
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    float currentValue = (float) animation.getAnimatedValue();
                                    if (currentValue == 1f) {
                                        //动画结束
                                        tv_coin_price_color.setTextColor(CommonUtils.getColor(R.color.color_font1));
                                        tv_coin_probably_color.setTextColor(CommonUtils.getColor(R.color.color_font2));
                                        tv_gains_color.setTextColor(CommonUtils.getColor(R.color.white));
                                    }
                                }
                            });
                            anim.start();
                            animatorMap.put(position, anim);
                        }
                    }
                }
            }
        }
    }


    Map<Integer, ValueAnimator> animatorMap;
    Map<Integer, ExchangeData> exchangeDataMap;

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public void updataOne(int position, ExchangeData data) {
        if (mDatas.size() > 0) {
            if (animatorMap == null) {
                animatorMap = new LinkedHashMap<>();
            }
            if (exchangeDataMap == null) {
                exchangeDataMap = new LinkedHashMap<>();
            }
            if (!data.getOnlyKey().equals(getDatas()
                    .get(position).getOnlyKey())) {
                return;
            }
            boolean isSameChange = false;
            boolean isSameLast = false;
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