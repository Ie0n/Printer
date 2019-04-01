package com.daily.jcy.printer;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.objectbox.android.AndroidObjectBrowser;

public class MyApplication extends Application {
    ExecutorService cachedThreadPool = null;

    public ExecutorService getCachedThreadPool() {
        return cachedThreadPool;
    }


    private Handler handler = new Handler();

    public Handler getHandler() {
        return handler;
    }

    static MyApplication instance = null;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ObjectBox.init(this);
        cachedThreadPool = Executors.newCachedThreadPool();
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(ObjectBox.getBoxStore()).start(this);
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

}

