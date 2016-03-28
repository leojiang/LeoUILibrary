package com.example.blurtest.imagefilter;

import android.graphics.Bitmap;
import android.graphics.Color;

public class EmbossFilter implements ImageFilterInterface {

    private ImageData image = null;

    public EmbossFilter(Bitmap bmp) {
        image = new ImageData(bmp);
    }

    public ImageData imageProcess() {
        int pixelsR, pixelsG, pixelsB, pixelsA, pixelsR2, pixelsG2, pixelsB2, pixelsA2;
        int color, color2;
        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = image.getColorArray();
        int[] newPixels = new int[width * height];

        for (int i = 1; i < height * width; i++) {
            color = pixels[i - 1];
            //前一个像素
            pixelsA = Color.alpha(color);
            pixelsR = Color.red(color);
            pixelsG = Color.green(color);
            pixelsB = Color.blue(color);
            //当前像素
            color2 = pixels[i];
            pixelsA2 = Color.alpha(color2);
            pixelsR2 = Color.red(color2);
            pixelsG2 = Color.green(color2);
            pixelsB2 = Color.blue(color2);

            pixelsA = (pixelsA - pixelsA2 + 127);
            pixelsR = (pixelsR - pixelsR2 + 127);
            pixelsG = (pixelsG - pixelsG2 + 127);
            pixelsB = (pixelsB - pixelsB2 + 127);
            //均小于等于255
            if (pixelsR > 255) {
                pixelsR = 255;
            }

            if (pixelsG > 255) {
                pixelsG = 255;
            }

            if (pixelsB > 255) {
                pixelsB = 255;
            }

            if (pixelsA > 255) {
                pixelsA = 255;
            }

            newPixels[i] = Color.argb(pixelsA, pixelsR, pixelsG, pixelsB);
        }

        image.setColorArray(newPixels);

        return image;
    }


}
