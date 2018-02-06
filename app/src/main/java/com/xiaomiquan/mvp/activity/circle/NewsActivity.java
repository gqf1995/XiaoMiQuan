package com.xiaomiquan.mvp.activity.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.NewsAdapter;
import com.xiaomiquan.entity.bean.circle.News;
import com.xiaomiquan.mvp.databinder.circle.NewsBinder;
import com.xiaomiquan.mvp.delegate.circle.NewsDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

import java.util.List;

public class NewsActivity extends BaseDataBindActivity<NewsDelegate, NewsBinder> {

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

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    private void initNews(List<News> newsList) {
        NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, newsList);


    }


}
