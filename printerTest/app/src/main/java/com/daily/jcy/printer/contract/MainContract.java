package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

import java.util.List;

public interface MainContract {

    interface Model extends BaseModel {
        List<Order> getOrderListData();

        void clearOrderList();
    }

    interface View extends BaseView {
        void updateOrderListData(List<Order> data);

    }

    abstract class Presenter extends BasePresenter<MainContract.View, MainContract.Model> {
        public abstract void updateOrderListData();

        public abstract void clearOrderList();
    }

}
