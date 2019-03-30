package com.daily.jcy.printer;

import android.app.Application;
import android.util.Log;

import io.objectbox.android.AndroidObjectBrowser;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(ObjectBox.getBoxStore()).start(this);
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

}

