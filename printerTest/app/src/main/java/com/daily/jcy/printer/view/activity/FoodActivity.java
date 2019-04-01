package com.daily.jcy.printer.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderFoodContract;

import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.OrderFoodPresenter;
import com.daily.jcy.printer.utils.LogUtils;
import com.daily.jcy.printer.utils.callback.OnFoodDialogOkListener;
import com.daily.jcy.printer.view.adapter.FoodRecyclerViewAdapter;
import com.daily.jcy.printer.view.dialog.FoodDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class FoodActivity extends BaseActivity implements OrderFoodContract.View , OnFoodDialogOkListener {

    private OrderFoodContract.Presenter presenter;
    private SwipeRecyclerView foodRecyclerView;
    private EditText search;
    private FoodRecyclerViewAdapter adapter;
    private FloatingActionButton btnAdd;
    private FoodDialog dialog;
    private Food newFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initPresenter();
        initView();
        initDialog();
    }

    private void initDialog() {
        dialog = new FoodDialog(this);
        dialog.setOnFoodDialogOkListener(this);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initPresenter() {
        presenter = new OrderFoodPresenter();
        presenter.attachView(this);
    }

    private void initView() {
        search = findViewById(R.id.food_search);
        search.addTextChangedListener(this);
        btnAdd = findViewById(R.id.food_btn_add);
        btnAdd.setOnClickListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        foodRecyclerView = findViewById(R.id.food_rv);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(FoodActivity.this);
                swipeMenuItem.setText("删除");
                swipeMenuItem.setImage(R.mipmap.ic_item_delete);
                swipeMenuItem.setBackground(R.drawable.item_delete_bg);
                swipeMenuItem.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                rightMenu.addMenuItem(swipeMenuItem);

            }
        };
        OnItemMenuClickListener onItemMenuClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                menuBridge.closeMenu();

                // 点击删除的操作
                int position = menuBridge.getPosition();
                Toast.makeText(FoodActivity.this, "删除" + position, Toast.LENGTH_SHORT).show();

            }
        };
        foodRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        foodRecyclerView.setOnItemMenuClickListener(onItemMenuClickListener);
        presenter.updateFoodListData();
    }

    @Override
    public void updateFoodListData(List<Food> data) {
        adapter = new FoodRecyclerViewAdapter(this, data);
        foodRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showResult(String text) {
        LogUtils.log("-gg","showResult: " + text);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btnAdd) {
            dialog.show();
        }
    }

    // dialog的回调
    @Override
    public void onOkListener(Food food) {
        if (food != null) {
            newFood = food;
            adapter.addData(food, 0);
            // 数据库操作
        }
    }
}
