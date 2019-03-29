package com.daily.jcy.printer.presenter;

import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.OrderClientModel;

public class OrderClientPresenter extends OrderClientContract.Presenter {


    private OrderClientContract.View mView;
    private OrderClientContract.Model mModel;

    @Override
    public void attachView(OrderClientContract.View view) {
        super.attachView(view);
        mView = view;
        mModel = createModel();
    }

    @Override
    public void updateClientListData() {
        mView.updateClientListData(mModel.getClientData());
    }

//    @Override
//    public void showResult() {
//
//    }

    @Override
<<<<<<< HEAD
    public void showResult(String result) {

=======
    public void showResult() {
        mView.showResult(mModel.getResult());
>>>>>>> 2ced59842afddb468f74fd71e2a07d21bf2bdcda
    }

    @Override
    public OrderClientContract.Model createModel() {
        return new OrderClientModel();
    }
}
