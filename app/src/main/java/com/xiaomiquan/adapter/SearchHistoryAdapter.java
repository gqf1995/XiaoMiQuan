package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/25 0025.
 */

public class SearchHistoryAdapter extends CommonAdapter<String> {


    private TextView tv_title;

    public SearchHistoryAdapter(Context context, List<String> datas) {
        super(context, R.layout.layout_flowtext, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_title = holder.getView(R.id.tv_title);
        tv_title.setText(s);
    }

}
