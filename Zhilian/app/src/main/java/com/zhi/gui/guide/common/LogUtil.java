package com.zhi.gui.guide.common;

import android.util.Log;

public class LogUtil {

    public static void d(String tag, String msg) {
        if (Constants.IS_DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (Constants.IS_DEBUG)
            Log.i(tag, tag + msg);
    }

    public static void w(String tag, String msg) {
        if (Constants.IS_DEBUG)
            Log.w(tag, tag + msg);
    }

    public static void e(String tag, String msg) {
        if (Constants.IS_DEBUG)
            Log.e(tag, tag + msg);
    }

}
