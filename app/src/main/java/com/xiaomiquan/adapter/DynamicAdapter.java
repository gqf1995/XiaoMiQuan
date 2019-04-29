package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class DynamicAdapter extends CommonAdapter<String> {


    private TextView tv_user_name;
    private TextView tv_master;
    private TextView tv_top;
    private IconFontTextview tv_time;
    private IconFontTextview tv_more;
    private FrameLayout fl_content;
    private IconFontTextview tv_praise;
    private IconFontTextview tv_message;
    private IconFontTextview tv_praise_people;
    private RecyclerView rv_message;
    private TextView tv_look_more_message;

    public DynamicAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_user_page, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_user_name = holder.getView(R.id.tv_user_name);
        tv_master = holder.getView(R.id.tv_master);
        tv_top = holder.getView(R.id.tv_top);
        tv_time = holder.getView(R.id.tv_time);
        tv_more = holder.getView(R.id.tv_more);
        fl_content = holder.getView(R.id.fl_content);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_message = holder.getView(R.id.tv_message);
        tv_praise_people = holder.getView(R.id.tv_praise_people);
        rv_message = holder.getView(R.id.rv_message);
        tv_look_more_message = holder.getView(R.id.tv_look_more_message);


    }

}