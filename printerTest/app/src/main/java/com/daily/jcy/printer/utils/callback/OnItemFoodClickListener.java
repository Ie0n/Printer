package com.daily.jcy.printer.utils.callback;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daily.jcy.printer.model.data.bean.Food;

public interface OnItemFoodClickListener {
    void onItemFoodClick(View view, TextView txtCount, Food food);
}
