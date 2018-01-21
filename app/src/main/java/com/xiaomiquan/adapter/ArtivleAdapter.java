package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ArticleData;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ArtivleAdapter extends CommonAdapter<ArticleData> {


    private TextView tv_name;
    private TextView tv_detail;
    private TextView tv_author;
    private ImageView ic_pic;
    private TextView tv_like_num;
    private TextView tv_comments_num;

    public ArtivleAdapter(Context context, List<ArticleData> datas) {
        super(context, R.layout.adapter_article, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ArticleData s, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_detail = holder.getView(R.id.tv_detail);
        tv_author = holder.getView(R.id.tv_author);
        ic_pic = holder.getView(R.id.ic_pic);
        tv_like_num = holder.getView(R.id.tv_like_num);
        tv_comments_num = holder.getView(R.id.tv_comments_num);

        tv_name.setText("文章名: " + s.getAvatar());
        tv_detail.setText("文章内容: " + s.getContent());
        tv_author.setText("作者: " + s.getNickName());
        tv_like_num.setText("点赞数: " + s.getGoodCount());
        tv_comments_num.setText("评论数: " + s.getCommentCount());

        GlideUtils.loadImage(s.getTitleImg(), ic_pic);

    }

}