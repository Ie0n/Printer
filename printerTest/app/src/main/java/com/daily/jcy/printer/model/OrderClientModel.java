package com.daily.jcy.printer.model;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class OrderClientModel implements OrderClientContract.Model {

    private List<Client> data;
    private String mResult;
    private Box<Client> clientBox;

    @Override
    public List<Client> getClientData() {
        data = new ArrayList<>();
        clientBox = ObjectBox.getBoxStore().boxFor(Client.class);
        data.add(new Client(1L,"10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client(2L,"10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client(3L,"10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client(4L,"10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client(5L,"10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client(6L,"10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client(7L,"10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        clientBox.put(data);
        setResult("Success");
        return data;
    }

    @Override
    public Client getClientID(Long id) {
        data.clear();
        data.add(new Client(id,"ddd","ddd","aaaaaaa","ddddddddddd"));
        return clientBox.get(id);
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
