package com.daily.jcy.printer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.utils.LogUtils;
import com.daily.jcy.printer.utils.callback.OnFoodDialogOkListener;

public class FoodDialog extends Dialog implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private Context mContext;
    private Food food;
    private String id;
    private String price;
    private String cnName;
    private String greName;
    private boolean isSweetAndWine;
    private TextView txtCancel;
    private TextView txtOk;
    private TextInputEditText editNum;
    private TextInputEditText editPrice;
    private TextInputEditText editCNName;
    private TextInputEditText editGreName;
    private RadioButton radioNo;
    private RadioButton radioYes;
    private RadioGroup radioGroup;
    private OnFoodDialogOkListener onFoodDialogOkListener;


    public FoodDialog( @NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_food);
        mContext = context;
        initView();
    }

    private void initView() {
        editNum = findViewById(R.id.txt_edit_num);
        editPrice = findViewById(R.id.txt_edit_price);
        editCNName = findViewById(R.id.txt_edit_cn_name);
        editGreName = findViewById(R.id.txt_edit_gre_name);
        radioNo = findViewById(R.id.radio_no);
        radioYes = findViewById(R.id.radio_yes);
        radioGroup = findViewById(R.id.radio_group);
        txtCancel = findViewById(R.id.txx_cancel);
        txtOk = findViewById(R.id.txt_ok);

        txtCancel.setOnClickListener(this);
        txtOk.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);

        editNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                price = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editCNName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cnName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editGreName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                greName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void show() {
        // 每次打开给editNum焦点和弹出软键盘
        editNum.setFocusable(true);
        editNum.setFocusableInTouchMode(true);
        editNum.requestFocus();
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (onFoodDialogOkListener != null) {
            onFoodDialogOkListener.onOkListener(food);
        }
    }


    // 判断是否完成
    private boolean isComplete(String... strings) {
        for (String string : strings) {
            if (string == null) {
                return false;
            } else if (string.equals("")) {
                return false;
            }
        }
        LogUtils.log("check","NO: " + radioNo.isChecked() + "  Yes:"  + radioYes.isChecked());
        return radioNo.isChecked() || radioYes.isChecked();
    }

    // 置空
    private void clear() {
        id = price = cnName = greName = null;
        isSweetAndWine = false;
        editNum.setText("");
        editPrice.setText("");
        editCNName.setText("");
        editGreName.setText("");
    }

    @Override
    public void onClick(View v) {
        // 取消和完成的点击事件
        if (v == txtCancel) {
            clear();
            dismiss();
        } else if (v == txtOk){
            if (!isComplete(id, price, cnName, greName)) {
                Toast.makeText(mContext, "请填写完整！", Toast.LENGTH_SHORT).show();
            } else {
                food = new Food(id, cnName, greName, price, isSweetAndWine);
                clear();
                dismiss();
            }
        }
    }


    // 单选框选择监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_yes:
                isSweetAndWine = true;
                break;
            case R.id.radio_no:
                isSweetAndWine = false;
                break;
        }
    }

    public void setOnFoodDialogOkListener(OnFoodDialogOkListener onFoodDialogOkListener) {
        this.onFoodDialogOkListener = onFoodDialogOkListener;
    }
}
