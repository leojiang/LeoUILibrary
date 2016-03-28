package com.example.blurtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blurtest.R;

public class SatelliteMenu extends ViewGroup {
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    private static final float RADIO_PADDING_LAYOUT = 1 / 12f;

    private int mRadius;
    private float mPadding;
    private String[] mItemTexts;
    private int[] mItemImgs;
    private int mMenuItemCount;
    private Context mContext;


    public SatelliteMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //ignore padding property
        setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutRadius = mRadius;
        int childCount = getChildCount();
        int left, top;
        int childWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        float angleDelay = 360 / childCount;

        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            mStartAngle %= 360;
            float tmp = layoutRadius / 2f - childWidth / 2;

            left = layoutRadius / 2 + (int) Math.round(tmp * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f * childWidth);
            top = layoutRadius / 2 + (int) Math.round(tmp * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f * childWidth);
            child.layout(left, top, left + childWidth, top + childWidth);
            mStartAngle += angleDelay;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            resWidth = getSuggestedMinimumWidth();
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;
            resHeight = getSuggestedMinimumHeight();
            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
        } else {
            resWidth = resHeight = Math.min(width, height);
        }

        setMeasuredDimension(resWidth, resHeight);

        mRadius = Math.min(getMeasuredWidth(), getMeasuredHeight());

        final int count = getChildCount();
        int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        int childMode = MeasureSpec.EXACTLY;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }

        mPadding = RADIO_PADDING_LAYOUT * mRadius;
    }

    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    public interface OnMenuItemClickListener {
        void itemClick(View view, int pos);
    }

    private OnMenuItemClickListener mOnMenuItemClickListener;

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }

    private void addMenuItems() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());

        for (int i = 0; i < mMenuItemCount; i++) {
            final int j = i;
            View view = mInflater.inflate(R.layout.circle_menu_item, this, false);
            ImageView iv = (ImageView) view
                    .findViewById(R.id.id_circle_menu_item_image);
            TextView tv = (TextView) view
                    .findViewById(R.id.id_circle_menu_item_text);

            if (iv != null) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(mItemImgs[i]);
            }
            if (tv != null) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(mItemTexts[i]);
            }

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.itemClick(v, j);
                    }
                }
            });

            addView(view);
        }
    }

    public void setMenuItemIconsAndTexts(int[] resIds, String[] texts) {
        mItemImgs = resIds;
        mItemTexts = texts;

        if (resIds == null && texts == null) {
            throw new IllegalArgumentException("nothing is given");
        }

        mMenuItemCount = resIds == null ? texts.length : resIds.length;

        if (resIds != null && texts != null) {
            mMenuItemCount = Math.min(resIds.length, texts.length);
        }

        addMenuItems();

    }

    private float mLastX;
    private float mLastY;
    //delta angle between down and up event.
    private float mTmpAngle;
    //time elapsed between down and up event.
    private long mDownTime;
    //when rotate speed is up to 300 degree per second, consider as fling event.
    private static final int FLINGABLE_VALUE = 300;
    //layout start angle
    private double mStartAngle = 0;
    //if delta angle is large than 3, intercept click event on children
    private static final int NOCLICK_VALUE = 3;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;

                //if rotating automatically, stop it
                if (isFling) {
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                //get the angle when touch down.
                float start = getAngle(mLastX, mLastY);
                //get current angle
                float end = getAngle(x, y);
                // calculate start angle according to quadrant.
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else {
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }
                // request layout after angle changed.
                requestLayout();

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:
                // rotate speed : angle per second.
                float anglePerSecond = mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime);
                //if fling condition is reached.
                if (Math.abs(anglePerSecond) > FLINGABLE_VALUE && !isFling) {
                    // post a runnable to rotate views.
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));
                    return true;
                }

                // intercept touch event on children
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }

        return super.onTouchEvent(event);
    }

    /**
     * get angle of the touch point.
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * get quadrant based on touch point
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }
    }

    private AutoFlingRunnable mFlingRunnable;
    private boolean isFling;

    private class AutoFlingRunnable implements Runnable {
        private float angelPerSecond;

        public AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {
            // if speed is less than 20/s, stop rotating.
            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                return;
            }
            isFling = true;
            // modify mStartAngle, make children rotating,  /30 to slow it down.
            mStartAngle += (angelPerSecond / 30);
            angelPerSecond /= 1.0666F;
            postDelayed(this, 30);
            requestLayout();
        }
    }
}
