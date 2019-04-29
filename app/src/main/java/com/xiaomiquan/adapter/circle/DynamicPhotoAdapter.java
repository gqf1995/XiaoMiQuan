package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.ImageView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.utils.UiHeplUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class DynamicPhotoAdapter extends CommonAdapter<String> {


    DefaultClickLinsener defaultClickLinsener;
    private IconFontTextview icf_plus;
    private ImageView iv_img;
    int[] ints;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public DynamicPhotoAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_release_dynamic, datas);
        ints = UiHeplUtils.cacularWidAndHei(context, R.dimen.trans_75px, R.dimen.trans_140px, 3, R.dimen.trans_10px, R.dimen.trans_10px);
    }

    public void setDatas(List<String> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        icf_plus = holder.getView(R.id.icf_plus);
        iv_img = holder.getView(R.id.iv_img);
        UiHeplUtils.setCacularWidAndHei(ints, iv_img);
        GlideUtils.loadImage(s, iv_img);
    }

}