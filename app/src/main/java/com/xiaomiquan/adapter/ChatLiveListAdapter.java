package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.UiHeplUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import skin.support.widget.SkinCompatCardView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ChatLiveListAdapter extends CommonAdapter<String> {


    private RoundedImageView iv_bg;
    private TextView tv_status;
    private TextView tv_online_num;
    private TextView tv_content;
    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_rank;
    private TextView tv_Introduction;
    private SkinCompatCardView card_root;
    int[] ints;

    public ChatLiveListAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_chart_live_list, datas);
        ints = UiHeplUtils.cacularWidAndHei(context, R.dimen.trans_90px, 2, R.dimen.trans_5px, R.dimen.trans_5px);

    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        iv_bg = holder.getView(R.id.iv_bg);
        tv_status = holder.getView(R.id.tv_status);
        tv_online_num = holder.getView(R.id.tv_online_num);
        tv_content = holder.getView(R.id.tv_content);
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_rank = holder.getView(R.id.tv_rank);
        tv_Introduction = holder.getView(R.id.tv_Introduction);
        card_root = holder.getView(R.id.card_root);
        UiHeplUtils.setCacularWidAndHei(ints, card_root);


    }

}