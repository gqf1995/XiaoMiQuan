package com.xiaomiquan.mvp.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class GetFriendsJoinDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_circle;
    }


    public static class ViewHolder {
        public View rootView;
        public EditText et_search;
        public TextView tv_commit;
        public RecyclerView rv_circle;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_search = (EditText) rootView.findViewById(R.id.et_search);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.rv_circle = (RecyclerView) rootView.findViewById(R.id.rv_circle);
        }

    }
}