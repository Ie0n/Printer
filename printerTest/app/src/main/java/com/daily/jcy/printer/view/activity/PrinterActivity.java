package com.daily.jcy.printer.view.activity;


import android.content.Context;

import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Mode;
import com.daily.jcy.printer.manager.PrintfManager;

import com.daily.jcy.printer.view.adapter.FoodRecyclerViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;

import java.util.ArrayList;
import java.util.List;

public class PrinterActivity extends BaseActivity {

    private ArrayList<Food> targetFoodList;
    private Client targetClient;
    private static final String TAG = "PrinterActivity-ee";
    private Button btnPrinter;
    private RecyclerView printerRecyclerView;
    private TextView tv_main_bluetooth;

    private List<Food> listData;

    private PrintfManager printfManager;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        initBeforeArg();
        context = this;
        initView();
        initData();
        setListener();
        setCustomActionBar();
    }

    private void setListener() {


        printfManager.addBluetoothChangLister(new PrintfManager.BluetoothChangLister() {
            @Override
            public void chang(String name, String address) {
                tv_main_bluetooth.setText(name);
            }
        });

        tv_main_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintfBlueListActivity.startActivity(PrinterActivity.this);
            }
        });

        btnPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printfManager.isConnect()) {
                    printfManager.printf_80(
                            "天香饭庄",
                            targetClient.getName(),
                            "鱼香肉丝不要鱼",
                            targetFoodList
                    );
                } else {
                    PrintfBlueListActivity.startActivity(PrinterActivity.this);
                }
            }
        });

    }

    private void initData() {
        printfManager = PrintfManager.getInstance(context);
        listData = new ArrayList<>();
        listData.add(new Food("10001","鱼香肉丝","dddd","200元",false,1));
        listData.add(new Food("10001","宫保鸡丁","aaaaaa","987元",false,2));
        listData.add(new Food("10001","盐煎肉","efefe","160元",false,1));
        printfManager.defaultConnection();
    }

    private void initBeforeArg() {
        if (getIntent() != null) {
            Bundle clientBundle = getIntent().getBundleExtra(OrderClientActivity.TARGET_Client_BUNDLE);
            Bundle foodBundle = getIntent().getBundleExtra(OrderFoodActivity.TARGET_FOOD_BUNDLE);
            if (clientBundle != null) {
                targetClient = (Client) clientBundle.getSerializable(OrderClientActivity.TARGET_CLIENT);
            }
            if (foodBundle != null) {
                targetFoodList = (ArrayList<Food>) foodBundle.getSerializable(OrderFoodActivity.TARGET_FOOD_LIST);
                for (int i = 0; i < targetFoodList.size(); i++) {
                    Log.d(TAG,targetFoodList.get(i).getCNname());
                }
            }
        }
    }

    private void initView() {
        printerRecyclerView = findViewById(R.id.printer_rv);
        btnPrinter = findViewById(R.id.btn_printer);

        tv_main_bluetooth = findViewById(R.id.tv_main_bluetooth);
        printerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        btnPrinter.setOnClickListener(this);
    }

    private void initAdapter() {
        if (targetFoodList != null) {
            printerRecyclerView.setAdapter(new FoodRecyclerViewAdapter(this, targetFoodList));
        } else {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        getSupportActionBar().setCustomView(mActionBarView, lp);
        TextView text = mActionBarView.findViewById(R.id.title);
        text.setText("出单");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView back = mActionBarView.findViewById(R.id.pic);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.this.finish();
            }
        });
    }
}
