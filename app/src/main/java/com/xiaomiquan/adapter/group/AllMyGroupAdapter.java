package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class AllMyGroupAdapter extends CommonAdapter<GroupItem> {


    private TextView tv_my_group_name1;
    private TextView tv_today_comein1;
    private TextView tv_all_comein1;
    private TextView tv_my_group_commit1;
    private LinearLayout lin_one_group;

    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public AllMyGroupAdapter(Context context, List<GroupItem> datas) {
        super(context, R.layout.adapter_my_group, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GroupItem s, final int position) {
        tv_my_group_name1 = holder.getView(R.id.tv_my_group_name1);
        tv_today_comein1 = holder.getView(R.id.tv_today_comein1);
        tv_all_comein1 = holder.getView(R.id.tv_all_comein1);
        tv_my_group_commit1 = holder.getView(R.id.tv_my_group_commit1);
        lin_one_group = holder.getView(R.id.lin_one_group);

        tv_my_group_name1.setText(s.getName());


        BigUIUtil.getinstance().rateTextView(s.getCurrProfit(),tv_today_comein1);
        BigUIUtil.getinstance().rateTextView(s.getTotalProfit(),tv_all_comein1);



        tv_my_group_commit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });
    }

}