package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.circle.ReleaseArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseDynamicActivity;
import com.xiaomiquan.mvp.delegate.HomeDelegate;
import com.xiaomiquan.mvp.dialog.ReleaseDialog;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import java.util.ArrayList;
import java.util.List;

import skin.support.widget.SkinCompatLinearLayout;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareDelegate extends BaseMyPullDelegate {

    public ViewHolder viewHolder;
    List<ReleaseDialog.ReleaseDialogEntity> entities;
    private String[] mBoomTitles=CommonUtils.getStringArray(R.array.sa_select_release_diolog);
    private int[] mIconBoomColorIds = {
            R.color.mark_color, R.color.mark_color
            , R.color.mark_color, R.color.mark_color
    };

    private int[] mIconBoomBgColorIds = {
            R.drawable.ic_combined_shape, R.drawable.ic_combined_shape
            , R.drawable.ic_combined_shape, R.drawable.ic_combined_shape
    };
    ReleaseDialog releaseDialog;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.scrollView_scroll.setTabAndPager(viewHolder.lin_tab,
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px),
                viewHolder.pull_recycleview, false);

        viewHolder.civ_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabu();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
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
        releaseDialog.showDialog(viewHolder.civ_send);
    }


    public static class ViewHolder {
        public View rootView;
        public RecyclerView ry_entrance;
        public TextView tv_live_time;
        public LinearLayout lin_live;
        public LinearLayout lin_tab;
        public RecyclerView pull_recycleview;
        public JudgeNestedScrollView scrollView_scroll;
        public AppCompatImageView civ_send;
        public SwipeRefreshLayout swipeRefreshLayout;
        public SkinCompatLinearLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ry_entrance = (RecyclerView) rootView.findViewById(R.id.ry_entrance);
            this.tv_live_time = (TextView) rootView.findViewById(R.id.tv_live_time);
            this.lin_live = (LinearLayout) rootView.findViewById(R.id.lin_live);
            this.lin_tab = (LinearLayout) rootView.findViewById(R.id.lin_tab);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.scrollView_scroll = (JudgeNestedScrollView) rootView.findViewById(R.id.scrollView_scroll);
            this.civ_send = (AppCompatImageView) rootView.findViewById(R.id.civ_send);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
        }

    }
}
