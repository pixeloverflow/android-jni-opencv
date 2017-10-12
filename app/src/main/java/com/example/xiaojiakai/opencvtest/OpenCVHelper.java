package com.example.xiaojiakai.opencvtest;

import android.graphics.Bitmap;
/**
 * Created by XiaoJiaKai on 28/03/2017.
 */

public class OpenCVHelper {
    static{
        System.loadLibrary("OpenCV");
    }

    public static native int[] gray(int[] buf, int w, int h);

    public static native int binary(int bitmap, int r);
}
