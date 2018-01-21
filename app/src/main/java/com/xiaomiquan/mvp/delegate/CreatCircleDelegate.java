package com.xiaomiquan.mvp.delegate;

import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.tablayout.CommonTabLayout;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.mvp.activity.user.InputSetActivity;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreatCircleDelegate extends BaseDelegate {

    private String circle_creat_name = "";
    private String circle_creat_brief = "";

    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        init();

    }

    public void init() {
        viewHolder.circle_crate_commit.setOnClickListener(onClickListener);
        initCrate();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.circle_crate_commit:

                    break;
            }
        }
    };

    private void saveInput() {
        circle_creat_name  = viewHolder.circle_creat_brief.getText().toString();
        circle_creat_brief = viewHolder.circle_creat_brief.getText().toString();
    }

    public void initCrate() {
        saveInput();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_circle;
    }


    public static class ViewHolder {
        public View rootView;
        public LinearLayout layout_title_bar;
        public CommonTabLayout tl_2;
        public MaterialEditText circle_creat_name;
        public MaterialEditText circle_creat_brief;
        public TextView circle_crate_commit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.layout_title_bar = (LinearLayout) rootView.findViewById(R.id.layout_title_bar);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.circle_creat_name = (MaterialEditText) rootView.findViewById(R.id.circle_creat_name);
            this.circle_creat_brief = (MaterialEditText) rootView.findViewById(R.id.circle_creat_brief);
            this.circle_crate_commit = (TextView) rootView.findViewById(R.id.circle_crate_commit);

        }

    }
}