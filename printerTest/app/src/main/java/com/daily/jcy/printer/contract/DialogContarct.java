package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Mode;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

public interface DialogContarct {
    interface Model extends BaseModel {
        boolean checkFoodNumber(String foodId);

        boolean checkClientNumber(String clientId);
    }

    interface View extends BaseView {
        boolean confirmationNumber(boolean flag);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract boolean checkFoodNumber(String id);

        public abstract boolean checkClientNumber(String id);
    }

}
