package com.daily.jcy.printer;

import android.app.Application;
import android.util.Log;

import com.daily.jcy.printer.model.data.bean.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }

}

