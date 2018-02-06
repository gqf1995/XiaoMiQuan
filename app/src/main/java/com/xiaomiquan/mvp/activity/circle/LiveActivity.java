package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleAllDvpAdapter;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.entity.bean.circle.Praise;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.GetFriendsJoinBinder;
import com.xiaomiquan.mvp.databinder.circle.LiveBinder;
import com.xiaomiquan.mvp.databinder.circle.NewsBinder;
import com.xiaomiquan.mvp.delegate.GetFriendsJoinDelegate;
import com.xiaomiquan.mvp.delegate.circle.LiveDelegate;
import com.xiaomiquan.mvp.delegate.circle.NewsDelegate;
import com.xiaomiquan.mvp.fragment.circle.SquareFragment;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;
import java.util.List;

public class LiveActivity extends BaseDataBindActivity<NewsDelegate, NewsBinder> {

    SquareLiveAdapter squareLiveAdapter;

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_tv_live)).setSubTitle(CommonUtils.getString(R.string.str_release)));
        addRequest(binder.getLive(this));
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        ReleaseDynamicActivity.startAct(LiveActivity.this, "2", "1");

    }

    public void initLive(final List<SquareLive> squareLives) {
        viewDelegate.viewHolder.tv_time.setText(squareLives.get(0).getYearMonthDay());
        squareLiveAdapter = new SquareLiveAdapter(binder, LiveActivity.this, squareLives);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        squareLiveAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                if (squareLives.get(position).getType().equals("1")) {
                    ArticleDetailsActivity.startAct(LiveActivity.this, squareLives.get(position));
                } else {
                    TopicDetailActivity.startAct(LiveActivity.this, squareLives.get(position));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        squareLiveAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, final int position, Object item) {
                if (view.getId() == R.id.tv_comment) {
                    if (squareLives.get(position).getType().equals("1")) {
                        ArticleDetailsActivity.startAct(LiveActivity.this, squareLives.get(position));
                    } else {
                        TopicDetailActivity.startAct(LiveActivity.this, squareLives.get(position));
                    }
                }
                if (view.getId() == R.id.cv_head) {
                    UserInfoActivity.startAct(LiveActivity.this, squareLives.get(position));
                }
            }
        });
        viewDelegate.viewHolder.pull_recycleview.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
        viewDelegate.viewHolder.pull_recycleview.setAdapter(squareLiveAdapter);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initLive(datas);
                break;
            case 0x124:

                break;
            case 0x127:

                break;
        }
    }

}
