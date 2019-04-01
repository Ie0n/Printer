package com.daily.jcy.printer.presenter;

import android.util.Log;

import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.OrderClientModel;
import com.daily.jcy.printer.model.data.bean.Client;

public class OrderClientPresenter extends OrderClientContract.Presenter {


    private OrderClientContract.View mView;
    private OrderClientContract.Model mModel;
    private static final String TAG = "OrderClientPresenter-ff";



    @Override
    public void attachView(OrderClientContract.View view) {
        super.attachView(view);
        mView = view;
        mModel = createModel();
    }

    // 初始化数据
    @Override
    public void updateClientListData() {
        mView.updateClientListData(mModel.getClientData(null));
        showResult();
    }


    // 搜索
    @Override
    public void searchClient(CharSequence s) {
        Log.i(TAG, "searchClient: ");
        mView.notifyUi(mModel.searchClientDb(s));
        showResult();
    }


    @Override
    public void showResult(String result) {
    }

    @Override
    public void showResult() {}

    // 存储
    @Override
    public String putClient(Client client) {
        return mModel.putClient(client);
    }

    // 删除
    @Override
    public void deleteClient(long deleteId) {
        mView.deleteResult(mModel.deleteClient(deleteId));
    }

    // 更新
    @Override
    public void updateClient(Client clickClient, Client updateClient) {
        mModel.updateClient(clickClient,updateClient);
    }


    @Override
    public OrderClientContract.Model createModel() {
        return new OrderClientModel();
    }
}
