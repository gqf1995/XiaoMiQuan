package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.circle.ReleaseArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseDynamicActivity;
import com.xiaomiquan.mvp.dialog.ReleaseDialog;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import java.util.ArrayList;
import java.util.List;

import skin.support.widget.SkinCompatFrameLayout;

public class ANewDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    List<ReleaseDialog.ReleaseDialogEntity> entities;
    private String[] mBoomTitles = CommonUtils.getStringArray(R.array.sa_select_release_diolog);
    private int[] mIconBoomBgColorIds = {
            R.color.transparent, R.color.transparent
            , R.color.transparent, R.color.transparent
    };

    private int[] mIconBoomColorIds = {
            R.drawable.ic_point, R.drawable.ic_article
            , R.drawable.ic_share, R.drawable.ic_forecast
    };
    ReleaseDialog releaseDialog;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.fl_tab, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_110px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px), viewHolder.viewpager, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anew;
    }

    public void fabu() {
        if (releaseDialog == null) {
            entities = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                entities.add(new ReleaseDialog.ReleaseDialogEntity(mBoomTitles[i], mIconBoomColorIds[i], mIconBoomBgColorIds[i]));
            }
            releaseDialog = new ReleaseDialog(this.getActivity(), R.style.baseNoBgDialog);
            releaseDialog.setDatas(entities);
            releaseDialog.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (SingSettingDBUtil.getUserLogin() != null) {
                        switch (position) {
                            case 1:
                                ReleaseDynamicActivity.startAct(getActivity(), "2", "1");
                                releaseDialog.dismiss();
                                break;
                            case 2:
                                ReleaseArticleActivity.startAct(getActivity(), "1", "1", "0");
                                releaseDialog.dismiss();
                                break;
                            case 3:
                                ReleaseArticleActivity.startAct(getActivity(), "1", "1", "1");
                                releaseDialog.dismiss();
                                break;
                            case 4:
                                ToastUtil.show("正在开发.....");
                                break;
                        }
                    } else {
                        ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                    }
                }
            });
        }
        releaseDialog.showDialog(viewHolder.tl_1);
    }

    public static class ViewHolder {
        public View rootView;
        public RecyclerView recycleview;
        public CommonTabLayout tl_1;
        public SkinCompatFrameLayout fl_tab;
        public ViewPager viewpager;
        public JudgeNestedScrollView nestedScrollView;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.recycleview = (RecyclerView) rootView.findViewById(R.id.recycleview);
            this.tl_1 = (CommonTabLayout) rootView.findViewById(R.id.tl_1);
            this.fl_tab = (SkinCompatFrameLayout) rootView.findViewById(R.id.fl_tab);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}