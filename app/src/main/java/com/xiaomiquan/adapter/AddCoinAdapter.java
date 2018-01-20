package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class AddCoinAdapter extends CommonAdapter<String> {


    private FontTextview tv_name;
    private AppCompatImageView iv_select;

    private List<Integer> selectPostion;

    public void select(int position) {
        if (selectPostion.contains(position)) {
            selectPostion.remove(selectPostion.indexOf(position));
        } else {
            selectPostion.add(position);
        }
        this.notifyItemChanged(position);
    }

    public AddCoinAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_add_coin, datas);
        selectPostion = new ArrayList<>();
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        iv_select = holder.getView(R.id.iv_select);
        tv_name = holder.getView(R.id.tv_name);
        tv_name.setText(s);
        if (selectPostion.contains(position)) {
            iv_select.setBackgroundResource(R.color.color_blue);
            tv_name.setTextColor(CommonUtils.getColor(R.color.color_blue));
        } else {
            iv_select.setBackgroundResource(R.color.white);
            tv_name.setTextColor(CommonUtils.getColor(R.color.color_font1));
        }
    }

}