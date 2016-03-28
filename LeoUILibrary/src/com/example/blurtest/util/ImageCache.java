package com.example.blurtest.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageCache {

    private static HashMap<String, Bitmap> imageCache = new HashMap<String, Bitmap>();

    public static void put(String key, Bitmap bmp) {
        imageCache.put(key, bmp);
    }

    public static Bitmap get(String key) {
        return imageCache.get(key);
    }

    public static void clear() {
        try{
            Set entrySet = imageCache.keySet();
            Iterator<String> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                Bitmap bitmap = imageCache.remove(key);
                bitmap.recycle();
                bitmap = null;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        imageCache.clear();
    }
}
