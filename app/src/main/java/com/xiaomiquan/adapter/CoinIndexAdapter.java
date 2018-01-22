package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.entity.bean.CoinIndex;
import com.xiaomiquan.utils.UiHeplUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CoinIndexAdapter extends CommonAdapter<CoinIndex> {


    private FontTextview tv_coin_name;
    private FontTextview tv_index;
    private FontTextview tv_price;
    private LinearLayout lin_root;
    int[] ints;

    public CoinIndexAdapter(Context context, List<CoinIndex> coinIndices) {
        super(context, R.layout.adapter_coin_index, coinIndices);
        ints = UiHeplUtils.cacularWidAndHei(context, R.dimen.trans_80px, 3, 1, 1);
    }

    public void setDatas(List<CoinIndex> coinIndices) {
        this.mDatas.clear();
        this.mDatas.addAll(coinIndices);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, CoinIndex s, final int position) {
        tv_coin_name = holder.getView(R.id.tv_coin_name);
        tv_index = holder.getView(R.id.tv_index);
        tv_price = holder.getView(R.id.tv_price);
        lin_root = holder.getView(R.id.lin_root);
        tv_index.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        tv_coin_name.setText(s.getName());
        UiHeplUtils.setCacularWidAndHei(ints, lin_root);

    }

}