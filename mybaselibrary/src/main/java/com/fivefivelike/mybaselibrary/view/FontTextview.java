package com.fivefivelike.mybaselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.fivefivelike.mybaselibrary.R;

import io.reactivex.internal.util.OpenHashSet;
import skin.support.widget.SkinCompatTextView;

/**
 * Created by 郭青枫 on 2017/10/14.
 */

public class FontTextview extends SkinCompatTextView {
    OpenHashSet<Typeface> resources;

    int type = 0;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        this.setTextColor(Color.parseColor(color));
    }

    public FontTextview(Context context) {
        super(context);
        init(context, null);
    }

    public FontTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FontTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet == null)
            return;

        TypedArray attributes = context.obtainStyledAttributes(
                attributeSet, R.styleable.FontTextview);

        type = attributes.getInt(
                R.styleable.FontTextview_TypeFace, type);
        if (type != 0) {
            Typeface typeface = getTypeface(context, type);
            if (typeface != null) {
                this.setTypeface(typeface);
            }
        }
        setIncludeFontPadding(false);
    }

    //            <enum name="Black" value="1"/>
    //            <enum name="BlackItalic" value="2"/>
    //            <enum name="Bold" value="3"/>
    //            <enum name="BoldItalic" value="4"/>
    //            <enum name="ExtraBold" value="5"/>
    //            <enum name="ExtraBoldItalic" value="6"/>
    //            <enum name="ExtraLight" value="7"/>
    //            <enum name="ExtraLightItalic" value="8"/>
    //            <enum name="Italic" value="9"/>
    //            <enum name="Light" value="10"/>
    //            <enum name="LightItalic" value="11"/>
    //            <enum name="Medium" value="12"/>
    //            <enum name="MediumItalic" value="13"/>
    //            <enum name="Regular" value="14"/>
    //            <enum name="SemiBold" value="15"/>
    //            <enum name="SemiBoldItalic" value="16"/>
    //            <enum name="Thin" value="17"/>
    //            <enum name="ThinItalic" value="18"/>
    public synchronized Typeface getTypeface(Context context, int type) {
        String name = "";
        if (type == 1) {
            name = "Black";
        } else if (type == 2) {
            name = "BlackItalic";
        } else if (type == 3) {
            name = "Bold";
        } else if (type == 4) {
            name = "BoldItalic";
        } else if (type == 5) {
            name = "ExtraBold";
        } else if (type == 6) {
            name = "ExtraBoldItalic";
        } else if (type == 7) {
            name = "ExtraLight";
        } else if (type == 8) {
            name = "ExtraLightItalic";
        } else if (type == 9) {
            name = "Italic";
        } else if (type == 10) {
            name = "Light";
        } else if (type == 11) {
            name = "LightItalic";
        } else if (type == 12) {
            name = "Medium";
        } else if (type == 13) {
            name = "MediumItalic";
        } else if (type == 14) {
            name = "Regular";
        } else if (type == 15) {
            name = "SemiBold";
        } else if (type == 16) {
            name = "SemiBoldItalic";
        } else if (type == 17) {
            name = "Thin";
        } else if (type == 18) {
            name = "ThinItalic";
        }
        try {
            Typeface ttfTypeface = Typeface.createFromAsset(context.getAssets(), "font/Exo2-" + name + ".ttf");
            return ttfTypeface;
        } catch (Exception e) {
            return null;
        }
    }

}
