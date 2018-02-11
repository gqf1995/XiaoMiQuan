package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class HotTeamAdapter extends CommonAdapter<HotTeam> {


    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_nick_name;
    private TextView tv_rate;
    private TextView tv_content;
    private TextView tv_team_people_num;
    private TextView tv_team_earnings;
    private TextView tv_apply_to_join;

    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public HotTeamAdapter(Context context, List<HotTeam> datas) {
        super(context, R.layout.adapter_hot_team, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HotTeam s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_nick_name = holder.getView(R.id.tv_nick_name);
        tv_rate = holder.getView(R.id.tv_rate);
        tv_content = holder.getView(R.id.tv_content);
        tv_team_people_num = holder.getView(R.id.tv_team_people_num);
        tv_team_earnings = holder.getView(R.id.tv_team_earnings);
        tv_apply_to_join = holder.getView(R.id.tv_apply_to_join);

        GlideUtils.loadImage(s.getAvatar(), ic_pic);
        tv_name.setText(s.getTeamName());
        tv_nick_name.setText(s.getNickName());
        BigUIUtil.getinstance().rateTextView(Double.parseDouble(s.getTotalProfit()), tv_rate);
        tv_content.setText(s.getBrief());
        tv_team_people_num.setText(s.getTeamCount() + CommonUtils.getString(R.string.str_people));
        tv_team_earnings.setText(s.getBonusPoolStr());


        tv_apply_to_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });
    }

}