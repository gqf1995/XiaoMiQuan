package com.xiaomiquan.adapter.msg;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circledialog.res.drawable.SelectorBtn;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class MsgSysytemAdapter extends CommonAdapter<String> {


    private TextView tv_time;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_look_detail;
    private LinearLayout lin_root;

    public MsgSysytemAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_msg_system, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_time = holder.getView(R.id.tv_time);
        tv_title = holder.getView(R.id.tv_title);
        tv_content = holder.getView(R.id.tv_content);
        tv_look_detail = holder.getView(R.id.tv_look_detail);
        lin_root = holder.getView(R.id.lin_root);

        lin_root.setBackground(new SelectorBtn(CommonUtils.getColor(R.color.colorPrimary),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px),
                (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_10px)
        ));
        ViewCompat.setElevation(lin_root, CommonUtils.getDimensionPixelSize(R.dimen.trans_5px));


    }

}