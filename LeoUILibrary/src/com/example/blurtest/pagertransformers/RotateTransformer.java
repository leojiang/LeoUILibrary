package com.example.blurtest.pagertransformers;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class RotateTransformer implements PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        if (position <= 0) {
            view.setScaleX(1 + position);
            view.setScaleY(1 + position);
            view.setRotation(360 * position);
        } else if (position <= 1) {
            view.setScaleX(1 - position);
            view.setScaleY(1 - position);
            view.setRotation(360 * position);
        }
    }

}
