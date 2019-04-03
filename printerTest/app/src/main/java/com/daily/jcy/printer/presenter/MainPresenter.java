package com.daily.jcy.printer.presenter;


import android.os.Message;
import android.util.Log;

import com.daily.jcy.printer.contract.MainContract;
import com.daily.jcy.printer.model.MainModel;
import com.daily.jcy.printer.utils.handler.BaseHandler;

public class MainPresenter extends MainContract.Presenter {

    private MainContract.View mView;
    private MainModel mModel;
    private static final String TAG = "MainPresenter-zz";

    private BaseHandler mHandler;


    // 绑定View
    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        mView = view;
        mModel = (MainModel) createModel();
    }

    // 解绑View
    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void showResult(String result) {

    }

//    @Override
//    public void showResult() {
//        mView.showResult(mModel.getResult());
//    }

    @Override
    public void showResult() {

    }

    // 创建Model
    @Override
    public MainContract.Model createModel() {
        return new MainModel();
    }

    @Override
    public void updateOrderListData() {
        Log.i(TAG, "updateOrderListData: ");
        mView.updateOrderListData(mModel.getOrderListData());
    }


    @Override
    public void clearOrderList() {
        mModel.clearOrderList();
    }

}