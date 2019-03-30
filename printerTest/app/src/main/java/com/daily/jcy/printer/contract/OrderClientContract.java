package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

import java.util.List;

public interface OrderClientContract {

    interface Model extends BaseModel {
<<<<<<< HEAD
        List<Client> getClientData();
        Client getClientID(Long id);
=======
        List<Client> getClientData(CharSequence s);
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055
    }

    interface View extends BaseView {
        void updateClientListData(List<Client> data);
        void finishSearch();
        void notifyUi(List<Client>data);
    }

    abstract class Presenter extends BasePresenter<OrderClientContract.View, OrderClientContract.Model> {
        public abstract void updateClientListData();
<<<<<<< HEAD
        public abstract void SearchClient(Long id);
        public abstract void finishSearch();
=======

        public abstract void onTxtChange(CharSequence s);
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055
    }

}
