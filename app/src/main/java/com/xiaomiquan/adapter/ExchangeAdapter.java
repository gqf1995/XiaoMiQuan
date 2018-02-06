package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ExchangeAdapter extends CommonAdapter<ExchangeName> {


    private ImageView iv_pic;
    private TextView tv_name;

    public ExchangeAdapter(Context context, List<ExchangeName> datas) {
        super(context, R.layout.adapter_exchange, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ExchangeName s, final int position) {
        iv_pic = holder.getView(R.id.iv_pic);
        tv_name = holder.getView(R.id.tv_name);
        iv_pic.setImageResource(R.drawable.dogecoin);
        tv_name.setText(s.getEname());

    }

}