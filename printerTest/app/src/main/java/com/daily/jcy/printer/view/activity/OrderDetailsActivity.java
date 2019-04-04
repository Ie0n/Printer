package com.daily.jcy.printer.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Count;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.model.data.bean.Order;

import java.util.List;

import io.objectbox.Box;

public class OrderDetailsActivity extends AppCompatActivity {
    private Order mOrder;
    private List<Food> foodList;
    private Client mClient;
    private List<Count> countList;
    private static final String TAG = "OrderDetailsActivity-aa";
    private Box<Order> orderBox;
    private TextView txtTime, txtSumme, txtClientId, txtName, txtPhone1, txtPhone2, txtZip, txtStreet, txtUnit, txtFloor, txtRoom, txtNote;
    private LinearLayout layoutFood;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        orderBox = ObjectBox.getBoxStore().boxFor(Order.class);
        inflater = LayoutInflater.from(this);
        initIntent();
        initView();
    }

    private void initIntent() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getBundleExtra(MainActivity.TARGET_BUNDLE);
            mOrder = (Order) bundle.getParcelable(MainActivity.TARGET_ORDER);
            if (mOrder != null) {
                orderBox.attach(mOrder);
                if (mOrder.getClientList() != null) {
                    mClient = mOrder.getClientList().get(0);
                }
                if (mOrder.getFoodList() != null) {
                    foodList = mOrder.getFoodList();
                }
                if (mOrder.countsList != null) {
                    countList = mOrder.countsList;
                }
            }
        }
    }

    private void initView() {
        if (mClient != null && foodList != null && countList != null) {
            txtTime = findViewById(R.id.txt_time);
            txtTime.setText(mOrder.getTime());

            layoutFood = findViewById(R.id.layout_food);
            for (int i = 0; i < foodList.size(); i++) {
                View view = inflater.inflate(R.layout.content_food, null, false);
                TextView txtCount = view.findViewById(R.id.txt_count);
                TextView txtId = view.findViewById(R.id.txt_food_id);
                TextView txtName = view.findViewById(R.id.txt_food_name);
                TextView txtPrice = view.findViewById(R.id.txt_price);
                Log.i(TAG, "initView: " + foodList.get(i).getNum());
                txtCount.setText(String.valueOf(mOrder.countsList.get(i).getCount()));
                txtId.setText(String.valueOf(foodList.get(i).getUid()));
                txtName.setText(foodList.get(i).getGERname());
                txtPrice.setText(foodList.get(i).getPrice());
                layoutFood.addView(view);
            }

            txtSumme = findViewById(R.id.txt_summe);
            txtSumme.setText(getSumme());

            txtClientId = findViewById(R.id.txt_client_id);
            txtClientId.setText(String.valueOf(mClient.getId()));

            txtName = findViewById(R.id.txt_client_name);
            txtName.setText(mClient.getName());

            txtPhone1 = findViewById(R.id.txt_client_phone1);
            txtPhone1.setText(mClient.getTel());

            txtPhone2 = findViewById(R.id.txt_client_phone2);
            if (mClient.getTel2() != null) {
                txtPhone2.setText(mClient.getTel2());
            } else {
                txtPhone2.setText("");
            }

            txtZip = findViewById(R.id.txt_client_zip);
            txtZip.setText(mClient.getZip());

            txtStreet = findViewById(R.id.txt_client_street);
            txtStreet.setText(mClient.getStreet());

            txtUnit = findViewById(R.id.txt_client_unit);
            if (mClient.getUnit() != null) {
                txtUnit.setText(mClient.getUnit());
            } else {
                txtUnit.setText("");
            }

            txtFloor = findViewById(R.id.txt_client_floor);

            if (mClient.getFloor() != null) {
                txtFloor.setText(mClient.getFloor());
            } else {
                txtFloor.setText("");
            }

            txtRoom = findViewById(R.id.txt_client_room);
            if (mClient.getRoom() != null) {
                txtRoom.setText(mClient.getRoom());
            } else {
                txtRoom.setText("");
            }

            txtNote = findViewById(R.id.txt_client_note);
            if (mClient.getNote() != null) {
                txtNote.setText(mClient.getNote());
            } else {
                txtNote.setText("");
            }

            Button btn = findViewById(R.id.btn_print_check);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 出单
                }
            });
        }
    }

    private String getSumme() {
        double result = 0;
        if (foodList != null) {
            for (int i = 0; i < foodList.size(); i++) {
                int count = foodList.get(i).getNum();
                String price = foodList.get(i).getPrice();
                String mPrice = price.replace(",", ".");
                double priceDouble = Double.parseDouble(mPrice) * count;
                result += priceDouble;
            }
        }
        Log.i(TAG, "getSumme: " + result);
        return String.format("%.2f", result);
    }
}
