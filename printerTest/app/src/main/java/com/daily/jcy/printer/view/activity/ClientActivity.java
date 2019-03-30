package com.daily.jcy.printer.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.adapter.ClientRecycleViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.OrderClientPresenter;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;
import com.daily.jcy.printer.utils.callback.OnClientDialogOkListener;
import com.daily.jcy.printer.view.dialog.ClientDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class ClientActivity extends BaseActivity implements OrderClientContract.View,
        OnItemClickListener, OnClientDialogOkListener {

    private EditText search;
    private SwipeRecyclerView clientRecyclerView;
    private OrderClientPresenter presenter;
    private ClientRecycleViewAdapter adapter;
    private FloatingActionButton btnAdd;
    private ClientDialog dialog;
    private Client newClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initPresenter();
        initView();
        initDialog();
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
        search = findViewById(R.id.client_search);
        btnAdd = findViewById(R.id.client_btn_add);
        btnAdd.setOnClickListener(this);
        search.addTextChangedListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        clientRecyclerView = findViewById(R.id.client_rv);
        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(ClientActivity.this);
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
                Toast.makeText(ClientActivity.this, "删除" + position, Toast.LENGTH_SHORT).show();

            }
        };
        clientRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        clientRecyclerView.setOnItemMenuClickListener(onItemMenuClickListener);
        presenter.updateClientListData();

    }

    private void initDialog() {
        dialog = new ClientDialog(this);
        dialog.setOnClientDialogOkListener(this);
        dialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void updateClientListData(List<Client> data) {
        adapter = new ClientRecycleViewAdapter(this, data);
        clientRecyclerView.setAdapter(adapter);
        // 设置Item的点击事件
        adapter.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(View view, Client client, Food food) {
        // 点击了Item的操作
        Toast.makeText(this, "点击了Item" + view.getTag(), Toast.LENGTH_SHORT).show();

    }

    // 关闭Dialog的回调
    @Override
    public void onOkListener(Client client) {
        if (client != null) {
            newClient = client;
            // 存入数据库,

            // 刷新页面
            adapter.addData(newClient,0);
        }
    }
}
