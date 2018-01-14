package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    int height;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    public void initUserMsg(UserLogin userLogin) {
        if (userLogin == null) {
            viewHolder.tv_nick_name.setText(CommonUtils.getString(R.string.str_nologin));
            viewHolder.tv_subtitle.setVisibility(View.GONE);
            return;
        }
        viewHolder.tv_nick_name.setText(userLogin.getNickName());
        viewHolder.tv_subtitle.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(userLogin.getEmail())){
            viewHolder.tv_subtitle.setText(userLogin.getEmail());
        }else  if(!TextUtils.isEmpty(userLogin.getPhone())){
            viewHolder.tv_subtitle.setText(userLogin.getPhone());
        }
    }

    private void init() {
        height = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_210px);
        viewHolder.fl_toolbar.getBackground().mutate().setAlpha(0);
        viewHolder.toolbar_title.setAlpha(0);
        viewHolder.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            // 将透明度声明成局部变量用于判断
            int alpha = 0;
            int count = 0;
            float scale = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY <= height) {
                    scale = (float) scrollY / height;
                    alpha = (int) (255 * scale);
                    // 随着滑动距离改变透明度
                    // Log.e("al=","="+alpha);
                    viewHolder.fl_toolbar.getBackground().mutate().setAlpha(alpha);
                    viewHolder.toolbar_title.setAlpha(scale);

                } else {
                    if (alpha < 255) {
                        // 防止频繁重复设置相同的值影响性能
                        alpha = 255;
                        viewHolder.fl_toolbar.getBackground().mutate().setAlpha(alpha);
                        viewHolder.toolbar_title.setAlpha(scale);
                    }
                }
            }
        });
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_piv;
        public TextView tv_nick_name;
        public TextView tv_subtitle;
        public SwitchButton checkbox_red_sticker;
        public LinearLayout lin_set1;
        public SwitchButton checkbox_night_model;
        public LinearLayout lin_set2;
        public TextView tv_select_price_model;
        public LinearLayout lin_set3;
        public LinearLayout lin_set4;
        public LinearLayout lin_set5;
        public TextView tv_select_language;
        public LinearLayout lin_set6;
        public LinearLayout lin_set7;
        public LinearLayout lin_set8;
        public LinearLayout lin_set9;
        public LinearLayout lin_user;
        public NestedScrollView nestedScrollView;
        public TextView toolbar_title;
        public FrameLayout fl_toolbar;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_piv = (CircleImageView) rootView.findViewById(R.id.ic_piv);
            this.tv_nick_name = (TextView) rootView.findViewById(R.id.tv_nick_name);
            this.tv_subtitle = (TextView) rootView.findViewById(R.id.tv_subtitle);
            this.checkbox_red_sticker = (SwitchButton) rootView.findViewById(R.id.checkbox_red_sticker);
            this.lin_set1 = (LinearLayout) rootView.findViewById(R.id.lin_set1);
            this.checkbox_night_model = (SwitchButton) rootView.findViewById(R.id.checkbox_night_model);
            this.lin_set2 = (LinearLayout) rootView.findViewById(R.id.lin_set2);
            this.tv_select_price_model = (TextView) rootView.findViewById(R.id.tv_select_price_model);
            this.lin_set3 = (LinearLayout) rootView.findViewById(R.id.lin_set3);
            this.lin_set4 = (LinearLayout) rootView.findViewById(R.id.lin_set4);
            this.lin_set5 = (LinearLayout) rootView.findViewById(R.id.lin_set5);
            this.tv_select_language = (TextView) rootView.findViewById(R.id.tv_select_language);
            this.lin_set6 = (LinearLayout) rootView.findViewById(R.id.lin_set6);
            this.lin_set7 = (LinearLayout) rootView.findViewById(R.id.lin_set7);
            this.lin_set8 = (LinearLayout) rootView.findViewById(R.id.lin_set8);
            this.lin_set9 = (LinearLayout) rootView.findViewById(R.id.lin_set9);
            this.lin_user = (LinearLayout) rootView.findViewById(R.id.lin_user);
            this.nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.fl_toolbar = (FrameLayout) rootView.findViewById(R.id.fl_toolbar);
        }

    }
}