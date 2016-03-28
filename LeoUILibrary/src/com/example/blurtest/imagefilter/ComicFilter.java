package com.example.blurtest.imagefilter;


import android.graphics.Bitmap;

import com.example.blurtest.util.ImageUtil;

public class ComicFilter implements ImageFilterInterface {

    private ImageData image = null;

    public ComicFilter(Bitmap bmp) {
        image = new ImageData(bmp);
    }

    public ImageData imageProcess() {
        int width = image.getWidth();
        int height = image.getHeight();
        int R, G, B, pixel;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                R = image.getRComponent(x, y);
                G = image.getGComponent(x, y);
                B = image.getBComponent(x, y);

                pixel = G - B + G + R;
                if (pixel < 0)
                    pixel = -pixel;
                pixel = pixel * R / 256;
                if (pixel > 255)
                    pixel = 255;
                R = pixel;

                pixel = B - G + B + R;
                if (pixel < 0)
                    pixel = -pixel;
                pixel = pixel * R / 256;
                if (pixel > 255)
                    pixel = 255;
                G = pixel;

                pixel = B - G + B + R;
                if (pixel < 0)
                    pixel = -pixel;
                pixel = pixel * G / 256;
                if (pixel > 255)
                    pixel = 255;
                B = pixel;
                image.setPixelColor(x, y, R, G, B);
            }
        }
        Bitmap bitmap = image.getDstBitmap();
        bitmap = ImageUtil.toGrayscale(bitmap);
        image = new ImageData(bitmap);
        return image;
    }

}
