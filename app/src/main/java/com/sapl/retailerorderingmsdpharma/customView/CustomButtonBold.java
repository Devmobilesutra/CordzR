package com.sapl.retailerorderingmsdpharma.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by ACER on 16-03-2017.
 */

public class CustomButtonBold extends Button{
//    private float spacing = Spacing.NORMAL;
        private CharSequence originalText = "";
    public CustomButtonBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nunito_Regular.ttf");
        setTypeface(tf ,1);

    }

//    public float getSpacing() {
//        return this.spacing;
//    }
//
//    public void setSpacing(float spacing) {
//        this.spacing = spacing;
//        applySpacing();
//    }
//
//    @Override
//    public void setText(CharSequence text, BufferType type) {
//        originalText = text;
//        applySpacing();
//    }
//
//    @Override
//    public CharSequence getText() {
//        return originalText;
//    }
//
//    private void applySpacing() {
//        if (this == null || this.originalText == null) return;
//        StringBuilder builder = new StringBuilder();
//        for(int i = 0; i < originalText.length(); i++) {
//            builder.append(originalText.charAt(i));
//            if(i+1 < originalText.length()) {
//                builder.append("\u00A0");
//
//                //U+0020
//            }
//        }
//        SpannableString finalText = new SpannableString(builder.toString());
//        if(builder.toString().length() > 1) {
//            for(int i = 1; i < builder.toString().length(); i+=2) {
//                finalText.setSpan(new ScaleXSpan((spacing+1)/10), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//        super.setText(finalText, BufferType.SPANNABLE);
//    }
//
//    public class Spacing {
//        public final static float NORMAL = 0;
//    }
}
