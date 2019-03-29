package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.adapter.ClientRecycleViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.OrderClientPresenter;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;

import java.util.List;

public class OrderClientActivity extends BaseActivity implements OrderClientContract.View, OnItemClickListener {


    private static final String TAG = "OrderClientActivity-bb";
    public static final String TARGET_CLIENT = "targetClient";
    public static final String TARGET_Client_BUNDLE = "targetClientBundle";
    private OrderClientPresenter presenter;
    private RecyclerView clientRecyclerView;
    private EditText search;
    private ClientRecycleViewAdapter adapter;
    private Client targetClient;
    private View tagrView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_client);
        initPresenter();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initPresenter() {
        presenter = new OrderClientPresenter();
        presenter.attachView(this);
    }

    private void initView() {
        search = findViewById(R.id.order_food_search);
        clientRecyclerView = findViewById(R.id.order_client_rv);
        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        search.addTextChangedListener(this);
        presenter.updateClientListData();
    }


    // View接口
    @Override
    public void updateClientListData(List<Client> data) {
        adapter = new ClientRecycleViewAdapter(this, data);
        clientRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void showResult(String text) {
        super.showResult(text);
        Log.i(TAG, "showResult: " + text);
    }


    @Override
    public void onItemClick(View view, Client client, Food food) {
        tagrView = view;
        Log.i(TAG, "onItemClick: " + view.getTag());
        targetClient = client;
        Intent intent = new Intent(this, OrderFoodActivity.class);
        Bundle bundle = new Bundle();
        // 传输点击的Client对象
        bundle.putSerializable(TARGET_CLIENT, targetClient);
        intent.putExtra(TARGET_Client_BUNDLE, bundle);
        startActivity(intent);
    }
}
