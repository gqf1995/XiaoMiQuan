package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.mvp.activity.group.TeamDetailActivity;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class MyTeamAdapter extends CommonAdapter<HotTeam> {


    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_nick_name;
    private TextView tv_rate;
    private TextView tv_team_earnings;
    private TextView tv_team_people_num;
    private TextView tv_commit;

    DefaultClickLinsener defaultClickLinsener;

    String text;
    private LinearLayout lin_root;

    public void setText(String text) {
        this.text = text;
    }

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public MyTeamAdapter(Context context, List<HotTeam> datas) {
        super(context, R.layout.adapter_my_team, datas);
    }


    public void setDatas(List<HotTeam> datas) {
        getDatas().clear();
        getDatas().addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, HotTeam s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_nick_name = holder.getView(R.id.tv_nick_name);
        tv_rate = holder.getView(R.id.tv_rate);
        tv_team_earnings = holder.getView(R.id.tv_team_earnings);
        tv_team_people_num = holder.getView(R.id.tv_team_people_num);
        lin_root = holder.getView(R.id.lin_root);

        lin_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamDetailActivity.startAct((FragmentActivity) mContext, getDatas().get(position).getId() + "");
            }
        });

        tv_name.setText(s.getTeamName());
        tv_nick_name.setText(s.getNickName());

        if (!TextUtils.isEmpty(s.getAverage())) {
            BigUIUtil.getinstance().rateTextView(Double.parseDouble(s.getAverage()), tv_rate);
        }

        GlideUtils.loadImage(s.getAvatar(), ic_pic);
        tv_team_earnings.setText(s.getBonusPoolStr());
        tv_team_people_num.setText(s.getTeamCount() + CommonUtils.getString(R.string.str_people));

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