package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/21.
 */

public class PraiseAdapter extends CommonAdapter<Comment> {


    private IconFontTextview tv_praise;
    private TextView tv_praise_people;

    public PraiseAdapter(Context context, List<Comment> datas) {
        super(context, R.layout.adapter_praise, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Comment comment, int position) {
        tv_praise_people = holder.getView(R.id.tv_praise_people);
        tv_praise_people.setText(comment.getNickName() + ":");
    }

}
