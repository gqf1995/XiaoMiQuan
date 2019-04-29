package com.xiaomiquan.mvp.delegate;

import com.xiaomiquan.R;


/**
 * Created by 郭青枫 on 2017/9/26.
 * 同一的 fragment列表页面 代理
 */

public class LoadMorePullDelegate extends BaseFragentPullDelegate {


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ppublic_loadmore_recycleview;
    }

}
