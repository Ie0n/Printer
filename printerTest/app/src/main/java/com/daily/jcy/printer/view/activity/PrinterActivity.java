package com.daily.jcy.printer.view.activity;


import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
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

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.R;
import com.daily.jcy.printer.manager.PrintfManager;

import com.daily.jcy.printer.model.data.bean.Count;
import com.daily.jcy.printer.model.data.bean.Order;
import com.daily.jcy.printer.utils.message.BusEvent;
import com.daily.jcy.printer.view.adapter.FoodRecyclerViewAdapter;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class PrinterActivity extends BaseActivity {

    private ArrayList<Food> targetFoodList;
    private Client targetClient;
    private static final String TAG = "PrinterActivity-ee";
    private Button btnPrintKitchen,btnPrintCheck;
    private RecyclerView printerRecyclerView;
    private TextView tv_main_bluetooth;
    private Box<Order> orderBox;
    private Box<Count> countBox;
    private List<Food> listData;
    private PrintfManager printfManager;
    private Context context;
    private FloatingActionButton back;

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
        initBox();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initBox() {
        orderBox = ObjectBox.getBoxStore().boxFor(Order.class);
        countBox = ObjectBox.getBoxStore().boxFor(Count.class);
    }

    private void setListener() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,MainActivity.class));
            }
        });
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

        btnPrintKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printfManager.isConnect()) {
                    printfManager.printf_kitchen(targetFoodList);
                } else {
                    PrintfBlueListActivity.startActivity(PrinterActivity.this);
                }
            }
        });
        btnPrintCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printfManager.isConnect()){
                    printfManager.print_check(
                            targetClient,
                            "Asia Restaurant",
                            targetFoodList,
                            getSumme());
                }

                saveOrder();
            }
        });

    }

    private String getSumme() {
        double result = 0;
        if (targetFoodList != null) {
            for (int i = 0; i < targetFoodList.size(); i++) {
                int count = targetFoodList.get(i).getNum();
                String price = targetFoodList.get(i).getPrice();
                String mPrice = price.replace(",", ".");
                double priceDouble = Double.parseDouble(mPrice) * count;
                result += priceDouble;
            }
        }
        Log.i(TAG, "getSumme: " + result);
        return String.format("%.2f", result);
    }

    private void saveOrder() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String time = simpleDateFormat.format(System.currentTimeMillis());
        String summe = String.valueOf(getSumme());
        String summe2 = summe.replace(".", ",");
        Log.i(TAG, "saveOrder: " + summe2);

        List<Count> counts = new ArrayList<>();
        for (int i = 0; i < targetFoodList.size(); i++) {
            counts.add(new Count(0L, targetFoodList.get(i).getNum()));
        }
        countBox.put(counts);
        Order order = new Order(0L, time, summe2);

        // 数据库操作
        orderBox.attach(order);
        order.foodList.addAll(targetFoodList);
        order.clientList.add(targetClient);
        order.countsList.addAll(counts);
        orderBox.put(order);
        EventBus.getDefault().post(new BusEvent(BusEvent.CREATE_ORDER));
    }

    private void initData() {
        printfManager = PrintfManager.getInstance(context);
        listData = new ArrayList<>();
        listData.add(new Food("10001","鱼香肉丝","dddd","$200",false,1));
        listData.add(new Food("10001","宫保鸡丁","aaaaaa","$987",false,2));
        listData.add(new Food("10001","盐煎肉","efefe","$160",false,1));
        printfManager.defaultConnection();
    }

    private void initBeforeArg() {
        if (getIntent() != null) {
            Bundle clientBundle = getIntent().getBundleExtra(OrderClientActivity.TARGET_Client_BUNDLE);
            Bundle foodBundle = getIntent().getBundleExtra(OrderFoodActivity.TARGET_FOOD_BUNDLE);
            if (clientBundle != null) {
                targetClient =  clientBundle.getParcelable(OrderClientActivity.TARGET_CLIENT);
            }
            if (foodBundle != null) {
                targetFoodList =  foodBundle.getParcelableArrayList(OrderFoodActivity.TARGET_FOOD_LIST);
            }
        }
    }

    private void initView() {
        printerRecyclerView = findViewById(R.id.printer_rv);
        btnPrintKitchen = findViewById(R.id.btn_print_kitchen);
        btnPrintCheck = findViewById(R.id.btn_print_check);
        back = findViewById(R.id.fab_back);
        tv_main_bluetooth = findViewById(R.id.tv_main_bluetooth);
        printerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        btnPrintKitchen.setOnClickListener(this);
        btnPrintCheck.setOnClickListener(this);
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
