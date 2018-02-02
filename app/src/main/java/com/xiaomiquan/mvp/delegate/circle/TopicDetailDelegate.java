package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andy on 2018/1/19.
 */

public class TopicDetailDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_details;
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView cv_head;
        public TextView tv_name;
        public TextView tv_master;
        public TextView tv_time;
        public TextView tv_con;
        public IconFontTextview tv_praise;
        public TextView tv_praise_num;
        public IconFontTextview tv_comment;
        public TextView tv_comment_num;
        public IconFontTextview icf_shared;
        public TextView tv_shared;
        public RecyclerView rv_comment;
        public SwipeRefreshLayout swipeRefreshLayout;
        public MaterialEditText et_input2;
        public TextView tv_commit;
        public LinearLayout lin_comment;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_master = (TextView) rootView.findViewById(R.id.tv_master);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_con = (TextView) rootView.findViewById(R.id.tv_con);
            this.tv_praise = (IconFontTextview) rootView.findViewById(R.id.tv_praise);
            this.tv_praise_num = (TextView) rootView.findViewById(R.id.tv_praise_num);
            this.tv_comment = (IconFontTextview) rootView.findViewById(R.id.tv_comment);
            this.tv_comment_num = (TextView) rootView.findViewById(R.id.tv_comment_num);
            this.icf_shared = (IconFontTextview) rootView.findViewById(R.id.icf_shared);
            this.tv_shared = (TextView) rootView.findViewById(R.id.tv_shared);
            this.rv_comment = (RecyclerView) rootView.findViewById(R.id.rv_comment);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.et_input2 = (MaterialEditText) rootView.findViewById(R.id.et_input2);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.lin_comment = (LinearLayout) rootView.findViewById(R.id.lin_comment);
        }

    }
}
