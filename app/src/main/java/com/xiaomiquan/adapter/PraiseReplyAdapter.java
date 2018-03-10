package com.xiaomiquan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.PraiseReplyItem;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class PraiseReplyAdapter extends CommonAdapter<PraiseReplyItem> {


    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_content;
    private TextView tv_reply;
    private CircleImageView ic_pic;

    public PraiseReplyAdapter(Context context, List<PraiseReplyItem> datas) {
        super(context, R.layout.adapter_praise_reply, datas);
    }

    @Override
    protected void convert(ViewHolder holder, PraiseReplyItem s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        tv_content = holder.getView(R.id.tv_content);
        tv_reply = holder.getView(R.id.tv_reply);

        GlideUtils.loadImage(s.getAvatar(),ic_pic);
        tv_name.setText(s.getNickName());
        tv_content.setText(s.getShowContent());
        if(1==s.getPraise()){
            //点赞
            tv_reply.setVisibility(View.GONE);
        }else{
            //回复
            tv_reply.setVisibility(View.VISIBLE);
            tv_reply.setText(s.getContent());
        }

    }

}