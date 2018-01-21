package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Commend;
import com.xiaomiquan.entity.bean.circle.UserTopic;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleContentAdapter extends CommonAdapter<UserTopic> {

    CircleImageView con_head;
    FontTextview con_name;
    FontTextview con_time;
    FontTextview con_con;
    RecyclerView topic_commend;
    TextView pinglun;
    TextView zan;
    TextView zan_num;
    TextView pinglun_num;

    List<Commend> commends;
    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public CircleContentAdapter(Context context, List<UserTopic> datas) {
        super(context, R.layout.adapter_circle_con, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserTopic circleContent, final int position) {
        con_head = holder.getView(R.id.con_head);
        con_name = holder.getView(R.id.con_name);
        con_time = holder.getView(R.id.con_time);
        con_con = holder.getView(R.id.con_con);
        pinglun = holder.getView(R.id.pinglun);


        zan = holder.getView(R.id.zan);
        zan_num = holder.getView(R.id.zan_num);
        pinglun_num = holder.getView(R.id.pinglun_num);
        topic_commend = holder.getView(R.id.topic_commend);

        con_name.setText(circleContent.getNickName());
        con_time.setText(circleContent.getCreateTimeStr());
        con_con.setText(circleContent.getContent());
        zan_num.setText(circleContent.getPraiseQty() + "");
        pinglun_num.setText(circleContent.getCommentQty() + "");
        if (circleContent.getCommentList() != null) {
            CommendAdapter commendAdapter = new CommendAdapter(mContext, circleContent.getCommentList());
            topic_commend.setLayoutManager(new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            topic_commend.setAdapter(commendAdapter);
        }
        pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
    }


}