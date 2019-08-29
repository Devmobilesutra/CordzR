package com.sapl.retailerorderingmsdpharma.customView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sapl.retailerorderingmsdpharma.R;
import com.sapl.retailerorderingmsdpharma.activities.MyApplication;

/**
 * Created by ACER on 26-05-2017.
 */

public class CustomEditTextMedium extends EditText {
    public CustomEditTextMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomEditTextMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditTextMedium(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nunito_Regular.ttf");
        setMaxLines(1);
        setSingleLine(true);
        setHintTextColor(this.getResources().getColor(R.color.gray));
        setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        setBackgroundColor(this.getResources().getColor(R.color.white));
        setTypeface(tf, 1);
    }
}
