package com.xiaomiquan.mvp.activity.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.NewsAdapter;
import com.xiaomiquan.entity.bean.circle.News;
import com.xiaomiquan.mvp.databinder.circle.NewsBinder;
import com.xiaomiquan.mvp.delegate.circle.NewsDelegate;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BasePullActivity<NewsDelegate, NewsBinder> {

    NewsAdapter newsAdapter;

    @Override
    protected Class<NewsDelegate> getDelegateClass() {
        return NewsDelegate.class;
    }

    @Override
    public NewsBinder getDataBinder(NewsDelegate viewDelegate) {
        return new NewsBinder(viewDelegate);

    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_tv_news)));
        initNews(new ArrayList<News>());
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getNews(NewsActivity.this));
            }
        });

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                break;
            case 0x124:
                List<News> newsList = GsonUtil.getInstance().toList(data, News.class);
                initNews(newsList);
                break;
        }
    }

    private void initNews(List<News> newsList) {
        if (newsAdapter == null) {
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            newsAdapter = new NewsAdapter(binder, NewsActivity.this, newsList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewsActivity.this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            viewDelegate.viewHolder.pull_recycleview.setLayoutManager(linearLayoutManager);
            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
            viewDelegate.viewHolder.pull_recycleview.setAdapter(newsAdapter);
        } else {
            newsAdapter.setDatas(newsList);
        }

    }


    @Override
    protected void refreshData() {
        addRequest(binder.getNews(NewsActivity.this));
    }
}
