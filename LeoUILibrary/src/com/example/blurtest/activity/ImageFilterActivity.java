package com.example.blurtest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.blurtest.R;
import com.example.blurtest.imagefilter.*;
import com.example.blurtest.util.ImageCache;
import com.example.blurtest.util.ImageUtil;

public class ImageFilterActivity extends Activity {

    ImageView imageView1, imageView2;
    Bitmap mBitmap;
    Bitmap tmpBitmap;

    Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);
        mContext = this;
        findView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int pos = bundle.getInt("position", 0);

        switch (pos) {
            case 0:
                if (getImageFromCache("IceFilter")) {
                    break;
                }
                doFilterWork("IceFilter", new IceFilter(mBitmap));
                break;
            case 1:
                if (getImageFromCache("MoltenFilter")) {
                    break;
                }
                doFilterWork("MoltenFilter", new MoltenFilter(mBitmap));
                break;
            case 2:
                if (getImageFromCache("ComicFilter")) {
                    break;
                }
                doFilterWork("ComicFilter", new ComicFilter(mBitmap));
                break;
            case 3:
                if (getImageFromCache("SoftGlowFilter")) {
                    break;
                }
                doFilterWork("SoftGlowFilter", new SoftGlowFilter(mBitmap, 10, 0.1f, 0.1f));
                break;
            case 4:
                if (getImageFromCache("GlowingEdgeFilter")) {
                    break;
                }
                doFilterWork("GlowingEdgeFilter", new GlowingEdgeFilter(mBitmap));
                break;
            case 5:
                if (getImageFromCache("FeatherFilter")) {
                    break;
                }
                doFilterWork("FeatherFilter", new FeatherFilter(mBitmap));
                break;
            case 6:
                if (getImageFromCache("BlurFiter")) {
                    break;
                }
                doFilterWork("BlurFiter", new BlurFilter(mBitmap));
                break;
            case 7:
                if (getImageFromCache("ReflectionFilter")) {
                    break;
                }
                tmpBitmap = new ReflectionFilter(mBitmap).imageProcess();
                imageView2.setImageBitmap(tmpBitmap);
                ImageCache.put("ReflectionFilter", tmpBitmap);
                break;
            default:
                imageView2.setImageBitmap(mBitmap);
                break;
        }
    }

    private boolean getImageFromCache(String key) {
        if (ImageCache.get(key) != null) {
            tmpBitmap = ImageCache.get(key);
            imageView2.setImageBitmap(tmpBitmap);
            return true;
        }
        return false;
    }

    private void doFilterWork(String key, ImageFilterInterface filter) {
        tmpBitmap = filter.imageProcess().getDstBitmap();
        imageView2.setImageBitmap(tmpBitmap);
        ImageCache.put(key, tmpBitmap);
    }

    private void findView() {
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        mBitmap = ImageUtil.readBitMap(mContext, R.drawable.image);
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                imageView1.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
        super.onDestroy();
    }
}
