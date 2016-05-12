package com.example.blurtest.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blurtest.R;
import com.example.blurtest.gif.GifHelper;
import com.example.blurtest.gif.GifHelper.GifFrame;

import java.io.FileInputStream;

public class GifActivity extends Activity {

    private PlayGifTask mGifTask;
    ImageView iv;
    GifHelper.GifFrame[] frames;
    FileInputStream fis = null;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        mTextView = (TextView) findViewById(R.id.text);
        mTextView.requestFocus();

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://httpbin.org/delay/1") // This URL is served with a 1 second delay.
//                .build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            response.newBuilder()
//                    .header("Cache-Control", "max-age=60")
//                    .build();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        iv = new ImageView(this);
//        iv.setScaleType(ImageView.ScaleType.CENTER);
//        setContentView(iv, new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
//                LayoutParams.FILL_PARENT));
//
//        final InputStream fis = getResources().openRawResource(R.raw.anim1);
//
//
//        frames = CommonUtil.getGif(fis);
//        mGifTask = new PlayGifTask(iv, frames);
//        mGifTask.startTask();
//        Thread th = new Thread(mGifTask);
//        th.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mGifTask) mGifTask.stopTask();

    }

    //用来循环播放Gif每帧图片
    private class PlayGifTask implements Runnable {
        int i = 0;
        ImageView iv;
        GifFrame[] frames;
        int framelen, oncePlayTime = 0;

        public PlayGifTask(ImageView iv, GifFrame[] frames) {
            this.iv = iv;
            this.frames = frames;

            int n = 0;
            framelen = frames.length;
            while (n < framelen) {
                oncePlayTime += frames[n].delay;
                n++;
            }

        }

        Handler h2 = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        iv.setImageBitmap((Bitmap) msg.obj);
                        break;
                }
            }

            ;
        };

        @Override
        public void run() {
            if (!frames[i].image.isRecycled()) {
                //      iv.setImageBitmap(frames[i].image);
                Message m = Message.obtain(h2, 1, frames[i].image);
                m.sendToTarget();
            }
            iv.postDelayed(this, frames[i++].delay);
            i %= framelen;
        }

        public void startTask() {
            iv.post(this);
        }

        public void stopTask() {
            if (null != iv) iv.removeCallbacks(this);
            iv = null;
            if (null != frames) {
                for (GifHelper.GifFrame frame : frames) {
                    if (frame.image != null && !frame.image.isRecycled()) {
                        frame.image.recycle();
                        frame.image = null;
                    }
                }
                frames = null;
                //      mGifTask=null;
            }
        }
    }
}
