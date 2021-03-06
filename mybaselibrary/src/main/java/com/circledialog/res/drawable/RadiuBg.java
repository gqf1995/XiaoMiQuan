package com.circledialog.res.drawable;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;

/**
 * 按钮的背景，有点击效果
 * Created by hupei on 2017/3/30.
 */

public class RadiuBg extends StateListDrawable {

    public RadiuBg(int backgroundColor, int leftTopRadius, int rightTopRadius, int rightBottomRadius, int
            leftBottomRadius) {
        //默认
        ShapeDrawable defaultDrawable = new ShapeDrawable(DrawableHelper.getRoundRectShape(leftTopRadius,
                rightTopRadius,
                rightBottomRadius, leftBottomRadius));
        defaultDrawable.getPaint().setColor(backgroundColor);

        addState(new int[]{-android.R.attr.state_pressed}, defaultDrawable);
    }
}
