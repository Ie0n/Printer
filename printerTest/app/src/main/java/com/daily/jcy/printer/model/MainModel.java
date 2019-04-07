package com.daily.jcy.printer.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.contract.MainContract;
import com.daily.jcy.printer.model.data.bean.Count;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.view.activity.PrinterActivity;


import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class MainModel implements MainContract.Model {

    private static final String TAG = "MainModel-zz";
    private List<Order> data;
    private Box<Count> countBox;
    private String result;
    private Box<Order>orderBox;

    public MainModel() {
        data = new ArrayList<>();
        orderBox = ObjectBox.getBoxStore().boxFor(Order.class);
        countBox = ObjectBox.getBoxStore().boxFor(Count.class);
    }

    @Override
    public List<Order> getOrderListData() {
        data = orderBox.getAll();
        return data;
    }


    @Override
    public void clearOrderList() {
        orderBox.removeAll();
        countBox.removeAll();

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
