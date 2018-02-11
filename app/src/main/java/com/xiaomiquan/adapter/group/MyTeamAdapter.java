package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.UserSet;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class MyTeamAdapter extends CommonAdapter<String> {


    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_nick_name;
    private TextView tv_rate;
    private TextView tv_team_earnings;
    private TextView tv_team_people_num;
    private TextView tv_commit;

    DefaultClickLinsener defaultClickLinsener;

    String text;

    public void setText(String text) {
        this.text = text;
    }

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public MyTeamAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_my_team, datas);
    }



    public void setDatas(List<String> datas) {
        getDatas().clear();
        getDatas().addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_nick_name = holder.getView(R.id.tv_nick_name);
        tv_rate = holder.getView(R.id.tv_rate);
        tv_team_earnings = holder.getView(R.id.tv_team_earnings);
        tv_team_people_num = holder.getView(R.id.tv_team_people_num);
        tv_commit = holder.getView(R.id.tv_commit);
        tv_commit.setText(text);
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });

    }

}