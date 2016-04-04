package com.example.blurtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.blurtest.R;
import com.example.blurtest.draglayouttomenu.DragLayoutActivity;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnImageFilter = (Button) findViewById(R.id.image_filter);
        btnImageFilter.setOnClickListener(this);

        Button btnGifImage = (Button) findViewById(R.id.gif_test);
        btnGifImage.setOnClickListener(this);


        Button btnViewPager = (Button) findViewById(R.id.viewpager_test);
        btnViewPager.setOnClickListener(this);

        Button btnDragLayout = (Button) findViewById(R.id.drag_layout_test);
        btnDragLayout.setOnClickListener(this);

        Button btnSatellites = (Button) findViewById(R.id.satellites);
        btnSatellites.setOnClickListener(this);

        Button btnClassLoaderTest = (Button) findViewById(R.id.ClassLoaderTest);
        btnClassLoaderTest.setOnClickListener(this);

        Button btnGesture = (Button) findViewById(R.id.gesture_test);
        btnGesture.setOnClickListener(this);

        Button btnTransition = (Button) findViewById(R.id.layout_test);
        btnTransition.setOnClickListener(this);

        Button html5Test = (Button) findViewById(R.id.html5_test);
        html5Test.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.image_filter:
                startActivity(ImageFilterMainActivity.class);
                break;
            case R.id.gif_test:
                startActivity(GifActivity.class);
                break;
            case R.id.viewpager_test:
                startActivity(ViewPagerActivity.class);
                break;
            case R.id.drag_layout_test:
                startActivity(DragLayoutActivity.class);
                break;
            case R.id.satellites:
                startActivity(SatelliteMenuActivity.class);
                break;
            case R.id.ClassLoaderTest:
                startActivity(ClassLoaderActivity.class);
                break;
            case R.id.gesture_test:
                startActivity(GestureActivity.class);
                break;
            case R.id.layout_test:
                startActivity(TransitionLayoutActivity.class);
                break;
            case R.id.html5_test:
                startActivity(Html5Test.class);
                break;
            default:
                break;
        }
    }

    private void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }
}