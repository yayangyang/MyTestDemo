package com.yayangyang.imagecompresstestdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/11.
 */

public class ImageUtil {


    public static Bitmap getimage(Resources resources,int id, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig=Bitmap.Config.RGB_565;
        // 若要对图片进行压缩，必须先设置Option的inJustDecodeBounds为true才能进行Option的修改
        options.inJustDecodeBounds = true;
        // 第一次decodeFile是获取到options.outHeight和options.outWidth
        BitmapFactory.decodeResource(resources,id, options);
        // options.inSampleSize是图片的压缩比，例如原来大小是100*100，options.inSampleSize为1，则不变，
        // options.inSampleSize为2，则压缩成50*50
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 重新设置options.inJustDecodeBounds为false，不能修改option
        options.inJustDecodeBounds = false;
        // 根据options重新加载图片(默认使用AGB_8888解码)
        Bitmap src = BitmapFactory.decodeResource(resources,id, options);
        return src;
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((height / inSampleSize) > reqHeight
                    || (height / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
            Log.e("calculateInSampleSize","缩小了"+inSampleSize);
        }else{
            Log.e("calculateInSampleSize","没缩小"+inSampleSize);
        }
        return inSampleSize;
    }

}
