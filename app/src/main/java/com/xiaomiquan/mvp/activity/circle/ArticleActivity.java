package com.xiaomiquan.mvp.activity.circle;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.ArtivleAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.circle.ArticleBinder;
import com.xiaomiquan.mvp.delegate.circle.ArticleDelegate;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleActivity extends BasePullActivity<ArticleDelegate, ArticleBinder> {

    ArtivleAdapter artivleAdapter;
    LinearLayoutManager linearLayoutManager;
    HeaderAndFooterWrapper headerAndFooterWrapper;
    UserLogin userLogin;

    @Override
    protected Class<ArticleDelegate> getDelegateClass() {
        return ArticleDelegate.class;
    }

    @Override
    public ArticleBinder getDataBinder(ArticleDelegate viewDelegate) {
        return new ArticleBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_tv_article)).setSubTitle(CommonUtils.getString(R.string.str_release)));
        initArticle(new ArrayList<SquareLive>());
        addRequest(binder.getArticle(ArticleActivity.this));
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getArticle(ArticleActivity.this));
            }
        });

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        ReleaseArticleActivity.startAct(ArticleActivity.this, "1", "1", "0");
    }

    public void initArticle(final List<SquareLive> squareLives) {
        if (artivleAdapter == null) {
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            artivleAdapter = new ArtivleAdapter(binder, ArticleActivity.this, squareLives);
            artivleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                    ArticleDetailsActivity.startAct(ArticleActivity.this, squareLives.get(position - 1));
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            artivleAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    switch (view.getId()) {
                        case R.id.cv_head:
                            PersonalHomePageActivity.startAct(ArticleActivity.this, squareLives.get(position - 1).getUserId());
                            break;
//                        case R.id.lin_praise:
//                            if (artivleAdapter.isUserPraise.get(position - 1)) {
//
//                                artivleAdapter.isUserPraise.add(position - 1, false);
//                                artivleAdapter.praiseNum.add(position - 1, artivleAdapter.praiseNum.get(position - 1) - 1);
//                            } else {
//                                artivleAdapter.isUserPraise.add(position - 1, true);
//                                artivleAdapter.praiseNum.add(position - 1, artivleAdapter.praiseNum.get(position - 1) + 1);
//                            }
//                            artivleAdapter.notifyDataSetChanged();
//                            addRequest(binder.savePraise(squareLives.get(position - 1).getId(), ArticleActivity.this));
//                            break;
                    }
                }
            });
            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
            headerAndFooterWrapper = new HeaderAndFooterWrapper(artivleAdapter);
            headerAndFooterWrapper.addHeaderView(initHead());
            initRecycleViewPull(headerAndFooterWrapper, 1, new LinearLayoutManager(ArticleActivity.this));
        } else {
            if (squareLives.size() > 0) {
                initHeadView(squareLives.get(0));
                squareLives.remove(0);
                getDataBack(artivleAdapter.getDatas(), squareLives, artivleAdapter);
            }
        }

    }


    public ImageView iv_banner;
    public TextView tv_time;
    public TextView tv_title;
    public CircleImageView iv_head;
    public TextView tv_name;
    public IconFontTextview tv_comment;
    public TextView tv_comment_num;
    public IconFontTextview tv_praise;
    public TextView tv_praise_num;
    public LinearLayout lin_praise;

    private View initHead() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_article_top, null);
        this.iv_banner = (ImageView) rootView.findViewById(R.id.iv_banner);
        this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        this.iv_head = (CircleImageView) rootView.findViewById(R.id.iv_head);
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        this.tv_comment = (IconFontTextview) rootView.findViewById(R.id.tv_comment);
        this.tv_comment_num = (TextView) rootView.findViewById(R.id.tv_comment_num);
        this.tv_praise = (IconFontTextview) rootView.findViewById(R.id.tv_praise);
        this.tv_praise_num = (TextView) rootView.findViewById(R.id.tv_praise_num);
        this.lin_praise = (LinearLayout) rootView.findViewById(R.id.lin_praise);

        return rootView;
    }

    private void initHeadView(final SquareLive squareLive) {
        /**
         * 用户是否点赞
         //                 */
        if (squareLive.isUserPraise()) {
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
        } else {
            tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
            tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
        }
        tv_time.setText(squareLive.getCreateTimeStr());
        tv_title.setText(squareLive.getTitle());
        GlideUtils.loadImage(squareLive.getAvatar(), iv_head);
        GlideUtils.loadImage(squareLive.getImg(), iv_banner);
        tv_name.setText(squareLive.getNickName());
        tv_comment_num.setText(squareLive.getCommentCount() + "");
        tv_praise_num.setText(squareLive.getGoodCount() + "");
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleDetailsActivity.startAct(ArticleActivity.this, squareLive);
            }
        });

        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalHomePageActivity.startAct(ArticleActivity.this, squareLive.getUserId());
            }
        });

        lin_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userLogin != null) {
                /**
                 * 用户是否点赞
                 //                 */
                if (squareLive.isUserPraise()) {
                    squareLive.setUserPraise(false);
                    squareLive.setGoodCount(squareLive.getGoodCount() - 1);
                    tv_praise_num.setText(squareLive.getGoodCount() + "");
                    tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
                    tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));

                } else {
                    squareLive.setUserPraise(true);
                    squareLive.setGoodCount(squareLive.getGoodCount() + 1);
                    tv_praise_num.setText(squareLive.getGoodCount() + "");
                    tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
                    tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));

                }
                addRequest(binder.savePraise(squareLive.getId(), ArticleActivity.this));
                } else {
                    ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                }
            }
        });

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleDetailsActivity.startAct(ArticleActivity.this, squareLive);
            }
        });


    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initArticle(datas);
                break;
            case 0x124:
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getArticle(ArticleActivity.this));
    }

}
