package com.xiaomiquan.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class UserCenterListAdapter extends CommonAdapter<String> {

    private TextView tv_time;
    private TextView tv_statu;
    private TextView tv_operation_time;
    private TextView tv_operation_type;
    private TextView tv_operation_currency;
    private TextView tv_operation_deal_price;
    private TextView tv_operation_change;
    private LinearLayout lin_operation;
    private TextView tv_view;
    private LinearLayout lin_view;
    private TextView tv_predict_time;
    private TextView tv_predict_content;
    private TextView tv_predict_statu;
    private LinearLayout lin_predict;
    private ImageView iv_piv;
    private TextView tv_article_title;
    private LinearLayout lin_article;

    public UserCenterListAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_user_center_list, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_time = holder.getView(R.id.tv_time);
        tv_statu = holder.getView(R.id.tv_statu);
        tv_operation_time = holder.getView(R.id.tv_operation_time);
        tv_operation_type = holder.getView(R.id.tv_operation_type);
        tv_operation_currency = holder.getView(R.id.tv_operation_currency);
        tv_operation_deal_price = holder.getView(R.id.tv_operation_deal_price);
        tv_operation_change = holder.getView(R.id.tv_operation_change);
        lin_operation = holder.getView(R.id.lin_operation);
        tv_view = holder.getView(R.id.tv_view);
        lin_view = holder.getView(R.id.lin_view);
        tv_predict_time = holder.getView(R.id.tv_predict_time);
        tv_predict_content = holder.getView(R.id.tv_predict_content);
        tv_predict_statu = holder.getView(R.id.tv_predict_statu);
        lin_predict = holder.getView(R.id.lin_predict);
        iv_piv = holder.getView(R.id.iv_piv);
        tv_article_title = holder.getView(R.id.tv_article_title);
        lin_article = holder.getView(R.id.lin_article);


    }

}