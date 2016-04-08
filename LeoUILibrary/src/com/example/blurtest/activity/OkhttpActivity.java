package com.example.blurtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.blurtest.R;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

public class OkhttpActivity extends Activity implements View.OnClickListener{
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        Button button = (Button) findViewById(R.id.http_test);
        button.setOnClickListener(this);

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.text);

        try {
            init(new File(Environment.getExternalStorageDirectory(), "HttpCache"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.clear:
                mTextView.setText("");
                break;
            case R.id.http_test:
                testOkhttp();
                break;
        }


    }

    private OkHttpClient client;

    public void init(File cacheDirectory) throws Exception {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        if(!cacheDirectory.exists()) {
            cacheDirectory.mkdir();
        }
        Cache cache = new Cache(cacheDirectory, cacheSize);
        client = new OkHttpClient();
        client.setCache(cache);
//        client.interceptors().add(new LoggingInterceptor());
    }


    public void testOkhttp(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Request request = new Request.Builder()
//                            .url("http://www.baidu.com/")
//                            .header("Cache-Control", "no-cache")
                            .header("Cache-Control", "max-stale=10")
                            .url("http://publicobject.com/helloworld.txt")
                            .build();

                    Response response1 = client.newCall(request).execute();
                    if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

                    String response1Body = response1.body().string();
                    Log.i("leojiang", "Response 1 response:          " + response1);
                    Log.i("leojiang", "Response 1 cache response:    " + response1.cacheResponse());
                    Log.i("leojiang", "Response 1 network response:  " + response1.networkResponse());
//                    mTextView.setText("Response 1 response:" + response1);
//                    mTextView.append("Response 1 cache response:" + response1.cacheResponse());
//                    mTextView.append("Response 1 network response:" + response1.networkResponse());
//                    mTextView.append("\n");

                    Response response2 = client.newCall(request).execute();
                    if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

                    String response2Body = response2.body().string();
                    Log.i("leojiang", "Response 2 response:          " + response2);
                    Log.i("leojiang", "Response 2 cache response:    " + response2.cacheResponse());
                    Log.i("leojiang", "Response 2 network response:  " + response2.networkResponse());

                    Log.i("leojiang","Response 2 equals Response 1? " + response1Body.equals(response2Body));

//                    mTextView.setText("Response 2 response:" + response2);
//                    mTextView.append("Response 2 cache response:" + response2.cacheResponse());
//                    mTextView.append("Response 2 network response:" + response2.networkResponse());
//                    mTextView.append("Response 2 equals Response 1? " + response1Body.equals(response2Body));
//                    mTextView.append("\n");

                    sleep(15000);
                    Response response3 = client.newCall(request).execute();
                    if (!response3.isSuccessful()) throw new IOException("Unexpected code " + response3);

                    String response3Body = response3.body().string();
                    Log.i("leojiang", "Response 3 response:          " + response3);
                    Log.i("leojiang", "Response 3 cache response:    " + response3.cacheResponse());
                    Log.i("leojiang", "Response 3 network response:  " + response3.networkResponse());
                    Log.i("leojiang", "Response 3 equals Response 1? " + response1Body.equals(response3Body));

//                    mTextView.setText("Response 3 response:" + response3);
//                    mTextView.append("Response 3 cache response:" + response3.cacheResponse());
//                    mTextView.append("Response 3 network response:" + response3.networkResponse());
//                    mTextView.append("Response 3 equals Response 1? " + response1Body.equals(response3Body));
//                    mTextView.append("\n");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request.newBuilder()
                    .header("Cache-Control", "max-stale=100")
                    .build();
            long t1 = System.nanoTime();
            Log.i("leojiang", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i("leojiang", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));



            return response;
        }
    }

}
