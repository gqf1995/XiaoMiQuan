package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class SearchAddCoinAdapter extends CommonAdapter<String> {


    private FontTextview tv_name;
    private AppCompatImageView iv_select;

    private List<Integer> selectPostion;
    private FontTextview tv_coin;

    public void select(int position) {
        if (selectPostion.contains(position)) {
            selectPostion.remove(selectPostion.indexOf(position));
        } else {
            selectPostion.add(position);
        }
        //直接请求添加

        this.notifyItemChanged(position);
    }

    public SearchAddCoinAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_search_add_coin, datas);
        selectPostion = new ArrayList<>();
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        iv_select = holder.getView(R.id.iv_select);
        tv_name = holder.getView(R.id.tv_name);
        tv_coin = holder.getView(R.id.tv_coin);
        if (selectPostion.contains(position)) {
            iv_select.setBackgroundResource(R.color.color_blue);
        } else {
            iv_select.setBackgroundResource(R.color.white);
        }
    }

}