package com.xiaomiquan.mvp.delegate;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.chat.ChatLiveListActivity;
import com.xiaomiquan.mvp.dialog.ReleaseDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    List<ReleaseDialog.ReleaseDialogEntity> entities;
    private String[] mBoomTitles = {"买BTC", "卖BTC"
            , "买链克", "卖链克"
    };
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
        viewHolder.chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.rootView.getContext(), ChatLiveListActivity.class);
                ((Activity) viewHolder.rootView.getContext()).startActivity(intent);
            }
        });

        viewHolder.fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabu();
            }
        });
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
                    if (position == 1) {

                    } else if (position == 2) {

                    } else if (position == 3) {

                    } else if (position == 4) {

                    }
                }
            });
        }
        releaseDialog.showDialog();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    public static class ViewHolder {
        public View rootView;
        public Button chart;
        public Button fabu;
        public FrameLayout fl_web;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.chart = (Button) rootView.findViewById(R.id.chart);
            this.fabu = (Button) rootView.findViewById(R.id.fabu);
            this.fl_web = (FrameLayout) rootView.findViewById(R.id.fl_web);
        }

    }
}