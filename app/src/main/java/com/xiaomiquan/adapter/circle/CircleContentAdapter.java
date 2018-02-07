package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 谷乐园
 */

public class CircleContentAdapter extends CommonAdapter<SquareLive> {

    DefaultClickLinsener defaultClickLinsener;
    private CircleImageView cv_head;
    private TextView tv_name;
    private IconFontTextview tv_dotos;
    private TextView tv_time;
    private TextView tv_con;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private IconFontTextview tv_comment;
    private TextView tv_comment_num;
    private RecyclerView rv_praise;
    private RecyclerView rv_comment;
    private TextView tv_more;
    private ImageView cv_article;
    private TextView tv_title;
    private TextView tv_dynamic;
    private TextView tv_article;
    private LinearLayout lin_article;
    public Context context;


    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public CircleContentAdapter(Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_circle_con, datas);
        this.context = context;
    }

    public void setDatas(List<SquareLive> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, SquareLive squareLive, final int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        rv_comment = holder.getView(R.id.rv_comment);

        tv_dynamic = holder.getView(R.id.tv_dynamic);
        cv_article = holder.getView(R.id.cv_article);
        tv_title = holder.getView(R.id.tv_title);
        tv_article = holder.getView(R.id.tv_article);
        lin_article = holder.getView(R.id.lin_article);

        tv_praise = holder.getView(R.id.tv_praise);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        tv_name.setText(squareLive.getNickName());
        tv_time.setText(squareLive.getCreateTimeStr());
        tv_praise_num.setText(squareLive.getGoodCount());
        tv_comment_num.setText(squareLive.getCommentCount());
        GlideUtils.loadImage(squareLive.getAvatar(), cv_head);

        if (squareLive.isUserPraise()) {
            tv_praise.setTextColor(context.getResources().getColor(R.color.color_blue));
        } else {
            tv_praise.setTextColor(context.getResources().getColor(R.color.color_font1));
        }

        if (squareLive.getType().equals("1")) {
            tv_dynamic.setText("发表了文章");
            lin_article.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(squareLive.getImg(), cv_article);
            tv_title.setText(squareLive.getTitle());
            tv_article.setText(squareLive.getContent());
        } else {
            tv_dynamic.setText(squareLive.getContent());
            lin_article.setVisibility(View.GONE);
        }


        if (squareLive.getCommentVos() != null) {
            CommentAdapter commentAdapter = new CommentAdapter(mContext, squareLive.getCommentVos());
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
        cv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
    }


}