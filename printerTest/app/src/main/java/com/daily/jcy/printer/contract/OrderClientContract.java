package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

import java.util.List;

public interface OrderClientContract {

    interface Model extends BaseModel {
        // 拉取所有数据
        List<Client> getClientData(CharSequence s);

        // 搜索
        List<Client> searchClientDb(CharSequence s);

        // 删除
        String deleteClient(long deleteId);

        // 添加
        String putClient(Client client);
    }

    interface View extends BaseView {
        // 拉取所有数据
        void updateClientListData(List<Client> data);
        void notifyUi(List<Client>data);

        void deleteResult(String result);
    }

    abstract class Presenter extends BasePresenter<OrderClientContract.View, OrderClientContract.Model> {
        // 拉取所有数据
        public abstract void updateClientListData();

        // 搜索
        public abstract void searchClient(CharSequence s);

        // 存储
        public abstract String putClient(Client client);

        // 删除
        public abstract void deleteClient(long deleteId);

    }

}
