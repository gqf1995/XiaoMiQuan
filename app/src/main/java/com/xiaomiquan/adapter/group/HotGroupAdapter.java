package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class HotGroupAdapter extends CommonAdapter<GroupItem> {


    DefaultClickLinsener defaultClickLinsener;
    private TextView tv_num;
    private TextView tv_last_week_earnings;
    private TextView tv_name;
    private TextView tv_focuse_num;
    private TextView tv_focuse;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public HotGroupAdapter(Context context, List<GroupItem> datas) {
        super(context, R.layout.adapter_hot_group, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GroupItem s, final int position) {
        tv_num = holder.getView(R.id.tv_num);
        tv_last_week_earnings = holder.getView(R.id.tv_last_week_earnings);
        tv_name = holder.getView(R.id.tv_name);
        tv_focuse_num = holder.getView(R.id.tv_focuse_num);
        tv_focuse = holder.getView(R.id.tv_focuse);


        tv_num.setText("" + s.getSort());
        BigUIUtil.getinstance().rateTextView(s.getRate(), tv_last_week_earnings);

        tv_name.setText(s.getName());
        tv_focuse_num.setText(s.getAttentionCount() + CommonUtils.getString(R.string.str_people) + CommonUtils.getString(R.string.str_focuse));

        if (s.getIsAttention() == 0) {
            tv_focuse.setText(CommonUtils.getString(R.string.str_details));
        } else if (s.getIsAttention() == 1) {
            tv_focuse.setText(CommonUtils.getString(R.string.str_focuse));
        } else if (s.getIsAttention() == 2) {
            tv_focuse.setVisibility(View.GONE);
        }
        tv_focuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });
    }

    public void setDatas(List<GroupItem> datas) {
        getDatas().clear();
        getDatas().addAll(datas);
        notifyDataSetChanged();
    }

}