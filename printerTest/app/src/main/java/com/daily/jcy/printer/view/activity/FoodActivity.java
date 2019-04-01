package com.daily.jcy.printer.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderFoodContract;
import com.daily.jcy.printer.view.adapter.FoodRecyclerViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.OrderFoodPresenter;
import com.daily.jcy.printer.utils.LogUtils;
import com.daily.jcy.printer.utils.callback.OnFoodDialogDismissListener;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;
import com.daily.jcy.printer.utils.message.MessageEvent;
import com.daily.jcy.printer.view.dialog.FoodDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class FoodActivity extends BaseActivity implements OrderFoodContract.View, OnFoodDialogDismissListener, OnItemClickListener {

    private OrderFoodContract.Presenter presenter;
    private SwipeRecyclerView foodRecyclerView;
    private EditText search;
    private FoodRecyclerViewAdapter adapter;
    private FloatingActionButton btnAdd;
    private FoodDialog createDialog;
    private FoodDialog updateDialog;
    private String input;
    private Food oldFood;
    private int clickPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initPresenter();
        initView();
        initDialog();
    }

    private void initDialog() {
        createDialog = new FoodDialog(this, new MessageEvent(MessageEvent.CREATE_FOOD));
        createDialog.setOnFoodDialogDismissListener(this);
        createDialog.setCanceledOnTouchOutside(false);

        updateDialog = new FoodDialog(this, new MessageEvent(MessageEvent.UPDATE_FOOD));
        updateDialog.setOnFoodDialogDismissListener(this);
        updateDialog.setCanceledOnTouchOutside(false);
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
                presenter.deleteFood(adapter.getmData().get(adapterPosition));
                Toast.makeText(FoodActivity.this, "删除" + adapterPosition, Toast.LENGTH_SHORT).show();

            }
        };
        foodRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        foodRecyclerView.setOnItemMenuClickListener(onItemMenuClickListener);
        // 初始化adapter
        adapter = new FoodRecyclerViewAdapter(this, null);
        foodRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        presenter.updateFoodListData();
    }

    @Override
    public void updateFoodListData(List<Food> data) {
        // 拉取全部数据
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
    }

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
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        presenter.updateFoodListData();
        // 清空搜索栏和监听值
        input = editTextClear(search, input);
    }

    @Override
    public void showResult(String text) {
//        LogUtils.log("-gg", "showResult: " + text);
    }

    // 搜索字的回调
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        input = s.toString();
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
        // 新菜品
        if (v == btnAdd) {
            createDialog.show();
        }
    }

    // 点击Item的回调
    @Override
    public void onItemClick(View view, Client client, Food food) {
        if (food != null) {
            clickPosition = (int) view.getTag(R.id.tag_position);
            oldFood = food;
            updateDialog.setBeforeFood(food);
            updateDialog.show();
        }
    }

    // CreateDialog关闭的回调
    @Override
    public void onCreateListener(Food newFood) {
        if (newFood != null) {
            adapter.addData(newFood, 0);
            // 数据库操作
            presenter.putFood(newFood);
        }
    }

    // updateDialog关闭的回调
    @Override
    public void onUpdateListener(Food updateFood) {
        if (updateFood != null) {
            // 更新数据
            presenter.updateFood(oldFood, updateFood);
            // 刷新Item
            adapter.updateData(oldFood, clickPosition);
        }
    }
}
