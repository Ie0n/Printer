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
<<<<<<< HEAD
    private BaseHandler mHandler;

=======
//    private static MainPresenter presenter;

//    public static MainPresenter getInstance() {
//        if (presenter == null) {
//            presenter = new MainPresenter();
//            return presenter;
//        }
//        return presenter;
//    }
>>>>>>> 2ced59842afddb468f74fd71e2a07d21bf2bdcda

    // 绑定View
    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        mView = view;
        mModel = (MainModel) createModel();
        mHandler = new BaseHandler(mModel);
    }

    // 解绑View
    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void showResult(String result) {
        mView.showResult(result);
    }

    // 创建Model
    @Override
    public MainContract.Model createModel() {
        return new MainModel();
    }

    @Override
    public void updateOrderListData() {
        Log.i(TAG, "updateOrderListData: ");
//        mHandler.sendEmptyMessage(BaseHandler.RESULT_SUCCESS);
//        Message message = Message.obtain();
//        message.what = BaseHandler.RESULT_SUCCESS;
//        mHandler.handleMessage(message);
        mView.updateOrderListData(mModel.getOrderListData());
    }

    @Override
    public void deleteOrderListData() {
        mView.deleteOrderListData(mModel.deleteOrderListData());
    }

//    @Override
//    public void showResult() {
//        Log.i(TAG, "showResult: ");
//        mView.showResult(mModel.getResult());
//    }
}
