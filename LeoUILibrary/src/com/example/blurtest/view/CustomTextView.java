package com.example.blurtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.blurtest.giftview.BaseDensityUtil;

import java.lang.reflect.Field;

public class CustomTextView extends TextView {

    private int mBorderColor;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBorderColor(int color) {
        mBorderColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint m_TextPaint = getPaint();
        setTextColorUseReflection(Color.parseColor("#FC5C6C"));
        m_TextPaint.setStrokeWidth(BaseDensityUtil.dip2px(getContext(), 2));
        m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        m_TextPaint.setShadowLayer(3, 5, 5, Color.parseColor("#000000"));
        super.onDraw(canvas);

        setTextColorUseReflection(Color.WHITE);
        m_TextPaint.setStrokeWidth(0);
        m_TextPaint.clearShadowLayer();
        super.onDraw(canvas);
    }

    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.setInt(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

