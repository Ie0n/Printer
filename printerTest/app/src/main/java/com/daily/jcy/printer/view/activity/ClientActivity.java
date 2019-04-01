package com.daily.jcy.printer.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.model.data.adapter.ClientRecycleViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.presenter.OrderClientPresenter;
import com.daily.jcy.printer.utils.LogUtils;
import com.daily.jcy.printer.utils.callback.OnItemClickListener;
import com.daily.jcy.printer.utils.callback.OnClientDialogDismissListener;
import com.daily.jcy.printer.utils.message.MessageEvent;
import com.daily.jcy.printer.view.dialog.ClientDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class ClientActivity extends BaseActivity implements OrderClientContract.View,
        OnItemClickListener, OnClientDialogDismissListener {

    private EditText search;
    private SwipeRecyclerView clientRecyclerView;
    private OrderClientPresenter presenter;
    private ClientRecycleViewAdapter adapter;
    private FloatingActionButton btnAdd;
    private ClientDialog createDialog;
    private ClientDialog updateDialog;
    private static final String TAG = "ClientActivity-ff";
    private Button btnSearch;
    private String input;
    private Client clickClient;

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
        btnSearch = findViewById(R.id.client_btn_search);
        btnAdd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
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
                // 通知去删除
                presenter.deleteClient(adapter.getmData().get(adapterPosition).getId());
            }
        };
        clientRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        clientRecyclerView.setOnItemMenuClickListener(onItemMenuClickListener);
        // 初始化adapter
        adapter = new ClientRecycleViewAdapter(this, null);
        clientRecyclerView.setAdapter(adapter);
        presenter.updateClientListData();
    }

    // 初始化dialog
    private void initDialog() {
        createDialog = new ClientDialog(this, new MessageEvent(MessageEvent.CREATE_CLIENT));
        createDialog.setOnClientDialogDismissListener(this);
        createDialog.setCanceledOnTouchOutside(false);

        MessageEvent event = new MessageEvent(MessageEvent.UPDATE_CLIENT);
        updateDialog = new ClientDialog(this, event);
        updateDialog.setOnClientDialogDismissListener(this);
        updateDialog.setCanceledOnTouchOutside(false);
    }


    // 初始化数据
    @Override
    public void updateClientListData(List<Client> data) {
        LogUtils.log(LogUtils.TEST_DB, "updateClientListData: ");
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
        // 设置Item的点击事件
        adapter.setOnItemClickListener(this);
    }


    // 搜索后的回调
    @Override
    public void notifyUi(List<Client> data) {
        Log.i(TAG, "notifyUi: ");
        if (data == null || data.size() == 0) {
            Toast.makeText(this, "不存在该编号", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setmData(data);
            adapter.notifyDataSetChanged();
        }
    }

    // 删除的回调
    @Override
    public void deleteResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        presenter.updateClientListData();
        // 清空搜索栏和监听值
        input = editTextClear(search, input);
    }


    // 搜索监听
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        input = s.toString();
        // 搜索栏为空的时候
        if (s.toString().equals("")) {
            presenter.updateClientListData();
        }
    }

    // 点击监听
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btnAdd) {
            createDialog.show();
        } else if (v == btnSearch) {
            // 如果为空或无就拉取全部数据
            if (input == null || input.equals("")) {
                presenter.updateClientListData();
            } else if (isInteger(input)) {
                Log.i(TAG, "onClick: " + input);
                presenter.searchClient(input);
            } else {
                Toast.makeText(this, "请输入正确的编号", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onItemClick(View view, Client client, Food food) {
        // 点击了Item的操作
        Toast.makeText(this, "点击了Item" + view.getTag(), Toast.LENGTH_SHORT).show();
        if (client != null) {
            clickClient = client;
            updateDialog.setBeforeClient(client);
            updateDialog.show();
        }
    }

    // 关闭创建的Dialog的回调
    @Override
    public void onCreateListener(Client client) {
        if (client != null) {
            LogUtils.log(LogUtils.TEST_DB, "onCreateListener");
            // 存入数据库,
            presenter.putClient(client);
            // 刷新页面
            adapter.addData(client, 0);
        }
    }

    // 更新数据的Dialog回调
    @Override
    public void onUpdateListener(Client client) {
        if (client != null) {
            // 更新数据
            presenter.updateClient(clickClient,client);
            // 刷新列表
            presenter.updateClientListData();
        }
    }


}
