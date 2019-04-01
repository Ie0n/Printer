package com.daily.jcy.printer.contract;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.view.BaseView;

import java.util.List;

public interface OrderFoodContract {
    interface Model extends BaseModel {
        // 拉取所有数据
        List<Food> getFoodData();

        // 搜素
        List<Food> searchFoodDb(String s);

        // 删除
        String deleteFood(Food deleteFood);

        // 添加
        String putFood(Food food);

        // 更新
        String updateFood(Food oldFood, Food updateFood);

        // 通过uid找到对象
        void getTargetFood(String uid);

        List<Food> queryTargetList(String input);
    }

    interface View extends BaseView {
        void updateFoodListData(List<Food> data);

        void notifyUI(List<Food> data);

        void deleteResult(String result);
    }

    abstract class Presenter extends BasePresenter<OrderFoodContract.View, OrderFoodContract.Model> {
        public abstract void updateFoodListData();

        public abstract void searchFood(String input);

        public abstract void updateFood(Food oldFood, Food updateFood);

        public abstract void putFood(Food newFood);

        public abstract void deleteFood(Food deleteFood);
    }

}
