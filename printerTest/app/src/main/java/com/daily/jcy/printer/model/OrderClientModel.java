package com.daily.jcy.printer.model;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;

import java.util.ArrayList;
import java.util.List;

public class OrderClientModel implements OrderClientContract.Model {

    private List<Client> data;
    private String mResult;

    @Override
    public List<Client> getClientData() {
        data = new ArrayList<>();
        data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China"));
        setResult("Success");
        return data;
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
