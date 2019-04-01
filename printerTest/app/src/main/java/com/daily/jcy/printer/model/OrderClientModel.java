package com.daily.jcy.printer.model;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Client_;
import com.daily.jcy.printer.utils.LogUtils;
import com.daily.jcy.printer.utils.message.MessageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.objectbox.Box;

public class OrderClientModel implements OrderClientContract.Model {

    private static final String TAG = "OrderClientModel-bb";
    private List<Client> data;
    private String mResult;
    private Box<Client> clientBox;
    private List<Client> queryList;
    private List<Client> targetClientList;


    public OrderClientModel() {
        clientBox = ObjectBox.getBoxStore().boxFor(Client.class);
        data = new ArrayList<>();
        queryList = new ArrayList<>();
    }

    // 返回全部数据
    @Override
    public List<Client> getClientData(CharSequence s) {
        data.clear();
        if (clientBox.getAll() != null) {
            data.addAll(clientBox.getAll());
        }
        return data;
    }

    // 搜索
    @Override
    public List<Client> searchClientDb(CharSequence s) {
        queryList.clear();
        targetClientList = queryTargetList(s.toString());
        if (targetClientList != null) {
            queryList.addAll(targetClientList);
        }
        return queryList;
    }

    // 删除
    @Override
    public String deleteClient(long deleteId) {
        clientBox.remove(deleteId);
        return "删除成功";
    }

    // 存储Client
    @Override
    public String putClient(Client client) {
        clientBox.put(client);
        LogUtils.log(LogUtils.TEST_DB, "getCount: " + clientBox.count() + "ID: " + clientBox.getId(client));
        return MessageEvent.PUT_SUCCESS;
    }

    @Override
    public String updateClient(Client oldClient, Client updateClient) {
        clientBox.remove(oldClient);
        clientBox.put(updateClient);
        return "更新成功";
    }

    @Override
    public List<Client> queryTargetList(String strNum) {
        List<Client> result = new ArrayList<>();
        long num;
        // 防止空格等非法字符
        if (isInteger(strNum)) {
            num = Long.parseLong(strNum);
        } else {
            return null;
        }
        if (clientBox.get(num) != null) {
            result.add(clientBox.get(num));
        }
        switch (strNum.length()) {
            case 1:
                List<Client> result1 = clientBox.query().between(Client_.id, num * 10, num * 10 + 9).build().find();
                List<Client> result2 = clientBox.query().between(Client_.id, num * 100, num * 100 + 99).build().find();
                List<Client> result3 = clientBox.query().between(Client_.id, num * 1000, num * 1000 + 999).build().find();
                result.addAll(result1);
                result.addAll(result2);
                result.addAll(result3);
                return result;
            case 2:
                List<Client> result4 = clientBox.query().between(Client_.id, num * 10, num * 10 + 99).build().find();
                List<Client> result5 = clientBox.query().between(Client_.id, num * 100, num * 100 + 999).build().find();
                result.addAll(result4);
                result.addAll(result5);
                return result;
            case 3:
                List<Client> result6 = clientBox.query().between(Client_.id, num * 10, num * 10 + 999).build().find();
                result.addAll(result6);
                return result;
            case 4:
                return result;
            default:
                return null;
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

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
