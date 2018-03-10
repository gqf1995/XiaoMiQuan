package com.xiaomiquan.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.AddPicAdapter;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.TimeUtils;
import com.xiaomiquan.utils.UiHeplUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class UserCenterListAdapter extends CommonAdapter<SquareLive> {

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
    private RecyclerView recycler_view;
    private TextView tv_article_content;


    public UserCenterListAdapter(Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_user_center_list, datas);
    }

    @Override
    protected void convert(ViewHolder holder, SquareLive s, final int position) {
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
        recycler_view = holder.getView(R.id.recycler_view);
        tv_article_content = holder.getView(R.id.tv_article_content);


        lin_view.setVisibility(View.GONE);
        lin_predict.setVisibility(View.GONE);
        lin_article.setVisibility(View.GONE);
        //文章1  观点2 操作3
        if ("1".equals(s.getType())) {
            tv_statu.setText("发表了文章");
            lin_article.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(s.getImg(), iv_piv);
            tv_article_title.setText(s.getTitle());
            tv_article_content.setText(s.getContent());
        } else if ("2".equals(s.getType())) {
            tv_statu.setText("发表了观点");
            lin_view.setVisibility(View.VISIBLE);
            tv_view.setText(s.getContent());
            initImg(s.getImgList(), recycler_view);
        } else if ("3".equals(s.getType())) {
            tv_statu.setText("更新了操作");
            lin_predict.setVisibility(View.VISIBLE);
            if (!ListUtils.isEmpty(s.getCommentVos())) {
                tv_operation_time.setText(com.blankj.utilcode.util.TimeUtils.millis2String(s.getUserDemoDealVos().get(0).getCreateTime(), TimeUtils.DEFAULT_FORMAT));
                if ("1".equals(s.getUserDemoDealVos().get(0).getType())) {
                    tv_operation_type.setText(CommonUtils.getString(R.string.str_buy));
                } else {
                    tv_operation_type.setText(CommonUtils.getString(R.string.str_sell));
                }
                tv_operation_currency.setText(s.getUserDemoDealVos().get(0).getSymbol());
                tv_operation_deal_price.setText(BigUIUtil.getinstance().bigPrice(s.getUserDemoDealVos().get(0).getPrice()));
                if (s.getUserDemoDealVos().get(0).getPositionRetaBefore() > s.getUserDemoDealVos().get(0).getPositionRetaAfter()) {
                    tv_operation_change.setText(s.getUserDemoDealVos().get(0).getPositionRetaBefore() + "%" + CommonUtils.getString(R.string.ic_Fall) + s.getUserDemoDealVos().get(0).getPositionRetaAfter() + "%");
                } else {
                    tv_operation_change.setText(s.getUserDemoDealVos().get(0).getPositionRetaBefore() + "%" + CommonUtils.getString(R.string.ic_Climb) + s.getUserDemoDealVos().get(0).getPositionRetaAfter() + "%");
                }
            }
        }

        if (!TextUtils.isEmpty(s.getCreateTime())) {
            tv_time.setText(TimeUtils.getDateToLeftTime(Long.parseLong(s.getCreateTime())));
        }


    }

    public void initImg(List<String> paths, RecyclerView recyclerView) {
        AddPicAdapter addPicAdapter = new AddPicAdapter(mContext, paths);
        addPicAdapter.setShowAdd(false);
        UiHeplUtils.initChoosePicRv(paths,
                addPicAdapter,
                (FragmentActivity) mContext,
                recyclerView,
                3,
                R.dimen.trans_120px,
                0,
                true,
                6
        );
    }
}