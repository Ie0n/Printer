package com.daily.jcy.printer.model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.daily.jcy.printer.contract.MainContract;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.presenter.MainPresenter;
import com.daily.jcy.printer.utils.handler.BaseHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainModel implements MainContract.Model {

    private static final String TAG = "MainModel-zz";
    private List<Order> data;
    private String result;
    private BaseHandler mHandler;
    private MainPresenter presenter;

    public MainModel() {
//        mHandler = new BaseHandler(this, Looper.getMainLooper());
//        presenter = MainPresenter.getInstance();
    }

    @Override
    public List<Order> getOrderListData() {
        Log.i(TAG, "getOrderListData: ");
        data = new ArrayList<>();
//        mHandler.sendEmptyMessage(BaseHandler.RESULT_SUCCESS);
        setResult("Success");
//        presenter.showResult(getResult());
        return data;
    }


    @Override
    public void setResult(String result) {
        Log.i(TAG, "setResult: " + result);
        this.result = result;
    }

    @Override
    public String getResult() {
        Log.i(TAG, "getResult: " + result);
        // 返回信息
        return result;
    }

}
