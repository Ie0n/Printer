package com.daily.jcy.printer.utils;

import android.util.Log;

public class LogUtils {
    public static final String TEST_DB = "测试数据库";
    public synchronized static void log(String TAG,String content) {
        Log.i(TAG,  content);
    }
}
