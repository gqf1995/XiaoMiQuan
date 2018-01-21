package com.xiaomiquan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.Commend;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/21.
 */

public class CommendAdapter extends CommonAdapter<Commend> {

    public TextView name;
    public TextView con;

    public CommendAdapter(Context context, List<Commend> datas) {
        super(context, R.layout.adapter_comend, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Commend commend, int position) {
        name=holder.getView(R.id.name);
        con=holder.getView(R.id.con);
        name.setText(commend.getNickName()+":");
        con.setText(commend.getContent());
    }


}
