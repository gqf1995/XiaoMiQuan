package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/3/10.
 */

public class TopicAdapter extends CommonAdapter<String> {


    private TextView tv_title;
    private TextView tv_topic_brief;
    private ImageView iv_topic;

    public TopicAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_square_topic, datas);
    }

    public void setDatas(List<String> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, String comment, int position) {
        tv_title = holder.getView(R.id.tv_title);
        tv_topic_brief = holder.getView(R.id.tv_topic_brief);
        iv_topic = holder.getView(R.id.iv_topic);
    }

}
