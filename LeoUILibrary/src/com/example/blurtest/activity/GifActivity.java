package com.example.blurtest.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.blurtest.R;
import com.example.blurtest.view.MGifView;

import java.net.URL;

public class GifActivity extends Activity {

    MGifView gifView;
    ImageView glideGif;
    MGifView glideGif0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        struct();

        setContentView(R.layout.activity_gif);
//        TextView textView = (TextView) findViewById(R.id.text);
//        textView.setText(Html.fromHtml("<font size=\"3\" color=\"red\">呢称:</font>我说了一句话+<img src=\"http://p0.jmstatic.com/assets/cart.gif\" width=\"35\" height=\"35\">", imgGetter, null));

        gifView = (MGifView) findViewById(R.id.remote_gif);
        glideGif0 = (MGifView) findViewById(R.id.remote_gif1);
        glideGif = (ImageView) findViewById(R.id.glid_gif);

//        gifView.setFileResource(MainActivity.urls[0]);

        Glide.with(this).load(MainActivity.urls[0]).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(gifView);
        Glide.with(this).load(MainActivity.urls[0]).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(glideGif0);
        Glide.with(this).load(MainActivity.urls[0]).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(glideGif);

//        Glide.with(this).load(MainActivity.urls[0]).into(gifView);
//        Glide.with(this).load(MainActivity.urls[0]).into(glideGif0);
//        Glide.with(this).load(MainActivity.urls[1]).into(glideGif);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                Log.i("GifActivity", e.getMessage());
                return null;
            }
            drawable.setBounds(0, 0, 100, 100);
            return drawable;
        }
    };

    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                // .detectAll()
                // for
                // all
                // detectable
                // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }
}
