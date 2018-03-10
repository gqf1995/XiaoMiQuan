package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.circle.NewSquareBinder;
import com.xiaomiquan.mvp.delegate.circle.NewSquareDelegate;
import com.xiaomiquan.mvp.fragment.circle.NewSquareItemFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fivefivelike.mybaselibrary.utils.glide.GlideUtils.BASE_URL;

public class NewSquareActivity extends BaseDataBindFragment<NewSquareDelegate, NewSquareBinder> {

    ArrayList<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    InnerPagerAdapter innerPagerAdapter;

    public interface Linsener {
        void openDrawerLayout();
    }

    Linsener linsener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        linsener = (Linsener) activity;
    }

    @Override
    protected Class<NewSquareDelegate> getDelegateClass() {
        return NewSquareDelegate.class;
    }

    @Override
    public NewSquareBinder getDataBinder(NewSquareDelegate viewDelegate) {
        return new NewSquareBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initTool();
        initView();
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
        viewDelegate.viewHolder.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}
