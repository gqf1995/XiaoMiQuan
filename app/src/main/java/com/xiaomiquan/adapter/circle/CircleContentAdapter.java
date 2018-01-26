package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.xiaomiquan.entity.bean.circle.UserTopic;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleContentAdapter extends CommonAdapter<UserTopic> {

    CircleImageView cv_head;
    TextView tv_name;
    TextView tv_time;
    TextView tv_con;
    RecyclerView rv_comment;
    TextView tv_praise;
    TextView tv_comment;
    TextView tv_comment_num;
    TextView tv_praise_num;

    List<Comment> comments;
    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public CircleContentAdapter(Context context, List<UserTopic> datas) {
        super(context, R.layout.adapter_circle_con, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserTopic circleContent, final int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        tv_con = holder.getView(R.id.tv_con);
        rv_comment = holder.getView(R.id.rv_comment);


        tv_praise = holder.getView(R.id.tv_praise);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);
        tv_praise_num = holder.getView(R.id.tv_praise_num);

        tv_name.setText(circleContent.getNickName());
        tv_time.setText(circleContent.getCreateTimeStr());
        tv_con.setText(circleContent.getContent());
        tv_praise_num.setText(circleContent.getPraiseQty() + "");
        tv_comment_num.setText(circleContent.getCommentQty() + "");
        if (circleContent.getCommentList() != null) {
            CommentAdapter commentAdapter = new CommentAdapter(mContext, circleContent.getCommentList());
            rv_comment.setLayoutManager(new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            rv_comment.setAdapter(commentAdapter);
        }
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
        tv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
    }


}