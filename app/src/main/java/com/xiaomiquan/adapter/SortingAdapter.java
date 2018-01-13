package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class SortingAdapter extends CommonAdapter<String> {


    private AppCompatImageView iv_start;
    private FontTextview tv_name;
    private FontTextview tv_coin;
    private AppCompatImageView iv_select;

    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public SortingAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_sorting, datas);
    }

    public void setData(List<String> datas) {
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        iv_start = holder.getView(R.id.iv_start);
        tv_name = holder.getView(R.id.tv_name);
        tv_coin = holder.getView(R.id.tv_coin);
        iv_select = holder.getView(R.id.iv_select);
        tv_name.setText(position + "");
        if (position == 0) {
            iv_select.setVisibility(View.GONE);
        } else {
            iv_select.setVisibility(View.VISIBLE);
        }
        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, R.id.iv_select);
                }
            }
        });
        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, R.id.iv_start);
                }
            }
        });

    }

}