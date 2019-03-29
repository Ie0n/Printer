package com.daily.jcy.printer.utils.callback;

import android.view.View;

import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;

public interface OnItemClickListener {
    void onItemClick(View view, Client client, Food food);

}
