package com.sapl.retailerorderingmsdpharma.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sapl.retailerorderingmsdpharma.R;

/**
 * Created by ACER on 24-05-2017.
 */

public class CustomTextViewMedium extends android.support.v7.widget.AppCompatTextView {
    public CustomTextViewMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewMedium(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nunito_Regular.ttf");
        setTextColor(getResources().getColor(R.color.white));
        //setBackgroundColor(getResources().getColor(R.color.black));
       /* MyApplication.d("CustomTextViewMedium " +""+MyApplication.get_session("color") );
        if(MyApplication.get_session("color") != null) {
            setTextColor(Color.parseColor(MyApplication.get_session("color")));
        }
        else
             setTextColor(Color.parseColor("#FFFFFF"));*/
        setTypeface(tf ,1);
    }

    //added methods





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
