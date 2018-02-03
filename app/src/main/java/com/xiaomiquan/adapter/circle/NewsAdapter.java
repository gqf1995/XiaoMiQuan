package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserFriende;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class NewsAdapter extends CommonAdapter<UserFriende> {


    DefaultClickLinsener defaultClickLinsener;
    private TextView tv_time;
    private IconFontTextview icf_shared;
    private TextView tv_shared;
    private TextView tv_con;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private TextView tv_dislike_num;
    private IconFontTextview tv_dislike;


    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public NewsAdapter(Context context, List<UserFriende> datas) {
        super(context, R.layout.adapter_news, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserFriende s, final int position) {
        tv_time = holder.getView(R.id.tv_time);
        icf_shared = holder.getView(R.id.icf_shared);
        tv_shared = holder.getView(R.id.tv_shared);
        tv_con = holder.getView(R.id.tv_con);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        tv_dislike_num = holder.getView(R.id.tv_dislike_num);
        tv_dislike = holder.getView(R.id.tv_dislike);

//
//        if (s.getAttention()) {
//            tv_attention.setText("取消关注");
//        } else {
//            tv_attention.setText("关注");
//        }
//
//        tv_attention.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (defaultClickLinsener != null) {
//                    defaultClickLinsener.onClick(view, position, null);
//                }
//            }
//        });


    }

}