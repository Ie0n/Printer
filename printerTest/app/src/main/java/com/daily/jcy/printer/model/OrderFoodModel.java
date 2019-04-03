package com.daily.jcy.printer.model;

import android.util.Log;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.contract.OrderFoodContract;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.model.data.bean.Food_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class OrderFoodModel implements OrderFoodContract.Model {

    private List<Food> data;
    private String mResult;
    private Box<Food> foodBox;
    private List<Food> queryList;
    private static final String TAG = "OrderFoodModel-ll";
    private Food targetFood;
    private List<Food> targetFoodList;

    public OrderFoodModel() {
        foodBox = ObjectBox.getBoxStore().boxFor(Food.class);
        data = new ArrayList<>();
        queryList = new ArrayList<>();
    }

    @Override
    public List<Food> getFoodData() {
        data.clear();
        if (foodBox.getAll() != null) {
            data.addAll(foodBox.getAll());
            Log.i(TAG, "getFoodData: size: " + foodBox.getAll().size());
        }
        return data;
    }

    // 搜索
    @Override
    public List<Food> searchFoodDb(String s) {
        queryList.clear();
        targetFoodList = queryTargetList(s);
        if (targetFoodList != null) {
            queryList.addAll(targetFoodList);
        }
        return queryList;
    }

    @Override
    public String deleteFood(Food deleteFood) {
        getTargetFood(deleteFood.getUid());
        if (targetFood != null) {
            foodBox.remove(targetFood);
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    @Override
    public String putFood(Food food) {
        foodBox.put(food);
        Log.i(TAG, "putFood: " + "size：" + foodBox.count());
        return "添加成功";
    }

    @Override
    public String updateFood(Food oldFood, Food updateFood) {
        deleteFood(oldFood);
        putFood(updateFood);
        return "更新成功";
    }

    @Override
    public void getTargetFood(String uid) {
        targetFood = foodBox.query().equal(Food_.uid, uid).build().findUnique();
    }

    @Override
    public List<Food> queryTargetList(String input) {
        return foodBox.query().startsWith(Food_.uid, input, QueryBuilder.StringOrder.CASE_SENSITIVE).build().find();
    }

    // 添加份数
    @Override
    public Food addFoodCount(Food targetFood) {
        int count = targetFood.getNum();
        count++;
        targetFood.setNum(count);
        putFood(targetFood);
        return targetFood;
    }

    @Override
    public Food subFoodCount(Food targetFood) {
        int count = targetFood.getNum();
        count--;
        targetFood.setNum(count);
        putFood(targetFood);
        return targetFood;
    }

    @Override
    public void clearFoodCount() {
        List<Food> foodList = foodBox.getAll();
        if (foodList != null) {
            for (int i = 0; i < foodList.size(); i++) {
                foodList.get(i).setNum(0);
            }
            foodBox.put(foodList);
        }
    }

    @Override
    public String getResult() {
        return mResult;
    }

    @Override
    public void setResult(String result) {
        mResult = result;
    }
}
