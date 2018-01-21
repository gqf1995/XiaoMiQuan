package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.icu.lang.UScript;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.CircleContentAdapter;
import com.xiaomiquan.entity.bean.GroupOwner;
import com.xiaomiquan.entity.bean.circle.CircleContent;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.entity.bean.circle.UserTopic;
import com.xiaomiquan.mvp.databinder.CircleContentBinder;
import com.xiaomiquan.mvp.delegate.CircleContentDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class CircleContentActivity extends BasePullActivity<CircleContentDelegate, CircleContentBinder> {

    CircleContentAdapter circleContentAdapter;
    HeaderAndFooterWrapper headerAndFooterWrapper;
    List<UserTopic> userTopicList;
    UserCircle userCircle;

    @Override
    protected Class<CircleContentDelegate> getDelegateClass() {
        return CircleContentDelegate.class;
    }

    @Override
    public CircleContentBinder getDataBinder(CircleContentDelegate viewDelegate) {
        return new CircleContentBinder(viewDelegate);
    }



    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        Intent intent=getIntent();
        userCircle= (UserCircle) intent.getSerializableExtra("userCircle");
        initToolbar(new ToolbarBuilder().setTitle("币圈神探").setSubTitle("发帖"));
        addRequest(binder.getCicleContent(userCircle.getId(),1,this));
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        Intent intent = new Intent();
        intent.putExtra("groupId",userCircle.getId());
        gotoActivity(UserTopicActivity.class).setIntent(intent).startAct();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
//                viewDelegate.viewHolder.lin_root.setVisibility(View.VISIBLE);
                String groupOwner1 = GsonUtil.getInstance().getValue(data, "groupOwner");
                if (groupOwner1 != null){
                GroupOwner groupOwner=GsonUtil.getInstance().toObj(groupOwner1,GroupOwner.class);}
                userTopicList = GsonUtil.getInstance().toList(data,"list",UserTopic.class);
                initUserTopic(userTopicList);
                break;
        }
    }

    private void initUserTopic(List<UserTopic> circleContents) {
        circleContentAdapter = new CircleContentAdapter(CircleContentActivity.this, circleContents);
        circleContentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        headerAndFooterWrapper = new HeaderAndFooterWrapper(circleContentAdapter);
        headerAndFooterWrapper.addHeaderView(initTopView());
//        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        initRecycleViewPull(headerAndFooterWrapper, headerAndFooterWrapper.getHeadersCount(),new LinearLayoutManager(CircleContentActivity.this));
        viewDelegate.setIsLoadMore(false);
    }

    public CircleImageView dvp_head;
    public FontTextview dvp_name;
    public FontTextview dvp_creater;
    public FontTextview dvp_num;

    private View initTopView() {
        View rootView = CircleContentActivity.this.getLayoutInflater().inflate(R.layout.layout_circle_con_top, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.dvp_head=(CircleImageView)rootView.findViewById(R.id.dvp_head);
        this.dvp_name=(FontTextview)rootView.findViewById(R.id.dvp_name);
        this.dvp_creater=(FontTextview)rootView.findViewById(R.id.dvp_creater);
        this.dvp_num=(FontTextview)rootView.findViewById(R.id.dvp_num);

        dvp_name.setText(userCircle.getName()+"");
        dvp_creater.setText("User"+userCircle.getUserId());
        dvp_num.setText(userCircle.getBrief()+"");

        return rootView;
    }

    @Override
    protected void refreshData() {

    }
}