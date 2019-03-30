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
    private BaseHandler mHandler;

>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055
//    private static MainPresenter presenter;

//    public static MainPresenter getInstance() {
//        if (presenter == null) {
//            presenter = new MainPresenter();
//            return presenter;
//        }
//        return presenter;
//    }
<<<<<<< HEAD

=======
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055

    // 绑定View
    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        mView = view;
        mModel = (MainModel) createModel();
<<<<<<< HEAD
=======
//        mHandler = new BaseHandler(mModel);
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055
    }

    // 解绑View
    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void showResult() {
        mView.showResult(mModel.getResult());
    }

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
