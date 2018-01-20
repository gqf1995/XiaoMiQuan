package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiaomiquan.R;
import com.xiaomiquan.base.UserSet;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.circle.CircleContent;
import com.xiaomiquan.widget.circle.CommentListView;
import com.xiaomiquan.widget.circle.PraiseListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleContentAdapter extends CommonAdapter<CircleContent>{

    CircleImageView con_head;
    FontTextview con_name;
    FontTextview con_time;
    PraiseListView praiseListView;
    CommentListView commentList;


    public CircleContentAdapter(Context context, List<CircleContent> datas) {
        super(context,R.layout.adapter_circle_con,datas);
    }

    @Override
    protected void convert(ViewHolder holder, CircleContent circleContent, int position) {
        con_head=holder.getView(R.id.con_head);
        con_name=holder.getView(R.id.con_name);
        con_time=holder.getView(R.id.con_time);
        praiseListView=holder.getView(R.id.praiseListView);
        commentList=holder.getView(R.id.commentList);

    }
}