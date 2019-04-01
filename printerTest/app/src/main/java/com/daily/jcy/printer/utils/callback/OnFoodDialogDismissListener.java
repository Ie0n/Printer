package com.daily.jcy.printer.utils.callback;

import com.daily.jcy.printer.model.data.bean.Food;

public interface OnFoodDialogDismissListener {
    void onCreateListener(Food newFood);

    void onUpdateListener(Food updateFood);
}
