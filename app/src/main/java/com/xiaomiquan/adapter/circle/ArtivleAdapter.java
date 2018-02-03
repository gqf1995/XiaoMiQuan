package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ArticleData;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 谷乐园
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

    public List<String> isPraise;
    public List<String> paiseNum;

    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public ArtivleAdapter(Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_article, datas);
        this.isPraise = new ArrayList<>();
        this.paiseNum = new ArrayList<>();
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

        isPraise.add(s.getUserPraise());
        paiseNum.add(s.getGoodCount());

        /**
         * 用户是否点赞
         */
        if (isPraise.get(position).equals("false")) {
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
        } else {
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
        }

        tv_time.setText(s.getCreateTimeStr());
        tv_title.setText(s.getTitle());
        tv_comment_num.setText(s.getCommentCount());
        tv_praise_num.setText(paiseNum.get(position));
        GlideUtils.loadImage(s.getImg(), ic_pic);
        GlideUtils.loadImage(s.getAvatar(), cv_head);

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