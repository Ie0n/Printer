package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderFoodContract;
import com.daily.jcy.printer.view.adapter.FoodRecyclerViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.OrderFoodPresenter;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderFoodActivity extends BaseActivity implements OrderFoodContract.View , OnItemClickListener {

    public static final String TARGET_FOOD_BUNDLE = "targetFoodBundle";
    public static final String TARGET_FOOD_LIST = "targetFoodList";
    private OrderFoodPresenter presenter;
    private TextView txtSelectCount;
    private EditText search;
    private RecyclerView foodRecycler;
    private Button btnPop;
    private static final String TAG = "OrderFoodActivity-dd";
    private FoodRecyclerViewAdapter adapter;
    private int count = 0;
    private List<Food> targetFoodList;
    private Bundle beforeBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initBeforePageArg();
        initPresenter();
        initView();
        setCustomActionBar();
        Log.i(TAG, "onCreate: ");
    }

    private void initBeforePageArg() {
        if (getIntent() != null) {
            Intent beforeIntent = getIntent();
            beforeBundle = beforeIntent.getBundleExtra(OrderClientActivity.TARGET_Client_BUNDLE);
        }

    }


    private void initPresenter() {
        presenter = new OrderFoodPresenter();
        presenter.attachView(this);
    }

    private void initView() {

        targetFoodList = new ArrayList<>();

        search = findViewById(R.id.order_client_search);
        foodRecycler = findViewById(R.id.order_food_rv);
        btnPop = findViewById(R.id.order_food_btn_pop);
        txtSelectCount = findViewById(R.id.order_food_txt_count);


        search.addTextChangedListener(this);
        foodRecycler.setLayoutManager(new LinearLayoutManager(this));
        btnPop.setOnClickListener(this);

        presenter.updateFoodListData();
    }

    @Override
    public void updateFoodListData(List<Food> data) {
        adapter = new FoodRecyclerViewAdapter(this, data);
        foodRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void showResult(String text) {
        super.showResult(text);
        Log.i(TAG, "showResult: " + text);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        // 点击菜篮的操作
        if (v == btnPop) {
            if (targetFoodList != null) {
                toNextActivity();
            } else {
                Toast.makeText(this, "菜篮还没有菜", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void toNextActivity() {
        Intent intent = new Intent(this, PrinterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TARGET_FOOD_LIST, (Serializable) targetFoodList);
        intent.putExtra(TARGET_FOOD_BUNDLE, bundle);
        intent.putExtra(OrderClientActivity.TARGET_Client_BUNDLE, beforeBundle);
        startActivity(intent);
    }

    // 点击加号的监听
    @Override
    public void onItemClick(View view, Client client, Food food) {
        // 如果被点击
        if ((boolean)view.getTag(R.id.tag_is_click)) {
            // 菜品未被添加被添加后的操作
            view.setBackgroundResource(R.mipmap.ic_yes);
            targetFoodList.add(food);
            count++;
            view.setTag(R.id.tag_is_click, false);
        } else {
            view.setBackgroundResource(R.mipmap.ic_add);
            for (int i = 0; i < targetFoodList.size(); i++) {
                if (targetFoodList.get(i) == food) {
                    targetFoodList.remove(i);
                }
            }
            count--;
            view.setTag(R.id.tag_is_click,true);
        }


        if (count > 0) {
            txtSelectCount.setVisibility(View.VISIBLE);
            txtSelectCount.setText(String.valueOf(count));
        } else {
            txtSelectCount.setText(String.valueOf(count));
            txtSelectCount.setVisibility(View.GONE);
        }
    }
    private void setCustomActionBar() {
        ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        TextView text = mActionBarView.findViewById(R.id.title);
        text.setText("选择菜品");
        getSupportActionBar().setCustomView(mActionBarView, lp);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView back = mActionBarView.findViewById(R.id.pic);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFoodActivity.this.finish();
            }
        });
    }
}
