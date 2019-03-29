package com.daily.jcy.printer;

import android.content.Context;
import android.util.Log;

import com.daily.jcy.printer.model.data.bean.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context){
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
        if (BuildConfig.DEBUG){
            new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
        }
        Log.d("App","Using ObjectBox" + BoxStore.getVersion()+"("+BoxStore.getVersionNative()+")");
    }
    public static BoxStore getBoxStore(){
        return boxStore;
    }
}
