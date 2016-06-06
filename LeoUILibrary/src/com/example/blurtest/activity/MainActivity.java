package com.example.blurtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.GifTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestFutureTarget;
import com.example.blurtest.R;
import com.example.blurtest.draglayouttomenu.DragLayoutActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

//        Button btnClassLoaderTest = (Button) findViewById(R.id.ClassLoaderTest);
//        btnClassLoaderTest.setOnClickListener(this);
//
//        Button btnGesture = (Button) findViewById(R.id.gesture_test);
//        btnGesture.setOnClickListener(this);

        Button btnTransition = (Button) findViewById(R.id.layout_test);
        btnTransition.setOnClickListener(this);

//        Button html5Test = (Button) findViewById(R.id.html5_test);
//        html5Test.setOnClickListener(this);
//
//        Button okhttp_test = (Button) findViewById(R.id.okhttp_test);
//        okhttp_test.setOnClickListener(this);

        Button vector_drawable = (Button) findViewById(R.id.vector_drawable);
        vector_drawable.setOnClickListener(this);

        Button download_progress = (Button) findViewById(R.id.download_progress);
        download_progress.setOnClickListener(this);

        Button mediaplayer_test = (Button) findViewById(R.id.mediaplayer_test);
        mediaplayer_test.setOnClickListener(this);

        Button graphics_test = (Button) findViewById(R.id.graphics_test);
        graphics_test.setOnClickListener(this);

        for (int i = 0; i < urls.length; i++) {
            final int j = i;
            Glide.with(getApplicationContext()).load(urls[j]).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(356, 200);
        }
    }

    public static String[] urls =
            {"http://img2.100bt.com/upload/ttq/20130914/1379127831456_middle.gif",
                    "http://photocdn.sohu.com/20150514/mp14940941_1431571108911_9.gif"};

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
//            case R.id.ClassLoaderTest:
//                startActivity(ClassLoaderActivity.class);
//                break;
//            case R.id.gesture_test:
//                startActivity(GestureActivity.class);
//                break;
            case R.id.layout_test:
                startActivity(TransitionLayoutActivity.class);
                break;
//            case R.id.html5_test:
//                startActivity(Html5Test.class);
//                break;
//            case R.id.okhttp_test:
//                startActivity(OkhttpActivity.class);
//                break;
            case R.id.vector_drawable:
                startActivity(FancySearchBar.class);
                break;
            case R.id.download_progress:
                startActivity(FancyDownloadProgressBar.class);
                break;
            case R.id.mediaplayer_test:
                startActivity(GiftViewActivity.class);
                break;
            case R.id.graphics_test:
                startActivity(GraphicsTestActivity.class);
                break;
            default:
                break;
        }
    }

    private void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }
}