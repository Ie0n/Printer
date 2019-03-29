package com.daily.jcy.printer.model;

<<<<<<< HEAD
=======
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
>>>>>>> 2ced59842afddb468f74fd71e2a07d21bf2bdcda
import android.util.Log;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.contract.MainContract;
import com.daily.jcy.printer.model.data.bean.Order;
<<<<<<< HEAD
=======
import com.daily.jcy.printer.presenter.MainPresenter;
import com.daily.jcy.printer.utils.handler.BaseHandler;
>>>>>>> 2ced59842afddb468f74fd71e2a07d21bf2bdcda

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class MainModel implements MainContract.Model {

    private static final String TAG = "MainModel-zz";
    private List<Order> data;
    private String result;
<<<<<<< HEAD
    private Box<Order>orderBox;
=======
    private BaseHandler mHandler;
    private MainPresenter presenter;
>>>>>>> 2ced59842afddb468f74fd71e2a07d21bf2bdcda

    public MainModel() {
//        mHandler = new BaseHandler(this, Looper.getMainLooper());
//        presenter = MainPresenter.getInstance();
    }

    @Override
    public List<Order> getOrderListData() {
        Log.i(TAG, "getOrderListData: ");
        orderBox = ObjectBox.getBoxStore().boxFor(Order.class);
        data = new ArrayList<>();
<<<<<<< HEAD
        data.add(new Order(0L,"2019.3.11 11:30","1660元"));
        data.add(new Order(1L,"2019.3.11 11:30","1660元"));
        data.add(new Order(2L,"2019.3.11 11:30","1660元"));
        data.add(new Order(3L,"2019.3.11 11:30","1660元"));
        data.add(new Order(4L,"2019.3.11 11:30","1660元"));
        data.add(new Order(5L,"2019.3.11 11:30","1660元"));
        data.add(new Order(6L,"2019.3.11 11:30","1660元"));
        data.add(new Order(7L,"2019.3.11 11:30","1660元"));
        data.add(new Order(8L,"2019.3.11 11:30","1660元"));
        data.add(new Order(9L,"2019.3.11 11:30","1660元"));
        orderBox.put(data);
=======
//        mHandler.sendEmptyMessage(BaseHandler.RESULT_SUCCESS);
        setResult("Success");
//        presenter.showResult(getResult());
>>>>>>> 2ced59842afddb468f74fd71e2a07d21bf2bdcda
        return data;
    }

    @Override
    public boolean deleteOrderListData() {
        orderBox.remove(data);
        data = null;
        return true;
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
