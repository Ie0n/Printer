package com.daily.jcy.printer.model;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.R;
import android.util.Log;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.utils.LogUtils;
import com.daily.jcy.printer.utils.message.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class OrderClientModel implements OrderClientContract.Model {

    private static final String TAG = "OrderClientModel-bb";
    private List<Client> data;
    private String mResult;
    private Box<Client> clientBox;
    private List<Client> queryList;
    private Client targetClient;


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
            LogUtils.log(LogUtils.TEST_DB, "size：" + data.size() + "getSize: " + clientBox.getAll().size());
        }
        LogUtils.log(LogUtils.TEST_DB, " getClientData");
        return data;
    }

    // 搜索
    @Override
    public List<Client> searchClientDb(CharSequence s) {
        queryList.clear();
        long targetId = Long.parseLong(s.toString());
        targetClient = clientBox.get(targetId);
        if (targetClient != null) {
            queryList.add(targetClient);
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
        LogUtils.log( LogUtils.TEST_DB,"getCount: " + clientBox.count() + "ID: " + clientBox.getId(client));
        return MessageEvent.PUT_SUCCESS;
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
