package com.daily.jcy.printer.presenter;

import com.daily.jcy.printer.contract.OrderFoodContract;
import com.daily.jcy.printer.model.OrderFoodModel;

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
    public void showResult() {
        mView.showResult(mModel.getResult());
    }

    @Override
    public OrderFoodContract.Model createModel() {
        return new OrderFoodModel();
    }

    @Override
    public void updateFoodListData() {
        mView.updateFoodListData(mModel.getFoodData());
    }


}
