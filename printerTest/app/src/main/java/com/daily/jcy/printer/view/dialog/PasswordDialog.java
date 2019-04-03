package com.daily.jcy.printer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.utils.callback.OnPasswordDialogDismissListener;

import java.util.regex.Pattern;

public class PasswordDialog extends Dialog implements TextWatcher, View.OnClickListener {

    private String password;
    private OnPasswordDialogDismissListener onPasswordDialogDismissListener;
    private Context mContext;
    private EditText editPassword;
    private TextView txtOk;
    private TextView txtCancel;

    public PasswordDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        setContentView(R.layout.dialog_password);
        initView();
    }


    private void initView() {
        editPassword = findViewById(R.id.password_edit);
        editPassword.addTextChangedListener(this);
        txtOk = findViewById(R.id.password_ok);
        txtCancel = findViewById(R.id.password_cancel);
        txtOk.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (onPasswordDialogDismissListener != null) {
            onPasswordDialogDismissListener.onOkListener(password);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        password = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void show() {
        initEdit();
        super.show();
    }

    private void initEdit() {
        if (password != null) {
            editPassword.setText(password);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == txtCancel) {
            clear();
            dismiss();
        } else if (v == txtOk) {
            if (password == null || password.equals("")) {
                Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (!isInteger(password)) {
                Toast.makeText(mContext, "请输入整数", Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
                clear();
            }
        }
    }

    private void clear() {
        editPassword.setText("");
        password = null;
    }

    public void setOnPasswordDialogDismissListener(OnPasswordDialogDismissListener onPasswordDialogDismissListener) {
        this.onPasswordDialogDismissListener = onPasswordDialogDismissListener;
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
