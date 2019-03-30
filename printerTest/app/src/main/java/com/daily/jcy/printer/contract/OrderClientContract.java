package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

import java.util.List;

public interface OrderClientContract {

    interface Model extends BaseModel {
        List<Client> getClientData(CharSequence s);
    }

    interface View extends BaseView {
        void updateClientListData(List<Client> data);
    }

    abstract class Presenter extends BasePresenter<OrderClientContract.View, OrderClientContract.Model> {
        public abstract void updateClientListData();

        public abstract void onTxtChange(CharSequence s);
    }

}
