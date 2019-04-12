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
            // 2
            case 1:
                // 20-29，200-299，2000-2999，20000-29999，200000-299999,2000000-2999999,20000000-29999999
                List<Client> result1 = clientBox.query().between(Client_.id, num * 10, num * 10 + 9).build().find();
                List<Client> result2 = clientBox.query().between(Client_.id, num * 100, num * 100 + 99).build().find();
                List<Client> result3 = clientBox.query().between(Client_.id, num * 1000, num * 1000 + 999).build().find();
                List<Client> result8 = clientBox.query().between(Client_.id, num * 10000, num * 10000 + 9999).build().find();
                List<Client> result9 = clientBox.query().between(Client_.id, num * 100000, num * 100000 + 99999).build().find();
                List<Client> result10 = clientBox.query().between(Client_.id, num * 1000000, num * 1000000 + 999999).build().find();
                List<Client> result11 = clientBox.query().between(Client_.id, num * 10000000, num * 10000000 + 9999999).build().find();

                result.addAll(result1);
                result.addAll(result2);
                result.addAll(result3);
                result.addAll(result8);
                result.addAll(result9);
                result.addAll(result10);
                result.addAll(result11);

                return result;
                // 20
            case 2:
                // 200-299,2000-2999,20000-29999,200000-299999,2000000-2999999,20000000-29999999
                List<Client> result4 = clientBox.query().between(Client_.id, num * 10, num * 10 + 99).build().find();
                List<Client> result5 = clientBox.query().between(Client_.id, num * 100, num * 100 + 999).build().find();
                List<Client> result12 = clientBox.query().between(Client_.id, num * 1000, num * 1000 + 9999).build().find();
                List<Client> result13 = clientBox.query().between(Client_.id, num * 10000, num * 10000 + 99999).build().find();
                List<Client> result14 = clientBox.query().between(Client_.id, num * 100000, num * 100000 + 999999).build().find();
                List<Client> result15 = clientBox.query().between(Client_.id, num * 1000000, num * 1000000 + 9999999).build().find();

                result.addAll(result4);
                result.addAll(result5);
                result.addAll(result12);
                result.addAll(result13);
                result.addAll(result14);
                result.addAll(result15);
                return result;
                // 200
            case 3:
                // 2000-2999,20000-29999,200000-299999,2000000-2999999
                List<Client> result6 = clientBox.query().between(Client_.id, num * 10, num * 10 + 999).build().find();
                List<Client> result16 = clientBox.query().between(Client_.id, num * 100, num * 100 + 9999).build().find();
                List<Client> result17 = clientBox.query().between(Client_.id, num * 1000, num * 1000 + 99999).build().find();
                List<Client> result18 = clientBox.query().between(Client_.id, num * 10000, num * 10000 + 999999).build().find();
                List<Client> result19 = clientBox.query().between(Client_.id, num * 100000, num * 100000 + 9999999).build().find();
                result.addAll(result6);
                result.addAll(result16);
                result.addAll(result17);
                result.addAll(result18);
                result.addAll(result19);
                return result;
                // 2000
            case 4:
                // 20000-29999,200000-299999,2000000-2999999,20000000-29999999
                List<Client> result7 = clientBox.query().between(Client_.id, num * 10, num * 10 + 9999).build().find();
                List<Client> result20 = clientBox.query().between(Client_.id, num * 100, num * 100 + 99999).build().find();
                List<Client> result21 = clientBox.query().between(Client_.id, num * 1000, num * 1000 + 999999).build().find();
                List<Client> result22 = clientBox.query().between(Client_.id, num * 10000, num * 10000 + 9999999).build().find();
                result.addAll(result7);
                result.addAll(result20);
                result.addAll(result21);
                result.addAll(result22);
                return result;
                //20000
            case 5:
                //200000-299999,2000000-2999999,20000000-29999999
                List<Client> result23 = clientBox.query().between(Client_.id, num * 10, num * 10 + 99999).build().find();
                List<Client> result24 = clientBox.query().between(Client_.id, num * 100, num * 100 + 999999).build().find();
                List<Client> result25 = clientBox.query().between(Client_.id, num * 1000, num * 1000 + 9999999).build().find();
                result.addAll(result23);
                result.addAll(result24);
                result.addAll(result25);
                return result;
                // 200000
            case 6:
                // 2000000-2999999,20000000-29999999
                List<Client> result26 = clientBox.query().between(Client_.id, num * 10, num * 10 + 999999).build().find();
                List<Client> result27 = clientBox.query().between(Client_.id, num * 100, num * 100 + 9999999).build().find();
                List<Client> result28 = clientBox.query().between(Client_.id, num * 1000, num * 1000 + 99999999).build().find();
                result.addAll(result26);
                result.addAll(result27);
                result.addAll(result28);
                return result;
                // 2000000
            case 7:
                // 20000000-29999999
                List<Client> result29 = clientBox.query().between(Client_.id, num * 10, num * 10 + 9999999).build().find();
                result.addAll(result29);
                return result;
            case 8:
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
