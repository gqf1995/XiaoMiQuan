package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class MyPropertyDetailAdapter extends CommonAdapter<String> {


    private AppCompatImageView ic_pic;
    private TextView tv_name;
    private TextView tv_now_price;
    private TextView tv_num;
    private TextView tv_operation;

    public MyPropertyDetailAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_myproperty_detail, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_now_price = holder.getView(R.id.tv_now_price);
        tv_num = holder.getView(R.id.tv_num);
        tv_operation = holder.getView(R.id.tv_operation);

        tv_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
