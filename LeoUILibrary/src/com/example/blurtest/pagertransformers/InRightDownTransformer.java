package com.example.blurtest.pagertransformers;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class InRightDownTransformer implements PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        int pageHeight = view.getHeight();
        view.setTranslationY(pageHeight * position);
    }
}
