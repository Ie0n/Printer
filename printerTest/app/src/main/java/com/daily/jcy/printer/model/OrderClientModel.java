package com.daily.jcy.printer.model;

<<<<<<< HEAD
import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.R;
=======
import android.util.Log;

>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;
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


    @Override
<<<<<<< HEAD
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
=======
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
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055
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
