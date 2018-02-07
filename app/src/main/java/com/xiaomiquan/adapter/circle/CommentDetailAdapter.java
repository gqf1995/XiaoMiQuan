package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andy on 2018/1/21.
 */

public class CommentDetailAdapter extends CommonAdapter<Comment> {


    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_master;
    private TextView tv_time;
    private TextView tv_con;
    private TextView tv_rename;
    private TextView tv_recon;
    private LinearLayout lin_recall;

    public CommentDetailAdapter(Context context, List<Comment> datas) {
        super(context, R.layout.adapter_details_comment, datas);
    }

    public void setDatas(List<Comment> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, Comment comment, int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_master = holder.getView(R.id.tv_master);
        tv_time = holder.getView(R.id.tv_time);
        tv_con = holder.getView(R.id.tv_con);
        tv_rename = holder.getView(R.id.tv_rename);
        tv_recon = holder.getView(R.id.tv_recon);
        lin_recall = holder.getView(R.id.lin_recall);

        if (comment.isReply()) {
            lin_recall.setVisibility(View.VISIBLE);
            tv_con.setVisibility(View.GONE);
            tv_rename.setText(comment.getReUserNickName());
            tv_recon.setText(comment.getContent());
        } else {
            lin_recall.setVisibility(View.GONE);
            tv_con.setVisibility(View.VISIBLE);
            tv_con.setText(comment.getContent());
        }

        tv_time.setText(comment.getCreateTimeStr());
        GlideUtils.loadImage(comment.getAvatar(), cv_head);
        tv_name.setText(comment.getNickName());
    }

}
