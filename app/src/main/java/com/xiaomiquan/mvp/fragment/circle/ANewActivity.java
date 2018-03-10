package com.xiaomiquan.mvp.fragment.circle;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CommentDetailAdapter;
import com.xiaomiquan.adapter.circle.TopicAdapter;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.NewSquareActivity;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.circle.ANewBinder;
import com.xiaomiquan.mvp.delegate.circle.ANewDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fivefivelike.mybaselibrary.utils.glide.GlideUtils.BASE_URL;

public class ANewActivity extends BaseDataBindFragment<ANewDelegate, ANewBinder> {

    ArrayList<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    InnerPagerAdapter innerPagerAdapter;

    public interface Linsener {
        void openDrawerLayout();
    }

    NewSquareActivity.Linsener linsener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        linsener = (NewSquareActivity.Linsener) activity;
    }


    @Override
    protected Class<ANewDelegate> getDelegateClass() {
        return ANewDelegate.class;
    }

    @Override
    public ANewBinder getDataBinder(ANewDelegate viewDelegate) {
        return new ANewBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initTool();
        initView();
        initTop();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        viewDelegate.fabu();
    }

    public CircleImageView ic_pic;
    public FrameLayout fl_pic;

    private void initTool() {
        initToolbar(new ToolbarBuilder().setShowBack(false)
                .setTitle(CommonUtils.getString(R.string.str_title_square))
                .setSubTitle(CommonUtils.getString(R.string.str_release)));
        View rootView = this.getLayoutInflater().inflate(R.layout.layout_home_top, null);
        viewDelegate.getFl_content().addView(rootView);
        this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
        this.fl_pic = (FrameLayout) rootView.findViewById(R.id.fl_pic);
        if (SingSettingDBUtil.getUserLogin() != null) {
            GlideUtils.loadImage(SingSettingDBUtil.getUserLogin().getAvatar(), ic_pic);
        } else {
            GlideUtils.loadImage(BASE_URL, ic_pic);
        }
        ic_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开抽屉
                linsener.openDrawerLayout();
            }
        });
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {

        }
    }

    TopicAdapter topicAdapter;

    private void initTop() {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            str.add(i + "1");
        }
        if (topicAdapter == null) {
            topicAdapter = new TopicAdapter(getActivity(), str);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewDelegate.viewHolder.recycleview.setLayoutManager(linearLayoutManager);
            viewDelegate.viewHolder.recycleview.setAdapter(topicAdapter);
        } else {

        }

    }

    private void initView() {
        if (!ListUtils.isEmpty(getChildFragmentManager().getFragments())) {
            String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_new_square);
            fragments = new ArrayList<>();
            for (int i = 0; i < stringArray.length; i++) {
                fragments.add(NewSquareItemFragment.newInstance(i));
                mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
            }
            viewDelegate.viewHolder.tl_1.setTabData(mTabEntities);
            innerPagerAdapter = new InnerPagerAdapter(getChildFragmentManager(), fragments, stringArray);
            viewDelegate.viewHolder.tl_1.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.viewpager);
        }
    }

}
