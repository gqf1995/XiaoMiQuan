package com.xiaomiquan.mvp.activity.circle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleAllDvpAdapter;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.activity.market.SearchCoinMarketActivity;
import com.xiaomiquan.mvp.databinder.GetFriendsJoinBinder;
import com.xiaomiquan.mvp.delegate.GetFriendsJoinDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class GetFriendsJoinActivity extends BasePullActivity<GetFriendsJoinDelegate, GetFriendsJoinBinder> {

    CircleAllDvpAdapter circleAllDvpAdapter;

    @Override
    protected Class<GetFriendsJoinDelegate> getDelegateClass() {
        return GetFriendsJoinDelegate.class;
    }

    @Override
    public GetFriendsJoinBinder getDataBinder(GetFriendsJoinDelegate viewDelegate) {
        return new GetFriendsJoinBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_search_edit_circle)).setShowBack(false).setSubTitle(CommonUtils.getString(R.string.str_pop_cancle)));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        initToolBarSearch();
        initCircle(new ArrayList<UserCircle>());
    }

    private void initCircle(final List<UserCircle> userCircles) {
        if (circleAllDvpAdapter == null) {
            refreshData();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            circleAllDvpAdapter = new CircleAllDvpAdapter(GetFriendsJoinActivity.this, userCircles);
            circleAllDvpAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                    CirclePreviewActivity.startAct(GetFriendsJoinActivity.this, userCircles.get(position));
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            initRecycleViewPull(circleAllDvpAdapter, new LinearLayoutManager(GetFriendsJoinActivity.this));
        } else {
            getDataBack(circleAllDvpAdapter.getDatas(), userCircles, circleAllDvpAdapter);
        }
    }


    @Override
    protected void refreshData() {
        addRequest(binder.getCircleMore(this));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                List<UserCircle> datas = GsonUtil.getInstance().toList(data, UserCircle.class);
                initCircle(datas);
                break;
            case 0x124:
                addRequest(binder.getCircleMore(this));
                break;
        }
    }

    public EditText et_search;
    public TextView subTitle;

    //给toolbar添加搜索布局
    private void initToolBarSearch() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_top_search, null);
        viewDelegate.getFl_content().addView(rootView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        layoutParams.leftMargin = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_40px);
        rootView.setLayoutParams(layoutParams);
        this.et_search = (EditText) rootView.findViewById(R.id.et_search);
        et_search.setHint(CommonUtils.getString(R.string.str_search_edit_circle));
        subTitle = viewDelegate.getmToolbarSubTitle();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                } else {

                }
            }
        });
        subTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });
    }

    private void onBack() {
        onBackPressed();
    }

}
