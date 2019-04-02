package com.daily.jcy.printer.presenter;

import com.daily.jcy.printer.contract.OrderFoodContract;
import com.daily.jcy.printer.model.OrderFoodModel;
import com.daily.jcy.printer.model.data.bean.Food;

public class OrderFoodPresenter extends OrderFoodContract.Presenter {

    private OrderFoodContract.View mView;
    private OrderFoodContract.Model mModel;

    @Override
    public void attachView(OrderFoodContract.View view) {
        super.attachView(view);
        mView = view;
        mModel = createModel();
    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showResult() {
        mView.showResult(mModel.getResult());
    }

    @Override
    public OrderFoodContract.Model createModel() {
        return new OrderFoodModel();
    }

    // 拉取全部数据
    @Override
    public void updateFoodListData() {
        mView.updateFoodListData(mModel.getFoodData());
    }

    // 搜索
    @Override
    public void searchFood(String input) {
        mView.notifyUI(mModel.searchFoodDb(input));
        showResult();
    }

    // 更新数据
    @Override
    public void updateFood(Food oldFood, Food updateFood) {
        mModel.updateFood(oldFood, updateFood);
    }

    // 添加
    @Override
    public void putFood(Food newFood) {
        mModel.putFood(newFood);
    }

    // 删除
    @Override
    public void deleteFood(Food deleteFood) {
        mView.deleteResult(mModel.deleteFood(deleteFood));
    }

    @Override
    public void addFoodCount(Food targetFood) {
        mView.addTargetList(mModel.addFoodCount(targetFood));
    }

    @Override
    public void subFoodCount(Food targetFood) {
        mView.subTargetList(mModel.subFoodCount(targetFood));
    }

    @Override
    public void clearFoodCount() {
        mModel.clearFoodCount();
    }
}
