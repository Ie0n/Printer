package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Login;
import com.daily.jcy.printer.model.data.bean.Mode;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

public interface SettingContract {
    interface Model extends BaseModel {

        void setPassword(Login login);

        void openPassword(Login login);

        void closePassword(Login login);

        String getPassword();

        boolean isOpen();
    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void setPassword(Login login);

        public abstract void openPassword(Login login);

        public abstract void closePassword(Login login);

        public abstract boolean isOpen();

        public abstract String getPassword();
    }

}
