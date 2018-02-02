package com.xiaomiquan.mvp.activity.circle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.circle.CircleAllDvpAdapter;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.GetFriendsJoinBinder;
import com.xiaomiquan.mvp.delegate.GetFriendsJoinDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.mvp.fragment.circle.CircleDvpFragment;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class GetFriendsJoinActivity extends BaseDataBindActivity<GetFriendsJoinDelegate, GetFriendsJoinBinder> {

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
        initToolbar(new ToolbarBuilder().setTitle("加入圈子"));
        addRequest(binder.getCircleMore(this));
    }

    private void initCircle(final List<UserCircle> userCircles) {
        circleAllDvpAdapter = new CircleAllDvpAdapter(GetFriendsJoinActivity.this, userCircles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GetFriendsJoinActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        circleAllDvpAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                //加入圈子
                if (userCircles.get(position).isFree()) {
                    CircleDialogHelper.initDefaultDialog(GetFriendsJoinActivity.this, "确定加入吗？", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addRequest(binder.joinCircle(userCircles.get(position).getId(), "", GetFriendsJoinActivity.this));
                        }
                    }).show();
                } else {
                    CircleDialogHelper.initDefaultInputDialog(GetFriendsJoinActivity.this, "输入邀请码", "请输入邀请码", "确定", new OnInputClickListener() {
                        @Override
                        public void onClick(String text, View v) {
                            addRequest(binder.joinCircle(userCircles.get(position).getId(), text, GetFriendsJoinActivity.this));
                        }
                    }).show();
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_circle.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.rv_circle.setAdapter(circleAllDvpAdapter);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
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

}
