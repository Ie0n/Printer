package com.daily.jcy.printer.model;

import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;

import java.util.List;

public class OrderClientModel implements OrderClientContract.Model {


    @Override
    public List<Client> getClientData() {
        return null;
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void setResult(String result) {

    }

}
