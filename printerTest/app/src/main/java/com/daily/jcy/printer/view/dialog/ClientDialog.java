package com.daily.jcy.printer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Client;
import com.daily.jcy.printer.utils.callback.OnClientDialogDismissListener;
import com.daily.jcy.printer.utils.message.MessageEvent;

import java.util.regex.Pattern;

public class ClientDialog extends Dialog implements  View.OnClickListener {

    private static final String TAG = "-mm";
    private int mCommand; // 判断是创建还是更新
    private TextView txtOk;
    private TextView txtCancel;
    private Client client;
    private String id;
    private String name;
    private String phone;
    private String address;
    private String note;
    private OnClientDialogDismissListener onClientDialogDismissListener;
    private TextInputEditText editNum;
    private TextInputEditText editName;
    private TextInputEditText editPhone;
    private TextInputEditText editAddress;
    private TextInputEditText editNote;
    private Context mContext;
    private Client beforeClient;

    public ClientDialog(@NonNull Context context, MessageEvent event) {
        super(context);
        setContentView(R.layout.dialog_clinet);
        mContext = context;
        mCommand = event.getMessage();
        Log.i(TAG, "command: " + mCommand);
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

        mTextWatcher();
    }

    // 设置点击item传进来的信息赋值给EditText的Hint
    private void setHintText(TextInputEditText editText,String hint) {
        if (hint != null) {
            editText.setText(hint);
        }
    }
    private void initEditHint() {
        id = String.valueOf(beforeClient.getId());
        name = beforeClient.getName();
        phone = beforeClient.getTel();
        address = beforeClient.getAddress();
        note = beforeClient.getNote();

        setHintText(editNum, id);
        setHintText(editName, name);
        setHintText(editPhone, phone);
        setHintText(editAddress, address);
        setHintText(editNote, note);
    }

    @Override
    public void show() {
        // 每次打开给editNum焦点和弹出软键盘
        editNum.setFocusable(true);
        editNum.setFocusableInTouchMode(true);
        editNum.requestFocus();

        // 修改的操作,给属性和Edit赋初值
        if (mCommand == MessageEvent.UPDATE_CLIENT) {
            initEditHint();
        }
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mCommand == MessageEvent.CREATE_CLIENT) {
            onClientDialogDismissListener.onCreateListener(client);
        } else if (mCommand == MessageEvent.UPDATE_CLIENT) {
            onClientDialogDismissListener.onUpdateListener(client);
        }
    }


    @Override
    public void onClick(View v) {
        // 取消和完成的点击事件
        if (v == txtCancel) {
            dismiss();
            clear();
        } else if (v == txtOk){
            if (!isComplete(id, name, phone, address)) {
                Toast.makeText(mContext, "请填写完整！", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "onClick: " + Long.parseLong(id));
                client = new Client(Long.parseLong(id), name, phone, address, note);
                dismiss();
                clear();
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
        if (!isInteger(strings[0])){
            Toast.makeText(mContext, "请输入正整数", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    private void clear() {
        client = null;
        id = name = phone = address = note = null;
        editName.setText("");
        editNum.setText("");
        editAddress.setText("");
        editPhone.setText("");
        editNote.setText("");
    }

    public void setOnClientDialogDismissListener(OnClientDialogDismissListener onClientDialogDismissListener) {
        this.onClientDialogDismissListener = onClientDialogDismissListener;
    }


    public void setBeforeClient(Client beforeClient) {
        this.beforeClient = beforeClient;
    }

    private void mTextWatcher() {
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
}
