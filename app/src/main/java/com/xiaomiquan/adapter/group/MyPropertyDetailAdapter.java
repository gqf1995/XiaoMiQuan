package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class MyPropertyDetailAdapter extends CommonAdapter<CoinDetail> {


    private AppCompatImageView ic_pic;
    private TextView tv_name;
    private TextView tv_now_price;
    private TextView tv_num;
    private TextView tv_operation;

    public MyPropertyDetailAdapter(Context context, List<CoinDetail> datas) {
        super(context, R.layout.adapter_myproperty_detail, datas);
    }
    public void setDatas(List<CoinDetail> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, CoinDetail s, int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_now_price = holder.getView(R.id.tv_now_price);
        tv_num = holder.getView(R.id.tv_num);
        tv_operation = holder.getView(R.id.tv_operation);

        GlideUtils.loadImage(s.getPicUrl(), ic_pic);
        tv_name.setText(s.getSymbol());
        tv_now_price.setText(BigUIUtil.getinstance().bigPrice(s.getPriceUsd()));
        tv_num.setText(BigUIUtil.getinstance().bigPrice(s.getCount()));


        tv_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
