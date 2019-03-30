package com.daily.jcy.printer.utils;

import android.util.Log;

public class LogUtils {
    public synchronized static void log(String TAG,String content) {
        Log.i(TAG,  content);
    }
}
