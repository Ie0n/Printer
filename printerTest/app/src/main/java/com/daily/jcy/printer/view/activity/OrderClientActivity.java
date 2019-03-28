package com.daily.jcy.printer.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.bean.Client;

import java.util.List;

public class OrderClientActivity extends BaseActivity implements OrderClientContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_client);
        initPresenter();
        initView();
    }

    private void initPresenter() {

    }

    private void initView() {

    }


    @Override
    public void updateClientListData(List<Client> data) {

    }
}
