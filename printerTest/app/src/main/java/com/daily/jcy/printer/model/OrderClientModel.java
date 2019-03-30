package com.daily.jcy.printer.model;

import android.util.Log;

import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.utils.message.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class OrderClientModel implements OrderClientContract.Model {

    private static final String TAG = "OrderClientModel-bb";
    private List<Client> data;
    private String mResult;


    @Override
    public List<Client> getClientData(CharSequence s) {
        if (s == null) {
            data = new ArrayList<>();
            data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China","hhh"));
            data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China","hhh"));
            data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China","hhh"));
            data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China","hhh"));
            data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China","hhh"));
            data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China","hhh"));
            data.add(new Client("10001", "张三", "10008011111111", "CQUPT,Nanan,Chongqing,China","hhhl"));
            setResult(MessageEvent.INIT);
//            EventBus.getDefault().post(new MessageEvent(MessageEvent.INIT));
        } else {
            Log.i(TAG, "getClientData: " + s);
            data.clear();
            setResult(MessageEvent.UPDATE);

        }
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
