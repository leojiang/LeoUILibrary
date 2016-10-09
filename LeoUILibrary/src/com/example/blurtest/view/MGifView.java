package com.example.blurtest.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 * Movie不能使用网络图片的流，也就是不可以使用Movie.decodeStream
 */
public class MGifView extends ImageView {
    private long movieStart;
    private static ExecutorService pools = Executors.newCachedThreadPool();

    private Movie movie;

    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj == null) {
                        return;
                    }
                    setResource((byte[]) msg.obj);
                    break;

                default:
                    break;
            }
        }
    };


    public MGifView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public MGifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setResource(int id) {
        movie = Movie.decodeStream(getResources().openRawResource(id));
        refresh();
    }
//
//    public void setFileResource(final String filename) {
////        pools.execute(new Runnable() {
////            @Override
////            public void run() {
//                try {
//                    File file = new File(filename);
//                    FileInputStream fis = new FileInputStream(file);
//                    byte[] buffer = new byte[1024];
//                    while(fis.read(buffer) > 0) {
//                    }
//
//                    Log.i("leojiang", "buffer: " + buffer);
//
//                    handler.obtainMessage(1, buffer).sendToTarget();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////            }
////        });
//
//    }

    public void setResource(final String urlstr) {
        pools.execute(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(urlstr);
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        byte[] buffer = getByte(is);
                        handler.obtainMessage(1, buffer).sendToTarget();
//                        is.close();
                    } else {
                        Log.e("getResponseCode", connection.getResponseCode()
                                + ":");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setResource(byte[] data) {
        movie = Movie.decodeByteArray(data, 0, data.length);
        refresh();
    }

    private final void refresh() {
        reMeasure();
        invalidate();
    }

    private final void reMeasure() {
        if (movie != null) {
            measure(MeasureSpec.makeMeasureSpec(movie.width(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(movie.height(), MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LayoutParams lp = getLayoutParams();
        boolean isWidMat = lp.width == LayoutParams.MATCH_PARENT;
        boolean isHeiMat = lp.height == LayoutParams.MATCH_PARENT;
        boolean isWidWra = lp.width == LayoutParams.WRAP_CONTENT;
        boolean isHeiWra = lp.height == LayoutParams.WRAP_CONTENT;
        if (isWidMat && isHeiMat) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        if (movie != null) {
            setMeasuredDimension(isWidMat ? MeasureSpec.getSize(widthMeasureSpec) : isWidWra ? movie.width() : lp.width,
                    isHeiMat ? MeasureSpec.getSize(heightMeasureSpec) : isHeiWra ? movie.height() : lp.height);
            requestLayout();
        } else {
            setMeasuredDimension(isWidMat ? MeasureSpec.getSize(widthMeasureSpec) : isWidWra ? 0 : lp.width,
                    isHeiMat ? MeasureSpec.getSize(heightMeasureSpec) : isHeiWra ? 0 : lp.height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (movie != null) {
            long curTime = android.os.SystemClock.uptimeMillis();
            if (movieStart == 0) {
                movieStart = curTime;
            }
            int duraction = movie.duration();
            if (duraction == 0) {
                duraction = 1000;
            }
            int relTime = (int) ((curTime - movieStart) % duraction);
            movie.setTime(relTime);
            movie.draw(canvas, 0, 0);
            if ((curTime - movieStart) >= duraction) {
                movieStart = 0;
            }
            // 强制重绘
            invalidate();
        } else {
            super.onDraw(canvas);
        }

    }

    private final static byte[] getByte(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
//            buffer = outputStream.toByteArray();
//            outputStream.flush();
//            outputStream.close();
//            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
