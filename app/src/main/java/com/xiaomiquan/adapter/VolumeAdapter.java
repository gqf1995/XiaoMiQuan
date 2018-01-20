package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.UiHeplUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class VolumeAdapter extends CommonAdapter<String> {


    private TextView tv_num;
    private TextView tv_name;
    private FontTextview tv_price;
    private FontTextview tv_volume;

    public VolumeAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_volume, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_num = holder.getView(R.id.tv_num);
        tv_name = holder.getView(R.id.tv_name);
        tv_price = holder.getView(R.id.tv_price);
        tv_volume = holder.getView(R.id.tv_volume);
        tv_num.setText(UiHeplUtils.numIntToString(position, 2));

    }

}