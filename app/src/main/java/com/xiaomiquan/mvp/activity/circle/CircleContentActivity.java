package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleContentAdapter;
import com.xiaomiquan.entity.bean.GroupOwner;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.entity.bean.circle.UserTopic;
import com.xiaomiquan.mvp.databinder.circle.CircleContentBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleContentDelegate;
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
        Intent intent = getIntent();
        userCircle = (UserCircle) intent.getParcelableExtra("userCircle");
        initToolbar(new ToolbarBuilder().setTitle("币圈神探").setSubTitle("发帖"));
        addRequest(binder.getCicleContent(userCircle.getId(), 1 + "", this));
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getCicleContent(userCircle.getId(), 1 + "", CircleContentActivity.this));
            }
        });
        userTopicList = new ArrayList<>();
        initUserTopic(userTopicList);
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        CircleDialogHelper.initDefaultInputDialog(CircleContentActivity.this, "发帖", "请输入内容", "发布", new OnInputClickListener() {
            @Override
            public void onClick(String text, View v) {
                ToastUtil.show(text);
                addRequest(binder.saveUsertopic(userCircle.getId() + "", text, CircleContentActivity.this));
            }
        }).show();
    }
    private void initEdit(){
        viewDelegate.viewHolder.lin_my_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(EditCircleActivity.class).startAct();
            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                String groupOwner1 = GsonUtil.getInstance().getValue(data, "groupOwner");
                if (groupOwner1 != null) {
                    GroupOwner groupOwner = GsonUtil.getInstance().toObj(groupOwner1, GroupOwner.class);
                }
                List<UserTopic> datas = GsonUtil.getInstance().toList(data, "list", UserTopic.class);
                getDataBack(userTopicList, datas, headerAndFooterWrapper);
                break;
            case 0x124:
            case 0x125:
            case 0x126:
                addRequest(binder.getCicleContent(userCircle.getId(), 1 + "", this));
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

        circleContentAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, final int position, Object item) {
                if (view.getId() == R.id.tv_comment) {
                    CircleDialogHelper.initDefaultInputDialog(CircleContentActivity.this, "评论", "请输入评论", "发布", new OnInputClickListener() {
                        @Override
                        public void onClick(String text, View v) {
                            addRequest(binder.saveComment(circleContentAdapter.getDatas().get(position - headerAndFooterWrapper.getHeadersCount()).getId() + "", 3 + "", text, CircleContentActivity.this));
                        }
                    }).show();
                }
                if (view.getId() == R.id.tv_praise) {
                    view.setEnabled(false);
                    addRequest(binder.savePraise(circleContentAdapter.getDatas().get(position - headerAndFooterWrapper.getHeadersCount()).getId(), CircleContentActivity.this));
                }
            }
        });
        headerAndFooterWrapper = new HeaderAndFooterWrapper(circleContentAdapter);
        headerAndFooterWrapper.addHeaderView(initTopView());
        initRecycleViewPull(headerAndFooterWrapper, headerAndFooterWrapper.getHeadersCount(), new LinearLayoutManager(CircleContentActivity.this));
        viewDelegate.setIsLoadMore(false);
    }

    int commentPosition = 0;
    public CircleImageView cv_head;
    public TextView tv_name;
    public TextView tv_creator;
    public TextView tv_num;

    private View initTopView() {
        View rootView = CircleContentActivity.this.getLayoutInflater().inflate(R.layout.layout_circle_con_top, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        this.tv_creator = (TextView) rootView.findViewById(R.id.tv_creator);
        this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);

        tv_name.setText(userCircle.getName() + "");
        tv_creator.setText("User" + userCircle.getUserId());
        tv_num.setText(userCircle.getBrief() + "");

        return rootView;
    }

    @Override
    protected void refreshData() {

    }


}
