package com.example.blurtest.pagertransformers;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class RotateDownPageTransformer implements PageTransformer {

    private static final float ROT_MAX = 20.0f;
    private float mRot;

    public void transformPage(View view, float position) {

        if (position <= 1 && position > -1) {
            mRot = (ROT_MAX * position);
            view.setPivotX(view.getMeasuredWidth() * 0.5f);
            view.setPivotY(view.getMeasuredHeight());
            view.setRotation(mRot);
        }
    }
}