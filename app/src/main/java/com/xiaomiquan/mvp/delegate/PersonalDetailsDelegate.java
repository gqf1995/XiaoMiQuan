package com.xiaomiquan.mvp.delegate;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserPageDetail;
import com.xiaomiquan.entity.bean.chat.ChatLiveItem;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import de.hdodenhof.circleimageview.CircleImageView;
import skin.support.widget.SkinCompatToolbar;

public class PersonalDetailsDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    int height;
    ChatLiveItem chatLiveItem;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    public void initUserDetail(UserPageDetail s) {
        GlideUtils.loadImage(s.getUser().getAvatar(), viewHolder.ic_pic);
        viewHolder.tv_name.setText(s.getUser().getNickName());
        viewHolder.tv_fans_num.setText(" " + s.getFansCount());
        viewHolder.tv_focuse_num.setText(" " + s.getAttentionCount());
        viewHolder.tv_introduction.setText(s.getUser().getBrief());
    }

    public void initChat(ChatLiveItem chatLiveItem) {
        this.chatLiveItem = chatLiveItem;
        if (chatLiveItem == null) {
            viewHolder.lin_chat.setVisibility(View.GONE);
        } else {
            viewHolder.lin_chat.setVisibility(View.VISIBLE);
            viewHolder.tv_chat_status.setText(chatLiveItem.getStatusStr());
            viewHolder.tv_chat_people_num.setText(CommonUtils.getString(R.string.str_people_talking_about, chatLiveItem.getOnlineTotal() + ""));
            viewHolder.tv_chat_content.setText(chatLiveItem.getTitle());
        }
    }

    public void initTool() {
        height = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_150px);
        viewHolder.layout_title_bar.getBackground().mutate().setAlpha(0);
        viewHolder.layout_title_bar.setVisibility(View.VISIBLE);
        viewHolder.swipeRefreshLayout.setProgressViewEndTarget(false, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_250px));
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_table, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_110px), viewHolder.viewpager, false);
        viewHolder.nestedScrollView.setOnScrollChangeListener(new JudgeNestedScrollView.OnScrollChangeListener() {
            // 将透明度声明成局部变量用于判断
            int alpha = 0;
            int count = 0;
            float scale = 0;

            @Override
            public void onScrollChangeListener(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                viewHolder.layout_title_bar.setVisibility(View.VISIBLE);
                if (scrollY <= height / 2) {
                    setToolColor(R.color.colorPrimary, false);
                } else {
                    setToolColor(R.color.colorPrimary, true);
                }
                if (scrollY <= height) {
                    scale = (float) scrollY / height;
                    alpha = (int) (255 * scale);
                    // 随着滑动距离改变透明度
                    // Log.e("al=","="+alpha);
                    viewHolder.layout_title_bar.getBackground().mutate().setAlpha(alpha);

                } else {
                    if (alpha < 255) {
                        // 防止频繁重复设置相同的值影响性能
                        alpha = 255;
                        viewHolder.layout_title_bar.getBackground().mutate().setAlpha(alpha);
                    }
                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_details;
    }

    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_pic;
        public TextView tv_name;
        public TextView tv_focuse_num;
        public TextView tv_fans_num;
        public TextView tv_introduction;
        public TextView tv_chat_status;
        public TextView tv_chat_people_num;
        public TextView tv_chat_content;
        public LinearLayout lin_chat;
        public LinearLayout lin_top;
        public CommonTabLayout tl_2;
        public LinearLayout lin_table;
        public ViewPager viewpager;
        public JudgeNestedScrollView nestedScrollView;
        public SwipeRefreshLayout swipeRefreshLayout;
        public View v_status;
        public IconFontTextview toolbar_back;
        public FrameLayout view_back_point;
        public TextView toolbar_back_txt;
        public LinearLayout toolbar_lin_back;
        public FrameLayout fl_content;
        public IconFontTextview toolbar_subtitle;
        public FrameLayout view_subtitle_point;
        public IconFontTextview toolbar_img2;
        public FrameLayout view_img2_point;
        public IconFontTextview toolbar_img1;
        public FrameLayout view_img1_point;
        public IconFontTextview toolbar_img;
        public FrameLayout view_img_point;
        public TextView toolbar_title;
        public SkinCompatToolbar toolbar;
        public LinearLayout layout_title_bar;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_focuse_num = (TextView) rootView.findViewById(R.id.tv_focuse_num);
            this.tv_fans_num = (TextView) rootView.findViewById(R.id.tv_fans_num);
            this.tv_introduction = (TextView) rootView.findViewById(R.id.tv_introduction);
            this.tv_chat_status = (TextView) rootView.findViewById(R.id.tv_chat_status);
            this.tv_chat_people_num = (TextView) rootView.findViewById(R.id.tv_chat_people_num);
            this.tv_chat_content = (TextView) rootView.findViewById(R.id.tv_chat_content);
            this.lin_chat = (LinearLayout) rootView.findViewById(R.id.lin_chat);
            this.lin_top = (LinearLayout) rootView.findViewById(R.id.lin_top);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.v_status = (View) rootView.findViewById(R.id.v_status);
            this.toolbar_back = (IconFontTextview) rootView.findViewById(R.id.toolbar_back);
            this.view_back_point = (FrameLayout) rootView.findViewById(R.id.view_back_point);
            this.toolbar_back_txt = (TextView) rootView.findViewById(R.id.toolbar_back_txt);
            this.toolbar_lin_back = (LinearLayout) rootView.findViewById(R.id.toolbar_lin_back);
            this.fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
            this.toolbar_subtitle = (IconFontTextview) rootView.findViewById(R.id.toolbar_subtitle);
            this.view_subtitle_point = (FrameLayout) rootView.findViewById(R.id.view_subtitle_point);
            this.toolbar_img2 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img2);
            this.view_img2_point = (FrameLayout) rootView.findViewById(R.id.view_img2_point);
            this.toolbar_img1 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img1);
            this.view_img1_point = (FrameLayout) rootView.findViewById(R.id.view_img1_point);
            this.toolbar_img = (IconFontTextview) rootView.findViewById(R.id.toolbar_img);
            this.view_img_point = (FrameLayout) rootView.findViewById(R.id.view_img_point);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (SkinCompatToolbar) rootView.findViewById(R.id.toolbar);
            this.layout_title_bar = (LinearLayout) rootView.findViewById(R.id.layout_title_bar);
        }

    }
}