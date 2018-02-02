package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class LiveDetailsAdapter extends CommonAdapter<Comment> {

    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_time;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private TextView tv_con;

    public LiveDetailsAdapter(Context context, List<Comment> datas) {
        super(context, R.layout.adapter_live_details, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Comment s, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        cv_head = holder.getView(R.id.cv_head);
        tv_time = holder.getView(R.id.tv_time);
        tv_con = holder.getView(R.id.tv_con);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        tv_praise = holder.getView(R.id.tv_praise);

        tv_con.setText(s.getContent());
        tv_name.setText(s.getNickName());
        tv_time.setText(s.getCreateTime());
        GlideUtils.loadImage(s.getImages(), cv_head);

    }

}