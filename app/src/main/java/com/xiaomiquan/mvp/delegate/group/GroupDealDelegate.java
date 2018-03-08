package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.NoParentsTouchFramelayout;
import com.tablayout.CommonTabLayout;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import skin.support.widget.SkinCompatImageView;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupDealDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    private ArrayList<CustomTabEntity> mTabEntitiesTop = new ArrayList<>();
    public CoinDetail mCoinDetail;
    public int selectType = 0;
    List<String> dataset2;

    public void initTop() {
        dataset2 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_price_type));
        selectType = 0;
        viewHolder.tv_choose_txt.setText(dataset2.get(0));
        viewHolder.lin_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCoinDetail != null) {
                    if (selectType == 0) {
                        selectType = 1;
                        viewHolder.tv_choose_txt.setText(dataset2.get(1));
                        viewHolder.et_sell_price.setEnabled(false);
                        viewHolder.et_sell_price.setText(BigUIUtil.getinstance().bigPrice(mCoinDetail.getPriceUsd()));
                    } else {
                        selectType = 0;
                        viewHolder.tv_choose_txt.setText(dataset2.get(0));
                        viewHolder.et_sell_price.setEnabled(true);
                    }
                }
            }
        });
        viewHolder.et_sell_num.addTextChangedListener(textWatcher);
        viewHolder.et_sell_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.et_sell_num.setText(null);
                viewHolder.et_sell_num.setHint("输入个数");
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            price(s.toString());
        }
    };

    private void price(String price) {
        if (!TextUtils.isEmpty(price)) {
            return;
        }
        if (!TextUtils.isEmpty(price)) {
            BigDecimal btcMoney = new BigDecimal(price);
            btcMoney = btcMoney.setScale(4, BigDecimal.ROUND_UP);
            String end = btcMoney.toString();
            if (!price.equals(end)) {
                viewHolder.et_sell_num.removeTextChangedListener(textWatcher);
                viewHolder.et_sell_num.setText(end);
                viewHolder.et_sell_num.addTextChangedListener(textWatcher);
            } else {
                viewHolder.et_sell_num.removeTextChangedListener(textWatcher);
                viewHolder.et_sell_num.setText("");
                viewHolder.et_sell_num.addTextChangedListener(textWatcher);
            }
        } else {
            viewHolder.et_sell_num.removeTextChangedListener(textWatcher);
            viewHolder.et_sell_num.setText("");
            viewHolder.et_sell_num.addTextChangedListener(textWatcher);
        }
    }

    public void setUpdata(String data) {
        String price = GsonUtil.getInstance().getValue(data, "priceUsd");
        String symbol = GsonUtil.getInstance().getValue(data, "symbol");
        List<String> strings = BigUIUtil.getinstance().rateUSDAndCNY(price, symbol, UserSet.getinstance().getUSDUnit());

        viewHolder.tv_sell_price.setText("$" + BigUIUtil.getinstance().bigPrice(price));
        viewHolder.tv_buy_price.setText(strings.get(1));
        if (GsonUtil.getInstance().getValue(data, "count") != null) {
            viewHolder.tv_hole.setText(GsonUtil.getInstance().getValue(data, "count"));
        }

    }

    public void onSelectLinsener(CoinDetail coinDetail) {
        mCoinDetail = coinDetail;
        if (coinDetail == null) {
            viewHolder.tv_buy_price.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_hole.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_sell_price.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_coin_type.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_unit.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_hold_unit.setText("");
            return;
        }
        if (selectType == 1) {
            viewHolder.et_sell_price.setText(BigUIUtil.getinstance().bigPrice(mCoinDetail.getPriceUsd()));
            viewHolder.et_sell_price.setEnabled(false);
        } else {
            viewHolder.et_sell_price.setEnabled(true);
        }
        viewHolder.tv_unit.setText(coinDetail.getSymbol());
        if (coinDetail.getCount() != null) {
            viewHolder.tv_hole.setText(coinDetail.getCount());
        }
        List<String> strings = BigUIUtil.getinstance().rateUSDAndCNY(coinDetail.getPriceUsd(), coinDetail.getSymbol(), UserSet.getinstance().getUSDUnit());
        viewHolder.tv_sell_price.setText(strings.get(0));
        viewHolder.tv_buy_price.setText(strings.get(1));

        GlideUtils.loadImage(coinDetail.getPicUrl(), viewHolder.ic_pic);
        viewHolder.tv_poundage.setText(coinDetail.getFee() + "%");

        viewHolder.tv_coin_type.setText(coinDetail.getSymbol());
    }

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_table, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_180px), viewHolder.vp_sliding, false);
        initTop();
        viewHolder.tv_all.setOnClickListener(onClickListener);
        viewHolder.tv_half_hold.setOnClickListener(onClickListener);
        viewHolder.tv_half_half_hold.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_all:
                    //买全部
                    sellChoose(v.getId());

                    break;
                case R.id.tv_half_hold:
                    //买一半
                    sellChoose(v.getId());

                    break;
                case R.id.tv_half_half_hold:
                    //买四分之一
                    sellChoose(v.getId());

                    break;
            }
        }
    };

    public String buynum;
    public String salenum;

    private void sellChoose(int id) {
        if (TextUtils.isEmpty(viewHolder.et_sell_price.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_input_price));
            return;
        }
        //返回卖出数量
        salenum = saleNum(id);

        BigDecimal hold = new BigDecimal(viewHolder.tv_usable.getText().toString());
        if (id == R.id.tv_all) {
            viewHolder.et_sell_num.setText("全仓");
        } else if (id == R.id.tv_half_hold) {
            hold = hold.multiply(new BigDecimal("0.5"));
            viewHolder.et_sell_num.setText("50%仓");
        } else if (id == R.id.tv_half_half_hold) {
            hold = hold.multiply(new BigDecimal("0.25"));
            viewHolder.et_sell_num.setText("25%仓");
        }
        BigDecimal price = new BigDecimal(viewHolder.et_sell_price.getText().toString());
        BigDecimal num = hold.divide(price, 4, BigDecimal.ROUND_UP);
        buynum = num.toPlainString();
    }

    private String saleNum(int id) {
        if (CommonUtils.getString(R.string.str_now_no_data).equals(viewHolder.tv_hole.getText().toString())) {
            return viewHolder.tv_hole.getText().toString();
        } else {
            if (id == R.id.tv_all) {
                return viewHolder.tv_hole.getText().toString();
            } else if (id == R.id.tv_half_hold) {
                BigDecimal bigDecimal = new BigDecimal(viewHolder.tv_hole.getText().toString());
                bigDecimal = bigDecimal.multiply(new BigDecimal("0.5")).setScale(4, BigDecimal.ROUND_UP);
                return bigDecimal.toPlainString();
            } else if (id == R.id.tv_half_half_hold) {
                BigDecimal bigDecimal = new BigDecimal(viewHolder.tv_hole.getText().toString());
                bigDecimal = bigDecimal.multiply(new BigDecimal("0.25")).setScale(4, BigDecimal.ROUND_UP);
                return bigDecimal.toPlainString();
            }
        }
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simulated_trading;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_total_assets;
        public TextView tv_usable;
        public TextView tv_assets_report;
        public SkinCompatImageView iv_banner;
        public TextView tv_input_label1;
        public NoParentsTouchFramelayout fl_currency;
        public AppCompatImageView ic_pic;
        public TextView tv_coin_type;
        public TextView tv_sell_price;
        public LinearLayout lin_sell;
        public TextView tv_buy_price;
        public TextView tv_price_label;
        public EditText et_sell_price;
        public TextView tv_choose_txt;
        public IconFontTextview tv_drop;
        public LinearLayout lin_choose;
        public TextView tv_num_label;
        public EditText et_sell_num;
        public TextView tv_unit;
        public TextView tv_all;
        public TextView tv_half_hold;
        public TextView tv_half_half_hold;
        public TextView tv_hole;
        public TextView tv_hold_unit;
        public TextView tv_poundage;
        public TextView tv_sale;
        public TextView tv_buy;
        public CommonTabLayout tl_2;
        public LinearLayout lin_table;
        public ViewPager vp_sliding;
        public JudgeNestedScrollView nestedScrollView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_total_assets = (TextView) rootView.findViewById(R.id.tv_total_assets);
            this.tv_usable = (TextView) rootView.findViewById(R.id.tv_usable);
            this.tv_assets_report = (TextView) rootView.findViewById(R.id.tv_assets_report);
            this.iv_banner = (SkinCompatImageView) rootView.findViewById(R.id.iv_banner);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.fl_currency = (NoParentsTouchFramelayout) rootView.findViewById(R.id.fl_currency);
            this.ic_pic = (AppCompatImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_coin_type = (TextView) rootView.findViewById(R.id.tv_coin_type);
            this.tv_sell_price = (TextView) rootView.findViewById(R.id.tv_sell_price);
            this.lin_sell = (LinearLayout) rootView.findViewById(R.id.lin_sell);
            this.tv_buy_price = (TextView) rootView.findViewById(R.id.tv_buy_price);
            this.tv_price_label = (TextView) rootView.findViewById(R.id.tv_price_label);
            this.et_sell_price = (EditText) rootView.findViewById(R.id.et_sell_price);
            this.tv_choose_txt = (TextView) rootView.findViewById(R.id.tv_choose_txt);
            this.tv_drop = (IconFontTextview) rootView.findViewById(R.id.tv_drop);
            this.lin_choose = (LinearLayout) rootView.findViewById(R.id.lin_choose);
            this.tv_num_label = (TextView) rootView.findViewById(R.id.tv_num_label);
            this.et_sell_num = (EditText) rootView.findViewById(R.id.et_sell_num);
            this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
            this.tv_all = (TextView) rootView.findViewById(R.id.tv_all);
            this.tv_half_hold = (TextView) rootView.findViewById(R.id.tv_half_hold);
            this.tv_half_half_hold = (TextView) rootView.findViewById(R.id.tv_half_half_hold);
            this.tv_hole = (TextView) rootView.findViewById(R.id.tv_hole);
            this.tv_hold_unit = (TextView) rootView.findViewById(R.id.tv_hold_unit);
            this.tv_poundage = (TextView) rootView.findViewById(R.id.tv_poundage);
            this.tv_sale = (TextView) rootView.findViewById(R.id.tv_sale);
            this.tv_buy = (TextView) rootView.findViewById(R.id.tv_buy);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        }

    }
}



