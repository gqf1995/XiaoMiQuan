package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.server.HttpUrl;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;

/**
 * Created by 谷乐园
 */

public class ArtivleAdapter extends CommonAdapter<SquareLive> {

    DefaultClickLinsener defaultClickLinsener;

    private ImageView ic_pic;
    private TextView tv_time;
    private TextView tv_title;
    private IconFontTextview tv_comment;
    private TextView tv_comment_num;
    private IconFontTextview tv_praise;
    private TextView tv_praise_num;
    private CircleImageView cv_head;

    public List<Boolean> isUserPraise;
    public List<Integer> praiseNum;
    UserLogin userLogin;

    BaseDataBind dataBind;
    private LinearLayout lin_praise;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }


    public ArtivleAdapter(BaseDataBind baseDataBind, Context context, List<SquareLive> datas) {
        super(context, R.layout.adapter_article, datas);
        dataBind = baseDataBind;
        isUserPraise = new ArrayList<>();
        praiseNum = new ArrayList<>();
        userLogin = SingSettingDBUtil.getUserLogin();
    }

    public void setDatas(List<SquareLive> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, final SquareLive s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_time = holder.getView(R.id.tv_time);
        tv_title = holder.getView(R.id.tv_title);
        tv_comment = holder.getView(R.id.tv_comment);
        tv_comment_num = holder.getView(R.id.tv_comment_num);
        tv_praise = holder.getView(R.id.tv_praise);
        tv_praise_num = holder.getView(R.id.tv_praise_num);
        cv_head = holder.getView(R.id.cv_head);
        lin_praise = holder.getView(R.id.lin_praise);
//
//        if (s != null) {
//            isUserPraise.add(s.isUserPraise());
//            praiseNum.add(s.getGoodCount());
//        }

        /**
         * 用户是否点赞
         */
        if (s.isUserPraise()) {
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
        } else {
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font2));
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font2));

        }

        tv_time.setText(s.getCreateTimeStr());
        tv_title.setText(s.getTitle());
        tv_comment_num.setText(s.getCommentCount() + "");
        tv_praise_num.setText(s.getGoodCount() + "");
        GlideUtils.loadImage(s.getImg(), ic_pic);
        GlideUtils.loadImage(s.getAvatar(), cv_head);

        lin_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userLogin != null) {
                    tv_praise = view.findViewById(R.id.tv_praise);
                    tv_praise_num = view.findViewById(R.id.tv_praise_num);
                    if (s.isUserPraise()) {
                        s.setUserPraise(false);
                        s.setGoodCount(s.getGoodCount() - 1);
                        tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font2));
                        tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font2));
                        tv_praise_num.setText(s.getGoodCount() + "");
                    } else {
                        s.setUserPraise(true);
                        s.setGoodCount(s.getGoodCount() + 1);
                        tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
                        tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
                        tv_praise_num.setText(s.getGoodCount() + "");
                    }
                    dataBind.addRequest(savePraise(dataBind, s.getId()));
//                notifyDataSetChanged();
                    notifyItemChanged(position - 1);
                } else {
                    ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                }
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
                tv_praise = view.findViewById(R.id.tv_praise);
                tv_praise_num = view.findViewById(R.id.tv_praise_num);
                if (s.isUserPraise()) {
                    s.setUserPraise(false);
                    s.setGoodCount(s.getGoodCount() - 1);
                    tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font2));
                    tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font2));
                    tv_praise_num.setText(s.getGoodCount() + "");
                } else {
                    s.setUserPraise(true);
                    s.setGoodCount(s.getGoodCount() + 1);
                    tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
                    tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
                    tv_praise_num.setText(s.getGoodCount() + "");
                }
                dataBind.addRequest(savePraise(dataBind, s.getId()));
//                notifyDataSetChanged();
                notifyItemChanged(position - 1);
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });


        cv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
    }

    /**
     * 点赞
     */
    private Disposable savePraise(BaseDataBind baseDataBind,
                                  String linkId
    ) {
        baseDataBind.getBaseMapWithUid();
        baseDataBind.put("linkId", linkId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().savePraise)
                .setShowDialog(false)
                .setRequestName("点赞")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseDataBind.getMap())
                .setRequestCallback(null)
                .build()
                .RxSendRequest();
    }

}