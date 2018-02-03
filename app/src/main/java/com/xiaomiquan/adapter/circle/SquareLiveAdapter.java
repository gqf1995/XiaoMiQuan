package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Praise;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.fragment.circle.SquareFragment;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareLiveAdapter extends CommonAdapter<SquareLive> {

    DefaultClickLinsener defaultClickLinsener;

    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_time;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private IconFontTextview tv_comment;
    private TextView tv_comment_num;
    private ImageView iv_article;
    private ImageView iv_img;
    private TextView tv_title;
    private TextView tv_dynamic;
    private TextView tv_article;
    private LinearLayout lin_article;
    public Context context;

    public List<String> isPraise;
    public List<String> paiseNum;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public SquareLiveAdapter(Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_live, datas);
        this.context = context;
        this.isPraise = new ArrayList<>();
        this.paiseNum=new ArrayList<>();
    }

    @Override
    protected void convert(ViewHolder holder, final SquareLive s, final int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);
        tv_dynamic = holder.getView(R.id.tv_dynamic);
        iv_article = holder.getView(R.id.iv_article);
        tv_title = holder.getView(R.id.tv_title);
        tv_article = holder.getView(R.id.tv_article);
        lin_article = holder.getView(R.id.lin_article);
        iv_img = holder.getView(R.id.iv_img);

        isPraise.add(s.getUserPraise());
        paiseNum.add(s.getGoodCount());

        /**
         * 判断 文章、帖子
         */
        if (s.getType().equals("1")) {
            tv_dynamic.setText("发表了文章");
            lin_article.setVisibility(View.VISIBLE);
            iv_img.setVisibility(View.GONE);
            GlideUtils.loadImage(s.getImg(), iv_article);
            tv_title.setText(s.getTitle());
            tv_article.setText(Html.fromHtml(s.getContent()));
        } else {
            if (s.getImg() == null) {
                iv_img.setVisibility(View.GONE);
                tv_dynamic.setText(s.getContent());
                lin_article.setVisibility(View.GONE);
            } else {
                iv_img.setVisibility(View.VISIBLE);
                tv_dynamic.setText(s.getContent());
                lin_article.setVisibility(View.GONE);
                GlideUtils.loadImage(s.getImg(), iv_img);

            }

        }
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


        /**
         * 用户信息加载
         */
        tv_name.setText(s.getNickName());
        tv_time.setText(s.getCreateTimeStr());
        tv_comment_num.setText(s.getCommentCount());
        tv_praise_num.setText(paiseNum.get(position));
        GlideUtils.loadImage(s.getAvatar(), cv_head);


        /**
         * 点击事件
         */
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