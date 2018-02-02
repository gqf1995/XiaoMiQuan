package com.xiaomiquan.mvp.activity.circle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.circle.ArtivleAdapter;
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
        initToolbar(new ToolbarBuilder().setTitle("热门文章"));
        addRequest(binder.getArticle(ArticleActivity.this));

    }

    public void initArticle(final List<SquareLive> squareLives) {
        initHeadView(squareLives.get(0));
        artivleAdapter = new ArtivleAdapter(ArticleActivity.this, squareLives);
        artivleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                TopicDetailActivity.startAct(ArticleActivity.this, squareLives.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        linearLayoutManager = new LinearLayoutManager(ArticleActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
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
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initArticle(datas);
                break;
        }
    }

    @Override
    protected void refreshData() {

    }
}
