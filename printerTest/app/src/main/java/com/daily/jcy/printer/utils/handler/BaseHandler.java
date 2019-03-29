package com.daily.jcy.printer.utils.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.daily.jcy.printer.model.BaseModel;

import java.lang.ref.WeakReference;

public class BaseHandler extends Handler {


    public static final int RESULT_ERROR = 10001;
    private static final String MSG_ERROR = "未知错误";
    public static final int RESULT_SUCCESS = 10002;
    private static final String MSG_SUCCESS = "Success";
    private static final String TAG = "BaseHandler-zz";
    private WeakReference<BaseModel> weakReference;

    public BaseHandler(BaseModel model) {
        this.weakReference = new WeakReference<>(model);
    }

    @Override
    public void handleMessage(Message msg) {
        BaseModel model = weakReference.get();
        Log.i(TAG, "handleMessage: ");
        switch (msg.what) {
            case RESULT_ERROR:
                model.setResult(MSG_ERROR);
                break;
            case RESULT_SUCCESS:
                model.setResult(MSG_SUCCESS);
                break;

        }
        super.handleMessage(msg);


    }
}
