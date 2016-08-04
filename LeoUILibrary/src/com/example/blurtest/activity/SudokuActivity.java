package com.example.blurtest.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.blurtest.R;
import com.example.blurtest.view.FancyImageLayout;

public class SudokuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        String[] urls = {
//        "http://a2.att.hudong.com/14/06/20300001244055130863068274072.jpg",
//        "http://mg.soupingguo.com/bizhi/big/10/054/203/10054203.jpg",
        "http://a0.att.hudong.com/51/47/01300000098168123480478377186.jpg",
        "http://pic33.nipic.com/20131011/2531170_115542187000_2.jpg",
        "http://pic20.nipic.com/20120406/6113596_150042568000_2.jpg",
        "http://5.66825.com/download/pic/000/326/719d548e256e2677aeb91a7a9c464571.jpg",
        "http://i1.17173cdn.com/9ih5jd/YWxqaGBf/forum/images/2014/08/01/231302zkvvgn1ng0lnglzn.jpg",
        "http://www.bz55.com/uploads/allimg/101227/1_101227162638_1.jpg",
        "http://www.impcas.ac.cn/kxcb/kxtp/201010/W020101028494020681011.jpg"};

        FancyImageLayout layout = (FancyImageLayout) findViewById(R.id.imagelayout);
        layout.setImageSources(urls);

        layout.requestLayout();
    }
}
