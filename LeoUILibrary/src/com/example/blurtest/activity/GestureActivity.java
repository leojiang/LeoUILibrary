package com.example.blurtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.blurtest.R;

public class GestureActivity extends Activity
        implements OnTouchListener {
    private Button mButton;
    private Button mClear;
    private TextView mText;
    GestureDetector mGesture = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_test);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnTouchListener(this);
        mGesture = new GestureDetector(this, new GestureListener());

        mText = (TextView) findViewById(R.id.text);
        mClear = (Button) findViewById(R.id.clear);
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mText.setText("");
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }

    class GestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mText.append("onDoubleTap\n");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mText.append("onDown\n");
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            Log.i("TEST", "onFling:velocityX = " + velocityX + " velocityY" + velocityY);
            mText.append("onFling:velocityX = " + velocityX + " velocityY" + velocityY + "\n");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mText.append("onLongPress\n");
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            mText.append("onScroll:distanceX = " + distanceX + " distanceY = " + distanceY + "\n");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mText.append("onSingleTapUp\n");
            return super.onSingleTapUp(e);
        }

    }
} 