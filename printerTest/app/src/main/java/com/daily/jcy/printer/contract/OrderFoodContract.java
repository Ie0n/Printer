package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

import java.util.List;

public interface OrderFoodContract {
    interface Model extends BaseModel {
        List<Food> getFoodData();
    }

    interface View extends BaseView {
        void updateFoodListData(List<Food> data);
    }

    abstract class Presenter extends BasePresenter<OrderFoodContract.View, OrderFoodContract.Model> {
        public abstract void updateFoodListData();
    }

}
