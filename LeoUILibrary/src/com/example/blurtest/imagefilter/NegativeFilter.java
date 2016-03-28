package com.example.blurtest.imagefilter;

import android.graphics.Bitmap;
import android.graphics.Color;

public class NegativeFilter implements ImageFilterInterface {

    private ImageData image = null;

    public NegativeFilter(Bitmap bmp) {
        image = new ImageData(bmp);
    }

    public ImageData imageProcess() {
        int pixelsR, pixelsG, pixelsB, pixelsA;
        int color;
        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = image.getColorArray();

        for (int i = 1; i < height * width; i++) {
            color = pixels[i];
            //获取RGB分量
            pixelsA = Color.alpha(color);
            pixelsR = Color.red(color);
            pixelsG = Color.green(color);
            pixelsB = Color.blue(color);

            //转换
            pixelsR = (255 - pixelsR);
            pixelsG = (255 - pixelsG);
            pixelsB = (255 - pixelsB);
            //均小于等于255大于等于0
            if (pixelsR > 255) {
                pixelsR = 255;
            } else if (pixelsR < 0) {
                pixelsR = 0;
            }
            if (pixelsG > 255) {
                pixelsG = 255;
            } else if (pixelsG < 0) {
                pixelsG = 0;
            }
            if (pixelsB > 255) {
                pixelsB = 255;
            } else if (pixelsB < 0) {
                pixelsB = 0;
            }

            pixels[i] = Color.argb(pixelsA, pixelsR, pixelsG, pixelsB);
        }

        return image;
    }
}