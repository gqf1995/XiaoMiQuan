package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
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

/**
 * Created by 谷乐园
 */

public class CircleContentAdapter extends CommonAdapter<SquareLive> {

    DefaultClickLinsener defaultClickLinsener;
    CommentAdapter commentAdapter;

    public Context context;
    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_time;
    private IconFontTextview icf_more;
    private TextView tv_dynamic;
    private RecyclerView rv_img;
    private ImageView iv_article;
    private TextView tv_title;
    private TextView tv_article;
    private LinearLayout lin_article;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private LinearLayout lin_praise_comment;
    private LinearLayout lin_praise_name;
    private LinearLayout lin_praise;
    private IconFontTextview tv_comment;
    private TextView tv_comment_num;
    private IconFontTextview icf_shared;
    private TextView tv_shared;
    private TextView tv_praise_name;
    private TextView tv_praise_people;
    private RecyclerView rv_comment;
    private TextView tv_more;
    BaseDataBind dataBind;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public CircleContentAdapter(BaseDataBind baseDataBind, Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_circle_con, datas);
        this.context = context;
        dataBind = baseDataBind;
    }

    public void setDatas(List<SquareLive> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, final SquareLive s, final int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        rv_comment = holder.getView(R.id.rv_comment);
        tv_dynamic = holder.getView(R.id.tv_dynamic);
        icf_more = holder.getView(R.id.icf_more);
        rv_img = holder.getView(R.id.rv_img);
        iv_article = holder.getView(R.id.iv_article);
        tv_title = holder.getView(R.id.tv_title);
        tv_article = holder.getView(R.id.tv_article);
        lin_article = holder.getView(R.id.lin_article);
        lin_praise = holder.getView(R.id.lin_praise);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        icf_shared = holder.getView(R.id.icf_shared);
        tv_shared = holder.getView(R.id.tv_shared);
        tv_praise_name = holder.getView(R.id.tv_praise_name);
        tv_praise_people = holder.getView(R.id.tv_praise_people);
        lin_praise_comment = holder.getView(R.id.lin_praise_comment);
        lin_praise_name = holder.getView(R.id.lin_praise_name);
        tv_more = holder.getView(R.id.tv_more);

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
            rv_img.setLayoutManager(gridLayoutManager);
            rv_img.setAdapter(dynamicPhotoAdapter);
        }

        if (s.getCommentCount() == 0 && s.getGoodCount() == 0) {
            lin_praise_comment.setVisibility(View.GONE);
        } else {
            lin_praise_comment.setVisibility(View.VISIBLE);
        }

        if (s.getGoodCount() > 0) {
            lin_praise_name.setVisibility(View.VISIBLE);
            tv_praise_name.setText(s.getPraiseStr());
        } else {
            lin_praise_name.setVisibility(View.GONE);
        }

        if (s.getCommentCount() > 0) {

            rv_comment.setVisibility(View.VISIBLE);
            tv_more.setVisibility(View.VISIBLE);
            tv_more.setText("点击查看全部" + s.getCommentCount() + "条回复");
            if (s.getCommentCount() >= 5) {
                commentAdapter= new CommentAdapter(mContext, s.getCommentVos().subList(0, 5));
            } else {
               commentAdapter = new CommentAdapter(mContext, s.getCommentVos());
            }
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            rv_comment.setLayoutManager(gridLayoutManager);
            rv_comment.setAdapter(commentAdapter);
        } else {
            rv_comment.setVisibility(View.GONE);
            tv_more.setVisibility(View.GONE);
        }

        /**
         * 判断 文章、帖子
         */
        if (s.getType().equals("1")) {
            tv_dynamic.setText("发表了文章");
            lin_article.setVisibility(View.VISIBLE);
            rv_img.setVisibility(View.GONE);
            GlideUtils.loadImage(s.getImg(), iv_article);
            tv_title.setText(s.getTitle());
            tv_article.setText(Html.fromHtml(s.getContent()));
        } else {
            if (s.getImg() == null) {
                rv_img.setVisibility(View.GONE);
                tv_dynamic.setText(s.getContent());
                lin_article.setVisibility(View.GONE);
            } else {
                rv_img.setVisibility(View.VISIBLE);
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
        tv_time.setText(s.getCreateTimeStr());
        tv_comment_num.setText(s.getCommentCount() + "");
        tv_praise_num.setText(s.getGoodCount() + "");
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
        lin_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.isUserPraise()) {
                    s.setUserPraise(false);
                    s.setGoodCount(s.getGoodCount() - 1);
                    s.setPraiseStr(SingSettingDBUtil.getUserLogin().getNickName()+"、"+s.getPraiseStr());
                } else {
                    s.setUserPraise(true);
                    s.setGoodCount(s.getGoodCount() + 1);
                    s.setPraiseStr(s.getPraiseStr().replace(SingSettingDBUtil.getUserLogin().getNickName()+"、",""));
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