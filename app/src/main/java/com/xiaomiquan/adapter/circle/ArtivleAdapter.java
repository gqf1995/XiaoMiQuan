package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ArticleData;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ArtivleAdapter extends CommonAdapter<SquareLive> {


    private ImageView ic_pic;
    private TextView tv_time;
    private TextView tv_title;
    private IconFontTextview tv_comment;
    private TextView tv_comment_num;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private CircleImageView cv_head;

    public ArtivleAdapter(Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_article, datas);
    }

    @Override
    protected void convert(ViewHolder holder, SquareLive s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_time = holder.getView(R.id.tv_time);
        tv_title = holder.getView(R.id.tv_title);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        cv_head = holder.getView(R.id.cv_head);

        tv_time.setText(s.getCreateTimeStr());
        tv_title.setText(s.getTitle());
        tv_comment_num.setText(s.getCommentCount());
        tv_praise_num.setText(s.getGoodCount());
        GlideUtils.loadImage(s.getImg(), ic_pic);
        GlideUtils.loadImage(s.getAvatar(), cv_head);


    }

}