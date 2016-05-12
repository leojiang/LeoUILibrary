package com.example.blurtest.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

public class GraphicsTestActivity extends Activity {
    private final static int CIRCLE_RADIUS = 150;
    private final static float CIRCLE_ANGLE = 360f;
    private final static float START_ANGLE = -135f;
    private final static int STROKE_WIDTH = 10;
    private final static int JOINT_ADJUSTMENT = STROKE_WIDTH / 2;


    private ValueAnimator mProgressAnimation;
    private ValueAnimator mSuccessAnimation1;
    private ValueAnimator mSuccessAnimation2;
    private float mCurrentGlobalProgressValue = 0;
    private float mCrossProgressValue1 = 0;
    private float mCrossProgressValue2 = 0;
    private CustomView mCustomVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomVIew = new CustomView(this);
        setContentView(mCustomVIew);
        mCustomVIew.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mCustomVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetValues();
                mProgressAnimation.start();
            }
        });

        mProgressAnimation = ValueAnimator.ofFloat(0, CIRCLE_ANGLE).setDuration(500);
        mProgressAnimation.setStartDelay(500);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
        mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentGlobalProgressValue = (float) valueAnimator.getAnimatedValue();
                mCustomVIew.invalidate();
            }
        });
        mProgressAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mSuccessAnimation1.start();
            }
        });

        mSuccessAnimation1 = ValueAnimator.ofFloat(0, 1f).setDuration(150);
        mSuccessAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCrossProgressValue1 = (float) valueAnimator.getAnimatedValue();
                mCustomVIew.invalidate();
            }
        });
        mSuccessAnimation1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mSuccessAnimation2.start();
            }
        });

        mSuccessAnimation2 = ValueAnimator.ofFloat(0, 1f).setDuration(200);
        mSuccessAnimation2.setInterpolator(new OvershootInterpolator(1.2f));
        mSuccessAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCrossProgressValue2 = (float) valueAnimator.getAnimatedValue();
                mCustomVIew.invalidate();
            }
        });

        mCircleBounds = new RectF();
        mCircleBounds.top = 0f;
        mCircleBounds.left = 0f;
        mCircleBounds.bottom = CIRCLE_RADIUS * 2;
        mCircleBounds.right = CIRCLE_RADIUS * 2;
    }

    private void resetValues() {
        mCurrentGlobalProgressValue = 0;
        mCrossProgressValue1 = 0;
        mCrossProgressValue2 = 0;
    }

    private RectF mCircleBounds;

    @Override
    protected void onResume() {
        super.onResume();
        mProgressAnimation.start();
    }

    class CustomView extends View {
        private int START_POINT_Y = CIRCLE_RADIUS;
        private int START_POINT_X = CIRCLE_RADIUS - CIRCLE_RADIUS * 2 / 5;
        private int delta1 = CIRCLE_RADIUS / 3;
        private int delta2 = CIRCLE_RADIUS / 3 * 2;

        public CustomView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {

            Paint mDrawingPaint = new Paint();
            mDrawingPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            mDrawingPaint.setStyle(Paint.Style.STROKE);
            mDrawingPaint.setColor(Color.parseColor("#FF0000"));
            mDrawingPaint.setStrokeWidth(STROKE_WIDTH);
            canvas.translate(getWidth() / 2 - CIRCLE_RADIUS, getWidth() / 2 - CIRCLE_RADIUS);

            //draw circle
            canvas.drawArc(mCircleBounds, START_ANGLE, -mCurrentGlobalProgressValue, false, mDrawingPaint);

            if (mCurrentGlobalProgressValue < CIRCLE_ANGLE) {
                return;
            }

            //draw left line
            float changement = delta1 * mCrossProgressValue1;
            if (mCrossProgressValue2 <= 1) {
                //draw left line normally
                canvas.drawLine(START_POINT_X, START_POINT_Y, START_POINT_X + changement, START_POINT_Y + changement, mDrawingPaint);
            } else {
                //draw left line with overshoot value
                float overshootChangement = delta1 * (mCrossProgressValue2 - 1) * 2;
                canvas.drawLine(START_POINT_X + overshootChangement, START_POINT_Y + overshootChangement, START_POINT_X + changement, START_POINT_Y + changement, mDrawingPaint);
            }

            //draw right line
            if (mCrossProgressValue2 > 0) {
                float chagement1 = delta2 * mCrossProgressValue2;
                canvas.drawLine(START_POINT_X + delta1 - JOINT_ADJUSTMENT, START_POINT_Y + delta1, START_POINT_X + delta1 + chagement1, START_POINT_Y + delta1 - chagement1, mDrawingPaint);
            }
        }
    }
}
