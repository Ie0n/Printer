package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.MainContract;
import com.daily.jcy.printer.utils.callback.OnOrderItemClickListener;
import com.daily.jcy.printer.utils.message.BusEvent;
import com.daily.jcy.printer.view.adapter.OrderRecycleViewAdapter;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.presenter.MainPresenter;
import com.daily.jcy.printer.utils.AnimateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View, OnOrderItemClickListener {

    private static final String TAG = "MainActivity-zz";
    public static final String TARGET_ORDER = "TARGET_ORDER";
    public static final String TARGET_BUNDLE = "TARGET_BUNDLE";
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
    private OrderRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initPresenter();
        initView();
        initUtils();
        setCustomActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoginActivity.activity.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        EventBus.getDefault().unregister(this);
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

        adapter = new OrderRecycleViewAdapter(this, null);
        adapter.setOnOrderItemClickListener(this);
        orderRecyclerView.setAdapter(adapter);
        presenter.updateOrderListData();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BusEvent event) {
        Log.i(TAG, "onEvent: " + event.getMessage());
        if (event.getMessage() == BusEvent.CREATE_ORDER) {
            presenter.updateOrderListData();
        }
    }


    @Override
    public void updateOrderListData(List<Order> data) {
        Log.i(TAG, "updateOrderListData: ");
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
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
        if (v == searchEdit && hasFocus) {
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
                CloseMenu();
                startActivity(new Intent(this, ClientActivity.class));
                break;
            case R.id.btn_food:
                CloseMenu();
                startActivity(new Intent(this, FoodActivity.class));
                break;
            case R.id.btn_clear:
                CloseMenu();
                presenter.clearOrderList();
                presenter.updateOrderListData();
                clearOrderId();
                Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_setting:
                CloseMenu();
                startActivity(new Intent(this, SettingActivity.class));
                break;

        }
    }

    private void clearOrderId() {
        SharedPreferences.Editor editor = getSharedPreferences(Order.ORDER_DB, MODE_PRIVATE).edit();
        editor.putLong(Order.ORDER_ID, 0L);
        editor.apply();
    }

    private void CloseMenu() {
        if (isOpen) {
            animateUtils.closeMenu();
            isOpen = false;
        }
    }

    // 点击Item的回调
    @Override
    public void onOrderItemClick(Order order) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TARGET_ORDER, order);
        intent.putExtra(TARGET_BUNDLE, bundle);
        startActivity(intent);
    }
    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_main, null);
        getSupportActionBar().setCustomView(mActionBarView, lp);
        TextView text = mActionBarView.findViewById(R.id.title);
        TextView search = mActionBarView.findViewById(R.id.search);
        text.setText("Printer");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PrintfBlueListActivity.class));
            }
        });
    }
}
