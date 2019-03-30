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

    @Override
    public void SearchClient(Long id) {
        mModel.getClientID(id);
    }

    @Override
    public void finishSearch() {
        mView.finishSearch();
    }

//    @Override
//    public void showResult() {
//
//    }

    @Override

    public void showResult(String result) {
    }

    @Override
    public void showResult() {

    }

    @Override
    public OrderClientContract.Model createModel() {
        return new OrderClientModel();
    }
}
