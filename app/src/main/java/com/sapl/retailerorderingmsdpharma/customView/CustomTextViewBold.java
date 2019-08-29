package com.sapl.retailerorderingmsdpharma.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by ACER on 16-03-2017.
 */

public class CustomTextViewBold extends android.support.v7.widget.AppCompatTextView{

//    private float spacing = Spacing.NORMAL;
    private CharSequence originalText = "";
    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nunito_Regular.ttf");
        setTypeface(tf ,1);

    }


}
