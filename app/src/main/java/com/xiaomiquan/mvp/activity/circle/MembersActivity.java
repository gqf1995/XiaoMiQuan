package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.adapter.circle.BigVListAdapter;
import com.xiaomiquan.adapter.circle.MembersAdapter;
import com.xiaomiquan.entity.bean.GroupOwner;
import com.xiaomiquan.entity.bean.circle.CirclePerson;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.entity.bean.circle.UserFriende;
import com.xiaomiquan.mvp.databinder.circle.LiveBinder;
import com.xiaomiquan.mvp.databinder.circle.MembersBinder;
import com.xiaomiquan.mvp.delegate.circle.LiveDelegate;
import com.xiaomiquan.mvp.delegate.circle.MembersDelegate;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class MembersActivity extends BaseDataBindActivity<MembersDelegate, MembersBinder> {

    MembersAdapter membersAdapter;

    @Override
    protected Class<MembersDelegate> getDelegateClass() {
        return MembersDelegate.class;
    }

    @Override
    public MembersBinder getDataBinder(MembersDelegate viewDelegate) {
        return new MembersBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("圈子成员"));
        addRequest(binder.CircleMembers(CircleContentActivity.groupId, MembersActivity.this));

    }

    public void initCirclePerson(final List<CirclePerson> circlePeoples) {
        membersAdapter = new MembersAdapter(MembersActivity.this, circlePeoples);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MembersActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        membersAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                //加入圈子
                if (circlePeoples.get(position).getBanned().equals("false")) {
                    CircleDialogHelper.initDefaultDialog(MembersActivity.this, "确定禁言吗？", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addRequest(binder.MembersBanned(circlePeoples.get(position).getId(), MembersActivity.this));
                        }
                    }).show();
                } else {
                    CircleDialogHelper.initDefaultDialog(MembersActivity.this, "解除禁言", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addRequest(binder.MembersUnBanned(circlePeoples.get(position).getId(), MembersActivity.this));
                        }
                    }).show();
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_person.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.rv_person.setAdapter(membersAdapter);
    }

    GroupOwner groupOwner;

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                String groupOwner1 = GsonUtil.getInstance().getValue(data, "groupOwner");
                if (groupOwner1 != null) {
                    groupOwner = GsonUtil.getInstance().toObj(groupOwner1, GroupOwner.class);
                    viewDelegate.viewHolder.tv_name.setText(groupOwner.getNickName());
                    GlideUtils.loadImage(groupOwner.getAvatar(), viewDelegate.viewHolder.cv_head);
                }
                List<CirclePerson> datas = GsonUtil.getInstance().toList(data, "list", CirclePerson.class);
                initCirclePerson(datas);
                break;
            case 0x124:
                addRequest(binder.CircleMembers(CircleContentActivity.groupId, MembersActivity.this));

                break;
            case 0x125:
                addRequest(binder.CircleMembers(CircleContentActivity.groupId, MembersActivity.this));

                break;

        }
    }

    UserCircle userCircle;

    private void getIntentData() {
        Intent intent = getIntent();
        userCircle = (UserCircle) intent.getParcelableExtra("userCircle");
    }

}
