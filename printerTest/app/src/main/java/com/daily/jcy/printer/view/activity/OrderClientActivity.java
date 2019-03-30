package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
    private List<Client> data;

    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_client);
        initPresenter();
        initView();
        setCustomActionBar();
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
        presenter.updateClientListData();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                presenter.SearchClient(Long.parseLong(search.getText().toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    // View接口
    @Override
    public void updateClientListData(List<Client> data) {
        this.data = data;
        adapter = new ClientRecycleViewAdapter(this, data);
        clientRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void finishSearch() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyUi(List<Client> data) {
        this.data.clear();
        this.data.addAll(data);
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

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_order_client, null);
        getSupportActionBar().setCustomView(mActionBarView, lp);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView back = mActionBarView.findViewById(R.id.pic);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderClientActivity.this.finish();
            }
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
