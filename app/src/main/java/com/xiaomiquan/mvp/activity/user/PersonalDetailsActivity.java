package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.UserPageDetail;
import com.xiaomiquan.entity.bean.chat.ChatLiveItem;
import com.xiaomiquan.entity.bean.chat.CheckScore;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.chat.GroupChatActivity;
import com.xiaomiquan.mvp.databinder.PersonalDetailsBinder;
import com.xiaomiquan.mvp.delegate.PersonalDetailsDelegate;
import com.xiaomiquan.mvp.fragment.UserCenterListFragment;
import com.xiaomiquan.widget.CircleDialogHelper;

import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsActivity extends BaseDataBindActivity<PersonalDetailsDelegate, PersonalDetailsBinder> {

    UserLogin userLogin;
    List<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    ChatLiveItem chatLiveItem;

    @Override
    protected Class<PersonalDetailsDelegate> getDelegateClass() {
        return PersonalDetailsDelegate.class;
    }

    @Override
    public PersonalDetailsBinder getDataBinder(PersonalDetailsDelegate viewDelegate) {
        return new PersonalDetailsBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.personCenter(id, PersonalDetailsActivity.this));
            }
        });
        addRequest(binder.personCenter(id, this));
    }


    private void initFragemnt() {
        if (!ListUtils.isEmpty(getSupportFragmentManager().getFragments())) {
            String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_user_center);
            fragments = new ArrayList<>();
            for (int i = 0; i < stringArray.length; i++) {
                fragments.add(UserCenterListFragment.newInstance(id, i + ""));//文章1  观点2 操作3
            }
            for (int i = 0; i < stringArray.length; i++) {
                mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
            }
            viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
            InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), (ArrayList) fragments, stringArray);
            viewDelegate.viewHolder.tl_2.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.viewpager);
        } else {
            fragments = getSupportFragmentManager().getFragments();
        }
    }

    public static void startAct(Activity activity,
                                String id, int code) {
        Intent intent = new Intent(activity, PersonalDetailsActivity.class);
        intent.putExtra("id", id);
        activity.startActivityForResult(intent, code);
    }

    private String id;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        userLogin = SingSettingDBUtil.getUserLogin();
        if (userLogin != null) {
            if (id.equals(userLogin.getId() + "")) {
                initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_my_home_page)).setSubTitle(" "));
                GlideUtils.loadImage(userLogin.getAvatar(), viewDelegate.viewHolder.ic_pic);
            } else {
                initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_other_home_page)).setSubTitle(" "));
            }
        } else {
            initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_my_home_page)).setSubTitle(" "));
            GlideUtils.loadImage(userLogin.getAvatar(), viewDelegate.viewHolder.ic_pic);
        }
        //初始化滑动渐变前
        viewDelegate.getLayoutTitleBar().setVisibility(View.GONE);
        initFragemnt();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //初始化滑动渐变
            viewDelegate.setToolColor(R.color.colorPrimary, false);
            viewDelegate.initTool();
            if (userLogin != null) {
                if (id.equals(userLogin.getId() + "")) {
                    viewDelegate.getmToolbarSubTitle().setText(CommonUtils.getString(R.string.str_edit));
                }
            }

        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (SingSettingDBUtil.isLogin(this)) {
            if (id.equals(userLogin.getId() + "")) {
                //编辑
                ChangeUserInfoActivity.startAct(this, 0x123);
            } else {
                //关注
                if (!userPageDetail.isIsAttention()) {
                    userPageDetail.setIsAttention(false);
                    addRequest(binder.attentiondelete(id, this));
                } else {
                    userPageDetail.setIsAttention(true);
                    addRequest(binder.attention(id, this));
                }
                viewDelegate.getmToolbarSubTitle().setText(userPageDetail.isIsAttention() ? CommonUtils.getString(R.string.str_focuse) : CommonUtils.getString(R.string.str_cancel_fucose));
            }
        }
    }

    UserPageDetail userPageDetail;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //刷新

        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                userPageDetail = GsonUtil.getInstance().toObj(data, UserPageDetail.class);
                viewDelegate.initUserDetail(userPageDetail);
                if (userLogin != null) {
                    if (!id.equals(userLogin.getId() + "")) {
                        viewDelegate.getmToolbarSubTitle().setText(userPageDetail.isIsAttention() ? CommonUtils.getString(R.string.str_focuse) : CommonUtils.getString(R.string.str_cancel_fucose));
                    }
                }
                addRequest(binder.getChatroom(id, this));
                break;
            case 0x124:
                //检测用户是否能进聊天
                CheckScore checkScore = GsonUtil.getInstance().toObj(data, CheckScore.class);
                if (!checkScore.isJoinGroup()) {
                    CircleDialogHelper.initDefaultToastDialog(this, CommonUtils.getString(R.string.str_toast_cannot_join_group), null)
                            .show();
                    return;
                }
                GroupChatActivity.startAct(this,
                        chatLiveItem.getGroupId(),
                        chatLiveItem.getGroupName(),
                        chatLiveItem.getAvatar(),
                        chatLiveItem.getTitle(),
                        chatLiveItem.getOnlineTotal() + "",
                        checkScore.isLeader(),
                        checkScore.isCanSpeak(),
                        0x123
                );
                break;
            case 0x125:
                //用户聊天室
                chatLiveItem = GsonUtil.getInstance().toObj(data, ChatLiveItem.class);
                viewDelegate.initChat(chatLiveItem);
                viewDelegate.viewHolder.lin_chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addRequest(binder.checkScore(chatLiveItem.getGroupId(), PersonalDetailsActivity.this));
                    }
                });
                break;
        }
    }

    //    private void initSubtitle() {
    //        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewDelegate.getmToolbarSubTitle().getLayoutParams();
    //        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    //        layoutParams.rightMargin = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_20px);
    //        viewDelegate.getmToolbarSubTitle().setLayoutParams(layoutParams);
    //        viewDelegate.getmToolbarSubTitle().setPadding(
    //                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px),
    //                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px),
    //                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px),
    //                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px)
    //        );
    //    }

}
