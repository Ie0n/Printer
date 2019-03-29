package com.daily.jcy.printer.model;

import com.daily.jcy.printer.contract.OrderFoodContract;
import com.daily.jcy.printer.model.data.bean.Food;

import java.util.ArrayList;
import java.util.List;

public class OrderFoodModel implements OrderFoodContract.Model {

    private List<Food> data;
    private String mResult;

    @Override
    public String getResult() {
        return mResult;
    }

    @Override
    public void setResult(String result) {
        mResult = result;
    }

    @Override
    public List<Food> getFoodData() {
        data = new ArrayList<>();
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        data.add(new Food("10001", "黄焖鸡米饭", "Braised chicken with rice", "$3.99", false));
        setResult("Success");
        return data;
    }
}
