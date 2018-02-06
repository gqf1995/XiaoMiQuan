package com.xiaomiquan.mvp.activity.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.ArtivleAdapter;
import com.xiaomiquan.entity.bean.circle.Praise;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.databinder.circle.ArticleBinder;
import com.xiaomiquan.mvp.delegate.circle.ArticleDelegate;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class ArticleActivity extends BasePullActivity<ArticleDelegate, ArticleBinder> {

    ArtivleAdapter artivleAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected Class<ArticleDelegate> getDelegateClass() {
        return ArticleDelegate.class;
    }

    @Override
    public ArticleBinder getDataBinder(ArticleDelegate viewDelegate) {
        return new ArticleBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_tv_article)).setSubTitle(CommonUtils.getString(R.string.str_release)));
        viewDelegate.initToplinsener((int) CommonUtils.getDimensionPixelSize(R.dimen.trans_230px));
        addRequest(binder.getArticle(ArticleActivity.this));
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getArticle(ArticleActivity.this));
            }
        });

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        ReleaseArticleActivity.startAct(ArticleActivity.this, "1", "1", "0");
    }

    public void initArticle(final List<SquareLive> squareLives) {
        initHeadView(squareLives.get(0));
        squareLives.remove(0);
        artivleAdapter = new ArtivleAdapter(ArticleActivity.this, squareLives);
        artivleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                ArticleDetailsActivity.startAct(ArticleActivity.this, squareLives.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        artivleAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                if (artivleAdapter.isPraise.get(position).equals("false")) {
                    artivleAdapter.isPraise.add(position, "true");
                    artivleAdapter.paiseNum.add(position, Integer.parseInt(artivleAdapter.paiseNum.get(position)) + 1 + "");
                    artivleAdapter.notifyItemChanged(position);
                } else {
                    artivleAdapter.isPraise.add(position, "false");
                    artivleAdapter.paiseNum.add(position, Integer.parseInt(artivleAdapter.paiseNum.get(position)) - 1 + "");
                    artivleAdapter.notifyItemChanged(position);
                }
                addRequest(binder.savePraise(squareLives.get(position).getId(), ArticleActivity.this));
            }
        });
        linearLayoutManager = new LinearLayoutManager(ArticleActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.pull_recycleview.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
        viewDelegate.viewHolder.pull_recycleview.setAdapter(artivleAdapter);


    }

    private void initHeadView(final SquareLive squareLive) {
        /**
         * 用户是否点赞
         //                 */
        if (squareLive.getUserPraise().equals("false")) {
            viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
            viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
        } else {
            viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
            viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
        }
        viewDelegate.viewHolder.tv_time.setText(squareLive.getCreateTimeStr());
        viewDelegate.viewHolder.tv_title.setText(squareLive.getTitle());
        GlideUtils.loadImage(squareLive.getAvatar(), viewDelegate.viewHolder.iv_head);
        GlideUtils.loadImage(squareLive.getImg(), viewDelegate.viewHolder.iv_banner);
        viewDelegate.viewHolder.tv_name.setText(squareLive.getNickName());
        viewDelegate.viewHolder.tv_comment_num.setText(squareLive.getCommentCount());
        viewDelegate.viewHolder.tv_praise_num.setText(squareLive.getGoodCount());
        viewDelegate.viewHolder.lin_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 用户是否点赞
                 //                 */
                if (squareLive.getUserPraise().equals("false")) {
                    squareLive.setUserPraise("true");
                    squareLive.setGoodCount(Integer.parseInt(squareLive.getGoodCount()) + 1 + "");
                    viewDelegate.viewHolder.tv_praise_num.setText(Integer.parseInt(squareLive.getGoodCount()) + 1 + "");
                    viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
                    viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
                } else {
                    squareLive.setUserPraise("false");
                    squareLive.setGoodCount(Integer.parseInt(squareLive.getGoodCount()) - 1 + "");
                    viewDelegate.viewHolder.tv_praise_num.setText(Integer.parseInt(squareLive.getGoodCount()) - 1 + "");
                    viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
                    viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
                }
                addRequest(binder.savePraise(squareLive.getId(), ArticleActivity.this));
            }
        });

        viewDelegate.viewHolder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleDetailsActivity.startAct(ArticleActivity.this, squareLive);
            }
        });


    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initArticle(datas);
                break;
            case 0x124:
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getArticle(ArticleActivity.this));
    }
}
