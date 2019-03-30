package com.daily.jcy.printer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.utils.callback.OnClientDialogOkListener;

public class ClientDialog extends Dialog implements  View.OnClickListener {

    private TextView txtOk;
    private TextView txtCancel;
    private Client client;
    private String id;
    private String name;
    private String phone;
    private String address;
    private String note;
    private OnClientDialogOkListener onClientDialogOkListener;
    private TextInputEditText editNum;
    private TextInputEditText editName;
    private TextInputEditText editPhone;
    private TextInputEditText editAddress;
    private TextInputEditText editNote;
    private Context mContext;

    public ClientDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        setContentView(R.layout.dialog_clinet);
        initView();
    }

    private void initView() {
        editNum = findViewById(R.id.txt_edit_num);
        editName = findViewById(R.id.txt_edit_name);
        editPhone = findViewById(R.id.txt_edit_phone);
        editAddress = findViewById(R.id.txt_edit_address);
        editNote = findViewById(R.id.txt_edit_note);
        txtOk = findViewById(R.id.txt_ok);
        txtCancel = findViewById(R.id.txx_cancel);
        txtOk.setOnClickListener(this);
        txtCancel.setOnClickListener(this);


        editNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id =  s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = (String) s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address = (String) s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                note = (String) s.toString();
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
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.showSoftInput(editNum, 0);
//        }
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (onClientDialogOkListener != null) {
            onClientDialogOkListener.onOkListener(client);
        }
    }


    @Override
    public void onClick(View v) {
        // 取消和完成的点击事件
        if (v == txtCancel) {
            clear();
            dismiss();
        } else if (v == txtOk){
            if (!isComplete(id, name, phone, address)) {
                Toast.makeText(mContext, "请填写完整！", Toast.LENGTH_SHORT).show();
            } else {
                client = new Client(id, name, phone, address, note);
                clear();
                dismiss();
            }
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
        return true;
    }

    private void clear() {
        id = name = phone = address = note = null;
        editName.setText("");
        editNum.setText("");
        editAddress.setText("");
        editPhone.setText("");
        editNote.setText("");
    }

    public void setOnClientDialogOkListener(OnClientDialogOkListener onClientDialogOkListener) {
        this.onClientDialogOkListener = onClientDialogOkListener;
    }
}
