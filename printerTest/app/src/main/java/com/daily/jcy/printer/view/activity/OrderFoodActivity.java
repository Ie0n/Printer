package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.daily.jcy.printer.utils.callback.OnItemFoodClickListener;
import com.daily.jcy.printer.view.adapter.FoodRecyclerViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.OrderFoodPresenter;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderFoodActivity extends BaseActivity implements OrderFoodContract.View, OnItemFoodClickListener {

    public static final String TARGET_FOOD_BUNDLE = "targetFoodBundle";
    public static final String TARGET_FOOD_LIST = "targetFoodList";
    private OrderFoodPresenter presenter;
    private TextView txtSelectCount;
    private EditText search;
    private RecyclerView foodRecycler;
    private Button btnPop;
    private static final String TAG = "OrderFoodActivity-dd";
    private FoodRecyclerViewAdapter adapter;
    private int selectCount = 0;
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.clearFoodCount();
        presenter.detachView();
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
        presenter.clearFoodCount();
    }

    private void initView() {
        targetFoodList = new ArrayList<>();
        search = findViewById(R.id.order_client_search);
        foodRecycler = findViewById(R.id.order_food_rv);
        btnPop = findViewById(R.id.order_food_btn_pop);
        txtSelectCount = findViewById(R.id.order_food_txt_count);

        search.addTextChangedListener(this);
        foodRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodRecyclerViewAdapter(this, null);
        foodRecycler.setAdapter(adapter);
        adapter.setOnItemFoodClickListener(this);
        btnPop.setOnClickListener(this);

        presenter.updateFoodListData();
    }

    @Override
    public void updateFoodListData(List<Food> data) {
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
    }

    /**
     * 通过通知Adapter刷新UI
     * @param data 从数据库中检索里到的新数据返回
     */
    @Override
    public void notifyUI(List<Food> data) {
        if (data == null || data.size() == 0) {
            Toast.makeText(this, "不存在该编号", Toast.LENGTH_SHORT).show();
        }
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteResult(String result) {

    }

    /**
     * 修改选址菜篮里列表的信息。
     * 如果本来就存在就修改里买呢对象的信息，否则新添加
     * @param targetFood 对其数量加1后返回的对象
     */
    @Override
    public void addTargetList(Food targetFood) {
        boolean flag = false;
        for (int i = 0; i < targetFoodList.size(); i++) {
            // 如果本来就有就修改
            if (targetFood.getUid().equals(targetFoodList.get(i).getUid())) {
                targetFoodList.set(i, targetFood);
                flag = true;
                break;
            }
        }
        // 没找到就添加
        if (!flag) {
            targetFoodList.add(targetFood);
        }
        Log.i(TAG, "addTargetList: " + targetFoodList.size());
    }

    /**
     * 修改选中菜篮里的信息，如果是减到了0就直接移除列表
     * @param targetFood 对其数量减1返回的对象
     */
    @Override
    public void subTargetList(Food targetFood) {
        for (int i = 0; i < targetFoodList.size(); i++) {

            if (targetFood.getUid().equals(targetFoodList.get(i).getUid())) {
                if (targetFood.getNum() == 0) {
                    targetFoodList.remove(i);
                } else {
                    targetFoodList.set(i, targetFood);
                }
                break;
            }
        }
        Log.i(TAG, "subTargetList: " + targetFoodList.size());
    }

    @Override
    public void showResult(String text) {
        super.showResult(text);
        Log.i(TAG, "showResult: " + text);
    }

    /**
     * 当s改变时立刻通知数据库中检索数据返回到NotifyUI
     * @param s 输入框中输入的文字
     * @param start
     * @param before
     * @param count 字数
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        // 搜索栏为空
        if (s.toString().equals("")) {
            presenter.updateFoodListData();
        } else {
            presenter.searchFood(s.toString());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        // 点击菜篮的操作
        if (v == btnPop) {
            if (targetFoodList != null && selectCount != 0) {
                // 清除所有Food的count
                presenter.clearFoodCount();
                toNextActivity();
            } else {
                Toast.makeText(this, "菜篮还没有菜", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void toNextActivity() {
        Intent intent = new Intent(this, PrinterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TARGET_FOOD_LIST, (ArrayList<? extends Parcelable>) targetFoodList);
        intent.putExtra(TARGET_FOOD_BUNDLE, bundle);
        intent.putExtra(OrderClientActivity.TARGET_Client_BUNDLE, beforeBundle);
        startActivity(intent);
    }

    // 点击加减号的监听
    @Override
    public void onItemFoodClick(View view, TextView txtCount, Food food) {
        if ((int) view.getTag(R.id.tag_what) == FoodRecyclerViewAdapter.BTN_ADD) {
            // 菜篮
            selectCount ++;
            // 修改显示的份数
            txtCount.setText(String.valueOf(food.getNum() + 1));
            // 到数据库修改food添加的份数
            presenter.addFoodCount(food);
        } else if ((int) view.getTag(R.id.tag_what) == FoodRecyclerViewAdapter.BTN_SUB) {
            if (Integer.parseInt(txtCount.getText().toString()) != 0) {
                // 菜篮
                selectCount--;
                // 修改显示的份数
                txtCount.setText(String.valueOf(food.getNum() - 1));
                // 到数据库减少Food的份数
                presenter.subFoodCount(food);
            }
        }

        if (selectCount > 0) {
            txtSelectCount.setVisibility(View.VISIBLE);
            txtSelectCount.setText(String.valueOf(selectCount));
        } else {
            txtSelectCount.setText(String.valueOf(0));
            txtSelectCount.setVisibility(View.GONE);
        }
    }

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
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
