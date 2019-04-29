package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;
import skin.support.widget.SkinCompatImageView;

public class TranspondTopicDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transpond_topic;
    }


    public static class ViewHolder {
        public View rootView;
        public MaterialEditText et_con;
        public CircleImageView cv_shard_head;
        public TextView tv_shared_name;
        public TextView icf_shared_attention;
        public TextView tv_shared_brief;
        public SkinCompatImageView iv_shared;
        public LinearLayout lin_shared_topic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_con = (MaterialEditText) rootView.findViewById(R.id.et_con);
            this.cv_shard_head = (CircleImageView) rootView.findViewById(R.id.cv_shard_head);
            this.tv_shared_name = (TextView) rootView.findViewById(R.id.tv_shared_name);
            this.icf_shared_attention = (TextView) rootView.findViewById(R.id.icf_shared_attention);
            this.tv_shared_brief = (TextView) rootView.findViewById(R.id.tv_shared_brief);
            this.iv_shared = (SkinCompatImageView) rootView.findViewById(R.id.iv_shared);
            this.lin_shared_topic = (LinearLayout) rootView.findViewById(R.id.lin_shared_topic);
        }

    }
}