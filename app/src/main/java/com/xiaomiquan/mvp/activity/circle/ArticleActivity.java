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
    int index;

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
                index = position;
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
        viewDelegate.viewHolder.pull_recycleview.setAdapter(artivleAdapter);


    }

    private void initHeadView(SquareLive squareLive) {
        viewDelegate.viewHolder.tv_time.setText(squareLive.getCreateTimeStr());
        viewDelegate.viewHolder.tv_title.setText(squareLive.getTitle());
        GlideUtils.loadImage(squareLive.getImg(), viewDelegate.viewHolder.iv_head);
        viewDelegate.viewHolder.tv_name.setText(squareLive.getNickName());
        viewDelegate.viewHolder.tv_comment_num.setText(squareLive.getCommentCount());
        viewDelegate.viewHolder.tv_praise_num.setText(squareLive.getGoodCount());
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
                Praise praise = GsonUtil.getInstance().toObj(data, Praise.class);
                artivleAdapter.isPraise.add(index, praise.getIspraise() + "");
                artivleAdapter.paiseNum.add(index, praise.getPraiseQty());
                artivleAdapter.notifyItemChanged(index);
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getArticle(ArticleActivity.this));
    }
}
