package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.base.UserSet;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CoinIndexAdapter extends CommonAdapter<String> {


    private FontTextview tv_coin_name;
    private FontTextview tv_index;
    private FontTextview tv_price;
    private LinearLayout lin_root;

    public CoinIndexAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_coin_index, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_coin_name = holder.getView(R.id.tv_coin_name);
        tv_index = holder.getView(R.id.tv_index);
        tv_price = holder.getView(R.id.tv_price);
        lin_root = holder.getView(R.id.lin_root);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) lin_root.getLayoutParams();
        if (position == 0) {
            layoutParams.leftMargin = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_20px);
        } else {
            layoutParams.leftMargin = 0;
        }
        lin_root.setLayoutParams(layoutParams);
        tv_index.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));

    }

}