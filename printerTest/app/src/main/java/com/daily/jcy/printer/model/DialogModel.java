package com.daily.jcy.printer.model;

import android.util.Log;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.contract.DialogContarct;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Client_;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.model.data.bean.Food_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class DialogModel implements DialogContarct.Model {

    private Box<Food> foodBox;
    private Box<Client> clientBox;
    private static final String TAG = "DialogModel-uu";

    public DialogModel() {
        foodBox = ObjectBox.getBoxStore().boxFor(Food.class);
        clientBox = ObjectBox.getBoxStore().boxFor(Client.class);
    }

    /**
     * 为了防止创建重复的ID
     *
     * @param foodId 输入框的字符id
     * @return 返回数据库中是否存在该id,存在就返回true，否则返回false
     */
    @Override
    public boolean checkFoodNumber(String foodId) {
        List<Food> food = foodBox.query().equal(Food_.uid, foodId, QueryBuilder.StringOrder.CASE_SENSITIVE).build().find();
        Log.i(TAG, "checkFoodNumber: " + food.size());
        return food.size() != 0;
    }

    @Override
    public boolean checkClientNumber(String clientId) {
        List<Client> client = clientBox.query().equal(Client_.id, clientId).build().find();
        return client.size() != 0;
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void setResult(String result) {

    }
}
