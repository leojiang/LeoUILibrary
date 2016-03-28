package com.example.blurtest.imagefilter;

import android.graphics.Matrix;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;


public class ReflectionFilter {

    private Bitmap originalImage = null;

    public ReflectionFilter(Bitmap bmp) {
        originalImage = bmp;
    }

    public Bitmap imageProcess() {
        // 获得图片的长宽
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1); // 实现图片的反转
        Bitmap upsideDownImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, false); // 创建反转后的图片Bitmap对象，图片高是原图的一半
        Bitmap reflection = Bitmap.createBitmap(width, height, Config.ARGB_8888); // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍

        Canvas canvas = new Canvas(reflection);
        canvas.drawBitmap(upsideDownImage, 0, 0, null); // 将反转后的图片画到画布中

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, height,
                0x70ffffff, 0x00ffffff, TileMode.MIRROR);// 创建线性渐变LinearGradient对象
        paint.setShader(shader); // 绘制
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));//倒影遮罩效果
        canvas.drawRect(0, 0, width, height, paint); // 画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果

        upsideDownImage.recycle();
        upsideDownImage = null;

        return reflection;
    }

}
