package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.yanzhenjie.album.AlbumFile;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ReleaseDynamicAdapter extends CommonAdapter<AlbumFile> {


    private ImageView iv_img;
    private IconFontTextview icf_plus;

    public List<String> path;
    public List<File> fileList;
    public ArrayList<AlbumFile> albumFiles;


    public ReleaseDynamicAdapter(Context context, ArrayList<AlbumFile> datas) {
        super(context, R.layout.adapter_release_dynamic, datas);
        this.path = new ArrayList<>();
        this.fileList = new ArrayList<>();
        this.albumFiles = datas;
    }

    @Override
    protected void convert(ViewHolder holder, AlbumFile s, final int position) {
        iv_img = holder.getView(R.id.iv_img);
        icf_plus = holder.getView(R.id.icf_plus);

        if (position != 0) {
            icf_plus.setVisibility(View.GONE);
            iv_img.setVisibility(View.VISIBLE);
            path.add(s.getPath());
            fileList.add(new File(s.getPath()));
            GlideUtils.loadImage(Uri.fromFile(new File(s.getPath())), iv_img);
        } else {
            icf_plus.setVisibility(View.VISIBLE);
            iv_img.setVisibility(View.GONE);
        }
    }

}