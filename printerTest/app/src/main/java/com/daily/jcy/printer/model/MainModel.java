package com.daily.jcy.printer.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.daily.jcy.printer.contract.MainContract;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.utils.handler.BaseHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainModel implements MainContract.Model {

    private static final String TAG = "MainModel-zz";
    private List<Order> data;
    private String result;
    private BaseHandler mHandler = new BaseHandler(this);

    public MainModel() {

    }

    @Override
    public List<Order> getOrderListData() {
        Log.i(TAG, "getOrderListData: ");
        data = new ArrayList<>();
        mHandler.sendEmptyMessage(BaseHandler.RESULT_SUCCESS);
        return data;
    }


    @Override
    public void setResult(String result) {
        Log.i(TAG, "setResult: ");
        this.result = result;
    }

    @Override
    public String getResult() {
        Log.i(TAG, "getResult: " + result);
        // 返回信息
        return result;
    }

}
