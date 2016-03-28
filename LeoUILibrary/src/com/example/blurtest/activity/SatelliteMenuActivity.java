package com.example.blurtest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.blurtest.R;
import com.example.blurtest.view.SatelliteMenu;

public class SatelliteMenuActivity extends Activity {

    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};

    private SatelliteMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satellite_menu);

        menu = (SatelliteMenu) findViewById(R.id.id_menulayout);
        menu.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        menu.setOnMenuItemClickListener(new SatelliteMenu.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(SatelliteMenuActivity.this, "item " + mItemTexts[pos] + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button animateBtn = (Button) findViewById(R.id.animation);
        animateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShrinked) {
                    startExpandAnimation(menu);
                } else {
                    startShrinkAnimation(menu);
                }
            }
        });
    }

    private boolean isShrinked = false;

    private void startExpandAnimation(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator anim0 = ObjectAnimator.ofFloat(view, "rotation",
                0f, 360f);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "alpha",
                0f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleX",
                0f, 1f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "scaleY",
                0f, 1f);

        animatorSet.playTogether(anim0, anim1, anim2, anim3);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isShrinked = false;
            }
        });
        animatorSet.start();
    }

    private void startShrinkAnimation(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator anim0 = ObjectAnimator.ofFloat(view, "rotation",
                360f, 0f);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "alpha",
                1f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleX",
                1f, 0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "scaleY",
                1f, 0f);

        animatorSet.playTogether(anim0, anim1, anim2, anim3);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isShrinked = true;
            }
        });
        animatorSet.start();
    }

}
