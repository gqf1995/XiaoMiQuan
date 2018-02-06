package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.News;
import com.xiaomiquan.entity.bean.circle.UserFriende;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class NewsAdapter extends CommonAdapter<News> {


    DefaultClickLinsener defaultClickLinsener;
    private TextView tv_time;
    private IconFontTextview icf_shared;
    private TextView tv_shared;
    private TextView tv_con;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private TextView tv_dislike_num;
    private IconFontTextview tv_dislike;
    private AppCompatImageView iv_start1;
    private AppCompatImageView iv_start2;
    private AppCompatImageView iv_start3;
    private AppCompatImageView iv_start4;
    private AppCompatImageView iv_start5;


    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public NewsAdapter(Context context, List<News> datas) {
        super(context, R.layout.adapter_news, datas);
    }

    @Override
    protected void convert(ViewHolder holder, News s, final int position) {
        tv_time = holder.getView(R.id.tv_time);
        icf_shared = holder.getView(R.id.icf_shared);
        tv_shared = holder.getView(R.id.tv_shared);
        tv_con = holder.getView(R.id.tv_con);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        tv_dislike_num = holder.getView(R.id.tv_dislike_num);
        tv_dislike = holder.getView(R.id.tv_dislike);
        iv_start1 = holder.getView(R.id.iv_start1);
        iv_start2 = holder.getView(R.id.iv_start2);
        iv_start3 = holder.getView(R.id.iv_start3);
        iv_start4 = holder.getView(R.id.iv_start4);
        iv_start5 = holder.getView(R.id.iv_start5);
        List<AppCompatImageView> satrts = new ArrayList<>();
        satrts.add(iv_start1);
        satrts.add(iv_start2);
        satrts.add(iv_start3);
        satrts.add(iv_start4);
        satrts.add(iv_start5);

        for (int i = 0; i < 3; i++) {
            satrts.get(i).setImageResource(R.drawable.ic_start);
        }


    }

}