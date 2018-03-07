package com.xiaomiquan.mvp.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.dialog.BaseDialog;
import com.xiaomiquan.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/3/7 0007.
 */

public class ReleaseDialog extends BaseDialog {

    List<ReleaseDialogEntity> datas;
    String title;
    List<ImageView> bgImages;// = new ArrayList<>();
    List<ImageView> iconImages;// = new ArrayList<>();
    List<TextView> titleTextViews;// = new ArrayList<>();

    public ReleaseDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ReleaseDialog setDatas(List<ReleaseDialogEntity> datas) {
        this.datas = datas;
        return this;
    }

    public TextView tv_title;
    public CircleImageView ic_pic1;
    public ImageView iv_piv1;
    public TextView tv_select1;
    public LinearLayout lin1;
    public CircleImageView ic_pic2;
    public ImageView iv_piv2;
    public TextView tv_select2;
    public LinearLayout lin2;
    public CircleImageView ic_pic3;
    public ImageView iv_piv3;
    public TextView tv_select3;
    public LinearLayout lin3;
    public CircleImageView ic_pic4;
    public ImageView iv_piv4;
    public TextView tv_select4;
    public TextView tv_close;
    public LinearLayout lin4;

    DefaultClickLinsener defaultClickLinsener;

    public ReleaseDialog setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
        return this;
    }

    public ReleaseDialog(Context context) {
        super(context);
    }

    public ReleaseDialog(Context context, int style) {
        super(context, style);
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_release;
    }

    @Override
    protected void startInit() {
        getWindow().setGravity(Gravity.BOTTOM);
        setWindowNoPadding();
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.ic_pic1 = (CircleImageView) findViewById(R.id.ic_pic1);
        this.iv_piv1 = (ImageView) findViewById(R.id.iv_piv1);
        this.tv_select1 = (TextView) findViewById(R.id.tv_select1);
        this.lin1 = (LinearLayout) findViewById(R.id.lin1);
        this.ic_pic2 = (CircleImageView) findViewById(R.id.ic_pic2);
        this.iv_piv2 = (ImageView) findViewById(R.id.iv_piv2);
        this.tv_select2 = (TextView) findViewById(R.id.tv_select2);
        this.lin2 = (LinearLayout) findViewById(R.id.lin2);
        this.ic_pic3 = (CircleImageView) findViewById(R.id.ic_pic3);
        this.iv_piv3 = (ImageView) findViewById(R.id.iv_piv3);
        this.tv_select3 = (TextView) findViewById(R.id.tv_select3);
        this.lin3 = (LinearLayout) findViewById(R.id.lin3);
        this.ic_pic4 = (CircleImageView) findViewById(R.id.ic_pic4);
        this.iv_piv4 = (ImageView) findViewById(R.id.iv_piv4);
        this.tv_select4 = (TextView) findViewById(R.id.tv_select4);
        this.tv_close = (TextView) findViewById(R.id.tv_close);
        this.lin4 = (LinearLayout) findViewById(R.id.lin4);
        bgImages = new ArrayList<>();
        bgImages.add(ic_pic1);
        bgImages.add(ic_pic2);
        bgImages.add(ic_pic3);
        bgImages.add(ic_pic4);
        iconImages = new ArrayList<>();
        iconImages.add(iv_piv1);
        iconImages.add(iv_piv2);
        iconImages.add(iv_piv3);
        iconImages.add(iv_piv4);
        titleTextViews = new ArrayList<>();
        titleTextViews.add(tv_select1);
        titleTextViews.add(tv_select2);
        titleTextViews.add(tv_select3);
        titleTextViews.add(tv_select4);
    }

    public void showDialog() {
        for (int i = 0; i < 4; i++) {
            bgImages.get(i).setImageResource(datas.get(i).bgId);
            iconImages.get(i).setImageResource(datas.get(i).iconId);
            titleTextViews.get(i).setText(datas.get(i).title);
        }
        tv_title.setText(title);
        this.lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, 1, null);
                }
            }
        });
        this.lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, 2, null);
                }
            }
        });
        this.lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, 3, null);
                }
            }
        });
        this.lin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, 4, null);
                }
            }
        });
        this.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.show();
    }

    public static class ReleaseDialogEntity {
        String title;
        int bgId;
        int iconId;

        public ReleaseDialogEntity(String title,
                                   int bgId,
                                   int iconId) {
            this.bgId = bgId;
            this.title = title;
            this.iconId = iconId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getBgId() {
            return bgId;
        }

        public void setBgId(int bgId) {
            this.bgId = bgId;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }
    }

}
