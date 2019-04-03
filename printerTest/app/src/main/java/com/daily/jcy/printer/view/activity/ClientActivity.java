package com.daily.jcy.printer.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.OrderClientContract;
import com.daily.jcy.printer.view.adapter.ClientRecycleViewAdapter;
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
    private String input;
    private Client oldClient;
    private int clickPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initPresenter();
        initView();
        initDialog();
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
                // 通知去删除
                presenter.deleteClient(adapter.getmData().get(adapterPosition).getId());
            }
        };
        clientRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        clientRecyclerView.setOnItemMenuClickListener(onItemMenuClickListener);
        // 初始化adapter
        adapter = new ClientRecycleViewAdapter(this, null);
        clientRecyclerView.setAdapter(adapter);
        // 设置Item的点击事件
        adapter.setOnItemClickListener(this);
        // 拉取全部数据
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
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
    }


    // 搜索后的回调
    @Override
    public void notifyUI(List<Client> data) {
        Log.i(TAG, "notifyUI: ");
        if (data == null || data.size() == 0) {
            Toast.makeText(this, "不存在该编号", Toast.LENGTH_SHORT).show();
        }
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
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
        } else {
            presenter.searchClient(input);
        }
    }

    // 点击监听
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btnAdd) {
            createDialog.show();
        }
    }


    @Override
    public void onItemClick(View view, Client client, Food food) {
        // 点击了Item的操作
        if (client != null) {
            oldClient = client;
            updateDialog.setBeforeClient(client);
            updateDialog.show();
            clickPosition = (int) view.getTag(R.id.tag_position);
        }
    }

    // 关闭CreateDialog的回调
    @Override
    public void onCreateListener(Client newClient) {
        if (newClient != null) {
            LogUtils.log(LogUtils.TEST_DB, "onCreateListener");
            // 存入数据库,
            presenter.putClient(newClient);
            // 刷新页面
            adapter.addData(newClient, 0);
        }
    }

    // 更新数据的Dialog回调
    @Override
    public void onUpdateListener(Client updateClient) {
        if (updateClient != null) {
            // 更新数据
            presenter.updateClient(oldClient, updateClient);
            // 刷新Item的数据
            adapter.updateData(updateClient, clickPosition);
        }
    }
    private void setCustomActionBar() {
        ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        getSupportActionBar().setCustomView(mActionBarView, lp);
        TextView text = mActionBarView.findViewById(R.id.title);
        text.setText("客户资料");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView back = mActionBarView.findViewById(R.id.pic);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientActivity.this.finish();
            }
        });
    }


}
