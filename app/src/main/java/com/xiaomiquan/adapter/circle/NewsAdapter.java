package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.News;
import com.xiaomiquan.server.HttpUrl;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

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
    private LinearLayout lin_praise;
    private LinearLayout lin_dislike;
    BaseDataBind dataBind;


    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public NewsAdapter(BaseDataBind bind, Context context, List<News> datas) {
        super(context, R.layout.adapter_news, datas);
        dataBind = bind;
    }


    public void setDatas(List<News> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }


    @Override
    protected void convert(ViewHolder holder, final News s, final int position) {
        tv_time = holder.getView(R.id.tv_time);
        icf_shared = holder.getView(R.id.icf_shared);
        tv_shared = holder.getView(R.id.tv_shared);
        tv_con = holder.getView(R.id.tv_con);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        tv_dislike_num = holder.getView(R.id.tv_dislike_num);
        tv_dislike = holder.getView(R.id.tv_dislike);
        lin_praise = holder.getView(R.id.lin_praise);
        lin_dislike = holder.getView(R.id.lin_dislike);
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

        for (int i = 0; i < s.getLevel(); i++) {
            satrts.get(i).setImageResource(R.drawable.ic_start);
        }
        tv_con.setText(s.getContent());
        tv_dislike_num.setText(s.getBadNum());
        tv_praise_num.setText(s.getGoodNum());
        tv_time.setText(s.getCreateTime());
        if (s.getGood().equals("0")) {
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
        } else {
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
        }
        if (s.getBad().equals("0")) {
            tv_dislike.setTextColor(CommonUtils.getColor(R.color.color_font1));
            tv_dislike_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
        } else {
            tv_dislike.setTextColor(CommonUtils.getColor(R.color.color_blue));
            tv_dislike_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
        }

        lin_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.getBad().equals("0")) {
                    s.setBad("1");
                    s.setBadNum(s.getBadNum() + 1);
                    dataBind.addRequest(saveLike(dataBind, s.getId(), s.getId(), "1"));
                } else {
                    s.setBad("0");
                    s.setBadNum(s.getBadNum() - 1);
                    dataBind.addRequest(saveLike(dataBind, s.getId(), s.getId(), "0"));
                }
                notifyItemChanged(position);


            }
        });
        lin_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.getGood().equals("0")) {
                    s.setGood("1");
                    s.setGoodNum(s.getGoodNum() + 1);
                    dataBind.addRequest(saveLike(dataBind, s.getId(), s.getId(), "1"));
                } else {
                    s.setGood("0");
                    s.setGoodNum(s.getGoodNum() - 1);
                    dataBind.addRequest(saveLike(dataBind, s.getId(), s.getId(), "0"));
                }
                notifyItemChanged(position);

            }
        });


    }

    /**
     * 利好
     */
    private Disposable saveLike(BaseDataBind baseDataBind,
                                String userId,
                                String newsId,
                                String like
    ) {
        baseDataBind.getBaseMapWithUid();
        baseDataBind.put("userId", userId);
        baseDataBind.put("newsId", newsId);
        baseDataBind.put("like", like);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().saveNews)
                .setShowDialog(false)
                .setRequestName("利好")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseDataBind.getMap())
                .setRequestCallback(null)
                .build()
                .RxSendRequest();
    }

}