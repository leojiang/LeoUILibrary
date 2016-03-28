package com.example.blurtest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.blurtest.R;

public class TransitionLayoutActivity extends Activity implements View.OnClickListener {
    private LinearLayout layout;
    private Button mBtnRemove;
    private Button mBtnAdd;

    private LayoutTransition mTransitioner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_activity);
        mBtnRemove = (Button) findViewById(R.id.btn_remove);
        mBtnRemove.setOnClickListener(this);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
        layout = (LinearLayout) findViewById(R.id.parent);

        initTransition();
        setTransition();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_add) {
            addButtonView();
        } else if (id == R.id.btn_remove) {
            removeButtonView();
        }
    }

    private void initTransition() {
        mTransitioner = new LayoutTransition();
        layout.setLayoutTransition(mTransitioner);
    }

    private void setTransition() {
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "alpha", 0.0f, 1.0F, 0.5f).
//                setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));


        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "translationX", -200,0).
                setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
        animator1.setInterpolator(new OvershootInterpolator(0.2f));
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animator1);

//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(null, "rotationX", 0F, 90F, 0F).
//                setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
//        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animator2);

        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 1);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 1);

        PropertyValuesHolder animator3 = PropertyValuesHolder.ofFloat("scaleX", 1F, 2F, 1F);
        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, animator3).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
        changeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setScaleX(1.0f);
            }
        });
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);

        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 2f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation =
                PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2);
        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        changeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setScaleX(1.0f);
            }
        });
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
    }

    private void addButtonView() {
        Button button = new Button(this);
        button.setText("button");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addView(button, Math.max(0, layout.getChildCount()), params);
    }

    private void removeButtonView() {
        if (layout.getChildCount() > 0)
            layout.removeViewAt(0);
    }
}
