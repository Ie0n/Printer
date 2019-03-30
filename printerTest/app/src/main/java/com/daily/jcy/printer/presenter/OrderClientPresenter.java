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
        mView.updateClientListData(mModel.getClientData(null));
        showResult();
    }

    @Override
    public void onTxtChange(CharSequence s) {
        mView.updateClientListData(mModel.getClientData(s));
        showResult();
    }

//    @Override
//    public void showResult() {
//
//    }


    public void showResult() {
        mView.showResult(mModel.getResult());
    }

    @Override
    public OrderClientContract.Model createModel() {
        return new OrderClientModel();
    }
}
