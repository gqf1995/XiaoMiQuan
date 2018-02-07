package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.server.HttpUrl;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.api.widget.Widget;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;

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
    private RecyclerView iv_img;
    private TextView tv_title;
    private TextView tv_dynamic;
    private TextView tv_article;
    private LinearLayout lin_article;
    private LinearLayout lin_praise;
    private LinearLayout lin_comment;
    public Context context;
    BaseDataBind dataBind;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public void setDatas(List<SquareLive> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public SquareLiveAdapter(BaseDataBind baseDataBind, Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_live, datas);
        this.context = context;
        dataBind = baseDataBind;
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
        lin_praise = holder.getView(R.id.lin_praise);
        lin_comment = holder.getView(R.id.lin_comment);

        if (s.getImgList() != null) {
            DynamicPhotoAdapter dynamicPhotoAdapter = new DynamicPhotoAdapter(context, s.getImgList());
            dynamicPhotoAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Album.gallery(context)
                            .widget(Widget.newDarkBuilder(context).title(CommonUtils.getString(R.string.str_img_preview)).build())
                            .checkedList((ArrayList<String>) s.getImgList()) // 要浏览的图片列表：ArrayList<String>。
                            .navigationAlpha(50) // Android5.0+的虚拟导航栏的透明度。
                            .checkable(false)
                            .start(); // 千万不要忘记调用start()方法。
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            iv_img.setLayoutManager(gridLayoutManager);
            iv_img.setAdapter(dynamicPhotoAdapter);
        }

        /**
         * 判断 文章、帖子
         */
        if (s.getType().equals("1")) {
            tv_dynamic.setText(CommonUtils.getString(R.string.str_tv_send_article));
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

            }

        }
        /**
         * 用户是否点赞
         */
        if (s.isUserPraise()) {

            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
        } else {
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
        }


        /**
         * 用户信息加载
         */
        tv_name.setText(s.getNickName());
        tv_time.setText(s.getHourMinute());
        tv_comment_num.setText(s.getCommentCount() + "");
        tv_praise_num.setText(s.getGoodCount() + "");
        GlideUtils.loadImage(s.getAvatar(), cv_head);


        /**
         * 点击事件
         */
        lin_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
        lin_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.isUserPraise()) {
                    s.setUserPraise(false);
                    s.setGoodCount(s.getGoodCount() - 1);

                } else {
                    s.setUserPraise(true);
                    s.setGoodCount(s.getGoodCount() + 1);
                }
                dataBind.addRequest(savePraise(dataBind, s.getId()));
                notifyItemChanged(position);
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

    /**
     * 点赞
     */
    private Disposable savePraise(BaseDataBind baseDataBind,
                                  String linkId
    ) {
        baseDataBind.getBaseMapWithUid();
        baseDataBind.put("linkId", linkId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().savePraise)
                .setShowDialog(false)
                .setRequestName("点赞")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseDataBind.getMap())
                .setRequestCallback(null)
                .build()
                .RxSendRequest();
    }

}