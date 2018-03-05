package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/21.
 */

public class CommentAdapter extends CommonAdapter<Comment> {

    public TextView tv_name;
    public TextView tv_con;
    private TextView tv_re;
    private TextView tv_rename;
    private LinearLayout lin_comment;

    public CommentAdapter(Context context, List<Comment> datas) {
        super(context, R.layout.adapter_comment, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Comment comment, int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_con = holder.getView(R.id.tv_con);
        tv_re = holder.getView(R.id.tv_re);
        tv_rename = holder.getView(R.id.tv_rename);
        lin_comment = holder.getView(R.id.lin_comment);
        if (comment.isReply()) {
            tv_re.setVisibility(View.VISIBLE);
            tv_rename.setVisibility(View.VISIBLE);
            tv_rename.setText(comment.getReUserNickName() + ":");
            tv_name.setText(comment.getNickName());
            tv_con.setText(comment.getContent());
        } else {
            tv_re.setVisibility(View.GONE);
            tv_rename.setVisibility(View.GONE);
            tv_name.setText(comment.getNickName() + ":");
            tv_con.setText(comment.getContent());
        }
    }

}
