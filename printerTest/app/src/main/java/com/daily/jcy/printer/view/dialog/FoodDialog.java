package com.daily.jcy.printer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Food;
import com.daily.jcy.printer.utils.callback.OnFoodDialogDismissListener;
import com.daily.jcy.printer.utils.message.MessageEvent;

public class FoodDialog extends Dialog implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private Context mContext;
    private int mCommand;
    private Food mFood;
    private String uid;
    private String price;
    private String cnName;
    private String greName;
    private boolean isSweetAndWine;
    private TextView txtCancel;
    private TextView txtOk;
    private TextInputEditText editId;
    private TextInputEditText editPrice;
    private TextInputEditText editCNName;
    private TextInputEditText editGreName;
    private RadioButton radioNo;
    private RadioButton radioYes;
    private RadioGroup radioGroup;
    private OnFoodDialogDismissListener onFoodDialogDismissListener;
    private Food beforeFood;
    private static long id = 100;
    private static final String TAG = "FoodDialog-pp";


    public FoodDialog(@NonNull Context context, MessageEvent event) {
        super(context);
        setContentView(R.layout.dialog_food);
        mContext = context;
        mCommand = event.getMessage();
        Log.i(TAG, "Command: " + mCommand);
        initView();
    }

    private void initView() {
        editId = findViewById(R.id.txt_edit_num);
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

        mTextWatcher();
    }


    // 设置点击item传进来的信息赋值给EditText的Hint
    private void setHintText(TextInputEditText editText,String hint) {
        if (hint != null) {
            editText.setText(hint);
        }
    }

    // 初始化Edit的默认文字
    private void initEditHint() {
        if (beforeFood != null) {
            uid = beforeFood.getUid();
            price = beforeFood.getPrice();
            cnName = beforeFood.getCNname();
            greName = beforeFood.getGERname();
            isSweetAndWine = beforeFood.isSweetAndWine();

            setHintText(editId, uid);
            setHintText(editPrice, price);
            setHintText(editCNName, cnName);
            setHintText(editGreName, greName);
            if (isSweetAndWine) {
//                radioGroup.check(radioYes.getId());
                radioYes.setChecked(true);
            } else {
//                radioGroup.check(R.id.radio_no);
//                radioGroup.check(radioNo.getId());
                radioNo.setChecked(true);

            }
            Log.i(TAG, "initEditHint: isSweetAndWine:  " + isSweetAndWine);
        }
    }

    @Override
    public void show() {
        // 每次打开给editNum焦点和弹出软键盘
        editId.setFocusable(true);
        editId.setFocusableInTouchMode(true);
        editId.requestFocus();
        if (mCommand == MessageEvent.UPDATE_FOOD) {
            initEditHint();
        }
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mCommand == MessageEvent.CREATE_FOOD) {
            onFoodDialogDismissListener.onCreateListener(mFood);
        } else if (mCommand == MessageEvent.UPDATE_FOOD) {
            onFoodDialogDismissListener.onUpdateListener(mFood);
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
        return radioNo.isChecked() || radioYes.isChecked();
    }

    // 置空
    private void clear() {
        mFood = null;
        uid = price = cnName = greName = null;
        isSweetAndWine = false;
        editId.setText("");
        editPrice.setText("");
        editCNName.setText("");
        editGreName.setText("");
        radioNo.setChecked(false);
        radioYes.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        // 取消和完成的点击事件
        if (v == txtCancel) {
            dismiss();
            clear();
        } else if (v == txtOk){
            if (!isComplete(uid, price, cnName, greName)) {
                Toast.makeText(mContext, "请填写完整！", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "原来的id：" + id);
                id++;
                mFood = new Food(uid, cnName, greName, price, isSweetAndWine);
                mFood.setId(id);
                Log.i(TAG, "add id: " + id);
                dismiss();
                clear();
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
        Log.i(TAG, "onCheckedChanged: " + isSweetAndWine);
    }
    public void setOnFoodDialogDismissListener(OnFoodDialogDismissListener onFoodDialogDismissListener) {
        this.onFoodDialogDismissListener = onFoodDialogDismissListener;
    }

    public void setBeforeFood(Food beforeFood) {
        this.beforeFood = beforeFood;
    }

    private void mTextWatcher() {
        editId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                uid = s.toString();
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
}
