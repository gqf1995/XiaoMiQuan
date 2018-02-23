package com.xiaomiquan.mvp.delegate;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.utils.AndroidUtil;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.BannerEntity;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.widget.StickyScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import skin.support.widget.SkinCompatLinearLayout;

public class AllGroupDelegate extends BaseFragentPullDelegate {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.lin_my_group.setVisibility(View.GONE);
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.view_tab,
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_1px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_79px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_100px),
                viewHolder.pull_recycleview, true);
        initDefaultBanner();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_group;
    }

    private void initDefaultBanner() {
        Integer[] images = {R.drawable.banner1, R.drawable.banner2,
                R.drawable.banner3, R.drawable.banner4};
        viewHolder.banner.setImageLoader(new com.xiaomiquan.widget.GlideBannerImageLoader());
        viewHolder.banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        viewHolder.banner.setImages(Arrays.asList(images));
        viewHolder.banner.setBannerAnimation(Transformer.Default);
        ViewGroup.LayoutParams layoutParams = viewHolder.banner.getLayoutParams();
        layoutParams.height = AndroidUtil.getScreenW(GlobleContext.getInstance().getApplicationContext(), false) * 240 / 750;
        viewHolder.banner.setLayoutParams(layoutParams);
        viewHolder.banner.start();
    }

    List<BannerEntity> bannerDatas;

    public void setBanner(List<BannerEntity> datas) {
        if (!ListUtils.isEmpty(datas)) {
            return;
        }
        bannerDatas = datas;
        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            imgs.add(datas.get(i).getPictureUrl());
        }
        viewHolder.banner.setImages(imgs);
        viewHolder.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (TextUtils.isEmpty(bannerDatas.get(position).getTurnUrl())) {
                    return;
                }
                if ("cjzd".equals(bannerDatas.get(position).getTurnUrl())) {
                    //跳转到大赛页面
                    ResultDialogEntity resultDialogEntity = new ResultDialogEntity();
                    resultDialogEntity.setCode("1");
                    EventBus.getDefault().post(resultDialogEntity);
                } else {
                    WebActivityActivity.startAct((FragmentActivity) viewHolder.rootView.getContext(), bannerDatas.get(position).getTurnUrl());
                }
            }
        });
    }

    public void initRank(OnTabSelectListener onTabSelectListener) {
        String[] string = CommonUtils.getStringArray(R.array.sa_select_group_rank);
        for (int i = 0; i < string.length; i++) {
            mTabEntities.add(new TabEntity(string[i], 0, 0));
        }
        viewHolder.tl_2.setVisibility(View.VISIBLE);
        viewHolder.tl_2.setTabData(mTabEntities);
        viewHolder.tl_2.setOnTabSelectListener(onTabSelectListener);
    }


    public static class ViewHolder {
        public View rootView;
        public LinearLayout lin_my_group;
        public RecyclerView rv_my_group;
        public Banner banner;
        public TextView tv_more_team;
        public RecyclerView rcv_hot_team;
        public CommonTabLayout tl_2;
        public RecyclerView rcv_hot_group;
        public View view_tab;
        public RecyclerView pull_recycleview;
        public StickyScrollView nestedScrollView;
        public SwipeRefreshLayout swipeRefreshLayout;
        public FrameLayout fl_rcv;
        public SkinCompatLinearLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lin_my_group = (LinearLayout) rootView.findViewById(R.id.lin_my_group);
            this.rv_my_group = (RecyclerView) rootView.findViewById(R.id.rv_my_group);
            this.banner = (Banner) rootView.findViewById(R.id.banner);
            this.tv_more_team = (TextView) rootView.findViewById(R.id.tv_more_team);
            this.rcv_hot_team = (RecyclerView) rootView.findViewById(R.id.rcv_hot_team);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.rcv_hot_group = (RecyclerView) rootView.findViewById(R.id.rcv_hot_group);
            this.view_tab = (View) rootView.findViewById(R.id.view_tab);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.nestedScrollView = (StickyScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_rcv = (FrameLayout) rootView.findViewById(R.id.fl_rcv);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
        }

    }
}