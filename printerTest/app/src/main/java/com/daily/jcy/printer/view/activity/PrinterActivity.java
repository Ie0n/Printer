package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daily.jcy.printer.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        initBeforeArg();
        initView();
    }

    private void initBeforeArg() {
        if (getIntent() != null) {
            Bundle clientBundle = getIntent().getBundleExtra(OrderClientActivity.TARGET_Client_BUNDLE);
            Bundle foodBundle = getIntent().getBundleExtra(OrderFoodActivity.TARGET_FOOD_BUNDLE);
            if (clientBundle != null) {
                targetClient = (Client) clientBundle.getSerializable(OrderClientActivity.TARGET_CLIENT);
                Log.i(TAG, "client: " + targetClient.getId());
            }
            if (foodBundle != null) {
                targetFoodList = (ArrayList<Food>) foodBundle.getSerializable(OrderFoodActivity.TARGET_FOOD_LIST);
                Log.i(TAG, "targetFoodList: " + targetFoodList.get(0).getId());
            }
        }
    }

    private void initView() {
        printerRecyclerView = findViewById(R.id.printer_rv);
        btnPrinter = findViewById(R.id.btn_printer);

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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btnPrinter) {
            Toast.makeText(this, "出单", Toast.LENGTH_SHORT).show();
        }
    }
}
