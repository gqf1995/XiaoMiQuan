package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

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

import java.util.ArrayList;
import java.util.List;

public class NewSquareDelegate extends BaseDelegate {
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_square;
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

        public CommonTabLayout tl_1;
        public LinearLayout lin_table;
        public ViewPager viewpager;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

            this.tl_1 = (CommonTabLayout) rootView.findViewById(R.id.tl_1);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        }

    }
}