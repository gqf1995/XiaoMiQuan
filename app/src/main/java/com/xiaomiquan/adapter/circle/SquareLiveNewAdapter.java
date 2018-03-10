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
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.server.HttpUrl;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.api.widget.Widget;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;
import skin.support.widget.SkinCompatImageView;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareLiveNewAdapter extends CommonAdapter<SquareLive> {

    DefaultClickLinsener defaultClickLinsener;
    public Context context;
    BaseDataBind dataBind;
    DynamicPhotoAdapter dynamicPhotoAdapter;

    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_type_time;
    private IconFontTextview icf_more;
    private TextView tv_reason;
    private RecyclerView recycleview_reason;
    private ImageView iv_article;
    private TextView tv_article_title;
    private LinearLayout lin_article;
    private TextView tv_forecast_time;
    private TextView tv_forecast_info;
    private TextView tv_forecast_result;
    private LinearLayout lin_forecast;
    private TextView tv_title;
    private TextView tv_topic_brief;
    private ImageView iv_topic;
    private LinearLayout lin_topic;
    private CircleImageView cv_shard_head;
    private TextView tv_shared_name;
    private TextView icf_shared_attention;
    private TextView tv_shared_brief;
    private SkinCompatImageView iv_shared;
    private LinearLayout lin_shared_topic;
    private IconFontTextview icf_shared;
    private TextView tv_shared_num;
    private LinearLayout lin_shared;
    private IconFontTextview tv_comment;
    private TextView tv_comment_num;
    private LinearLayout lin_comment;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private LinearLayout lin_praise;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public void setDatas(List<SquareLive> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public SquareLiveNewAdapter(BaseDataBind baseDataBind, Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_square_new, datas);
        this.context = context;
        dataBind = baseDataBind;
    }

    @Override
    protected void convert(ViewHolder holder, final SquareLive s, final int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_type_time = holder.getView(R.id.tv_type_time);
        icf_more = holder.getView(R.id.icf_more);
        tv_reason = holder.getView(R.id.tv_reason);
        recycleview_reason = holder.getView(R.id.recycleview_reason);
        iv_article = holder.getView(R.id.iv_article);
        tv_article_title = holder.getView(R.id.tv_article_title);
        lin_article = holder.getView(R.id.lin_article);
        tv_forecast_time = holder.getView(R.id.tv_forecast_time);
        tv_forecast_info = holder.getView(R.id.tv_forecast_info);
        tv_forecast_result = holder.getView(R.id.tv_forecast_result);
        lin_forecast = holder.getView(R.id.lin_forecast);
        tv_title = holder.getView(R.id.tv_title);
        tv_topic_brief = holder.getView(R.id.tv_topic_brief);
        iv_topic = holder.getView(R.id.iv_topic);
        lin_topic = holder.getView(R.id.lin_topic);
        cv_shard_head = holder.getView(R.id.cv_shard_head);
        tv_shared_name = holder.getView(R.id.tv_shared_name);
        icf_shared_attention = holder.getView(R.id.icf_shared_attention);
        tv_shared_brief = holder.getView(R.id.tv_shared_brief);
        iv_shared = holder.getView(R.id.iv_shared);
        lin_shared_topic = holder.getView(R.id.lin_shared_topic);
        icf_shared = holder.getView(R.id.icf_shared);
        tv_shared_num = holder.getView(R.id.tv_shared_num);
        lin_shared = holder.getView(R.id.lin_shared);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);
        lin_comment = holder.getView(R.id.lin_comment);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        lin_praise = holder.getView(R.id.lin_praise);


        //加载图片
        initImg(s.getImgList());

        /**
         * 判断 文章、帖子
         */
        switch (s.getType()) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
        }
        lin_forecast.setVisibility(View.GONE);
        lin_shared_topic.setVisibility(View.GONE);
        lin_topic.setVisibility(View.GONE);
        if (s.getType().equals("1")) {
            tv_type_time.setText(CommonUtils.getString(R.string.str_tv_send_article) + s.getHourMinute());
            lin_article.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(s.getImg(), iv_article);
            tv_title.setText(s.getTitle());
            recycleview_reason.setVisibility(View.GONE);
            tv_article_title.setText(Html.fromHtml(s.getContent()));
        } else {
            tv_type_time.setText(s.getHourMinute());
            tv_reason.setText(s.getContent());
            recycleview_reason.setVisibility(View.VISIBLE);
            lin_article.setVisibility(View.GONE);
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
                if (SingSettingDBUtil.getUserLogin() != null) {
                    if (s.isUserPraise()) {
                        s.setUserPraise(false);
                        s.setGoodCount(s.getGoodCount() - 1);

                    } else {
                        s.setUserPraise(true);
                        s.setGoodCount(s.getGoodCount() + 1);
                    }
                    dataBind.addRequest(savePraise(dataBind, s.getId()));
                    notifyItemChanged(position);
                } else {
                    ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
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
        lin_article.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 图片预览
     */
    private void initImg(final List<String> path) {
        if (path != null) {
            dynamicPhotoAdapter = new DynamicPhotoAdapter(context, path);
            dynamicPhotoAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Album.gallery(context)
                            .widget(Widget.newDarkBuilder(context).title(CommonUtils.getString(R.string.str_img_preview)).build())
                            .checkedList((ArrayList<String>) path) // 要浏览的图片列表：ArrayList<String>。
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
            recycleview_reason.setLayoutManager(gridLayoutManager);
            recycleview_reason.setAdapter(dynamicPhotoAdapter);

        }
    }

}