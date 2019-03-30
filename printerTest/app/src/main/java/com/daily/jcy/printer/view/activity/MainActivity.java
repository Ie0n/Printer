package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.MainContract;
import com.daily.jcy.printer.model.data.adapter.OrderRecycleViewAdapter;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.presenter.MainPresenter;
import com.daily.jcy.printer.utils.AnimateUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String TAG = "MainActivity-zz";
    private MainPresenter presenter;
    private EditText searchEdit;
    private RecyclerView orderRecyclerView;
    private FloatingActionButton btnMore;
    private Button btnClient;
    private Button btnFood;
    private Button btnSetting;
    private boolean isOpen = false;
    private AnimateUtils animateUtils;
    private Button btnClear;
    private ArrayList<View> animViews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verification();
        initPresenter();
        initView();
        initUtils();
    }

    private void verification() {
        // 取出是否开启密码
        if (true) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initPresenter() {
        presenter = new MainPresenter();
        presenter.attachView(this);
    }

    private void initView() {
        Log.i(TAG, "initView: ");
        searchEdit = findViewById(R.id.main_search);
        searchEdit.setOnFocusChangeListener(this);
        searchEdit.setOnClickListener(this);
        searchEdit.addTextChangedListener(this);
        orderRecyclerView = findViewById(R.id.main_rv);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
<<<<<<< HEAD



=======
        presenter.updateOrderListData();
        presenter.showResult();
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055

        // 菜单控件
        btnMore = findViewById(R.id.fab_menu);
        btnClient = findViewById(R.id.btn_client);
        btnFood = findViewById(R.id.btn_food);
        btnSetting = findViewById(R.id.btn_setting);
        btnClear = findViewById(R.id.btn_clear);
        btnMore.setOnClickListener(this);
        btnClient.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        presenter.updateOrderListData();
        adapter = new OrderRecycleViewAdapter(this, data);
        orderRecyclerView.setAdapter(adapter);
    }

    private void initUtils() {
        animViews = new ArrayList<>();
        animViews.add(btnClient);
        animViews.add(btnFood);
        animViews.add(btnClear);
        animViews.add(btnSetting);
        animateUtils = new AnimateUtils();
        // 设置半径和个数
        animateUtils.setRadius(300);
        animateUtils.setTotalCount(4);
        animateUtils.setViews(animViews);
    }



    @Override
    public void updateOrderListData(List<Order> data) {
        Log.i(TAG, "updateOrderListData: ");
        orderRecyclerView.setAdapter(new OrderRecycleViewAdapter(this, data));
    }

    @Override
    public void deleteOrderListData(boolean is) {

    }


    // 展示更新数据的错误信息
    @Override
    public void showResult(String text) {
        Log.i(TAG, "showResult: " + text);
    }

    // 设置EditText监听
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        super.onFocusChange(v, hasFocus);
        if (v == searchEdit&& hasFocus) {
            startActivity(new Intent(this, OrderClientActivity.class));
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        startActivity(new Intent(this, OrderClientActivity.class));
    }

    // 点击监听
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_search:
                startActivity(new Intent(this, OrderClientActivity.class));
                break;
            case R.id.fab_menu: {
                if (!isOpen) {
                    animateUtils.openMenu();
                    isOpen = true;
                } else {
                    animateUtils.closeMenu();
                    isOpen = false;
                }
                break;
            }
            case R.id.btn_client:
                animateUtils.closeMenu();
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.btn_food:
                animateUtils.closeMenu();
                startActivity(new Intent(this,FoodActivity.class));
                break;
            case R.id.btn_clear:
                animateUtils.closeMenu();
                Toast.makeText(this, "清除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_setting:
                animateUtils.closeMenu();
                startActivity(new Intent(this,SettingActivity.class));
                break;

        }
    }
}
