package com.nanshan.support.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiangbo on 2018/8/23.
 * 控制图片大小的工具类
 */

public class DrawableUtils {

    //根据宽高比例对图片进行缩放
    public static Drawable getDrawable(Context context, int resId, float newWidth) {
        return getDrawable(context, resId, newWidth, -1f);
    }

    /**
     * 对图片进行缩放
     */
    public static Drawable getDrawable(Context context, int resId,
                                       float newWidth, float newHeigth) {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleHeight;
        if (newHeigth == -1) {
            //高和宽的比列
            float ratio = (float) height / width;
            scaleHeight = newWidth * ratio / height;
        } else {
            scaleHeight = newHeigth / height;
        }
        float scaleWidht = newWidth / width;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        return new BitmapDrawable(context.getResources(), newbm);
    }
}
