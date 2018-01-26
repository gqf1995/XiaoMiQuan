package com.xiaomiquan.mvp.fragment.circle;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleMyAdapter;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.activity.circle.CircleContentActivity;
import com.xiaomiquan.mvp.activity.circle.CreatCircleActivity;
import com.xiaomiquan.mvp.databinder.circle.CircleShowBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleShowDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andy on 2018/1/25.
 */

public class CircleShowFragment extends BaseDataBindFragment<CircleShowDelegate, CircleShowBinder> {

    ArrayList<Fragment> fragments;
    List<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    CircleMyAdapter circleMyAdapter;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initTablelayout();
        initSearch();
    }

    private void initSearch() {
        viewDelegate.viewHolder.lin_search_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initMyCircle(final List<UserCircle> userCircles) {
        UserCircle userCircle = new UserCircle();
        userCircle.setBrief("创建圈子");
        userCircles.add(0, userCircle);
        circleMyAdapter = new CircleMyAdapter(getActivity(), userCircles);
        final List<UserCircle> finalUserCircles = userCircles;
        circleMyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position != 0) {
                    Intent intent = new Intent();
                    intent.putExtra("userCircle", (Parcelable) userCircles.get(position));
                    gotoActivity(CircleContentActivity.class).setIntent(intent).startAct();
                } else {
                    gotoActivity(CreatCircleActivity.class).startAct();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                return false;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.rv_mycircle.setLayoutManager(gridLayoutManager);
        viewDelegate.viewHolder.rv_mycircle.setItemAnimator(new DefaultItemAnimator());
        viewDelegate.viewHolder.rv_mycircle.setAdapter(circleMyAdapter);
    }

    private void initTablelayout() {
        mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_circle_show));
        fragments = new ArrayList<>();
        fragments.add(new CircleDynamicFragment());
        fragments.add(new CircleFindFragment());
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);

        viewDelegate.viewHolder.vp_sliding.setAdapter(new InnerPagerAdapter(getChildFragmentManager(), fragments, mTitles.toArray(new String[mTitles.size()])));
        viewDelegate.viewHolder.vp_sliding.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewDelegate.viewHolder.tl_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewDelegate.viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewDelegate.showFragment(position);
                viewDelegate.viewHolder.vp_sliding.setCurrentItem(position, true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    protected Class<CircleShowDelegate> getDelegateClass() {
        return CircleShowDelegate.class;
    }

    @Override
    public CircleShowBinder getDataBinder(CircleShowDelegate viewDelegate) {
        return new CircleShowBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }
}
