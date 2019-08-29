package com.sapl.retailerorderingmsdpharma.customView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.sapl.retailerorderingmsdpharma.activities.MyApplication;


/**
 * Created by MRUNAL on 07-Feb-18.
 */
public class CustomUnderLine extends View {
    public CustomUnderLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomUnderLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomUnderLine(Context context) {
        super(context);
        init();
    }

    public void init() {
        setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        /*Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nunito_Regular.ttf");
        setBackgroundColor(this.getResources().getColor(R.color.gray));
        setTypeface(tf, 1);*/
    }
}
