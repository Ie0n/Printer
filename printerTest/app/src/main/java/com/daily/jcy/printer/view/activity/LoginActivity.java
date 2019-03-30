package com.daily.jcy.printer.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;

import com.daily.jcy.printer.R;

public class LoginActivity extends BaseActivity{

    private TextInputLayout editLayout;
    private TextInputEditText editPassword;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        editLayout = findViewById(R.id.txt_edit_layout);
        editPassword = findViewById(R.id.txt_edit_password);
        editPassword.addTextChangedListener(this);
        // 从数据库取出设置的密码
        password = "1234";
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        if (password.equals(s.toString())) {
            // 正确
            finish();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        super.afterTextChanged(s);
        if (!password.equals(s.toString())) {
            editLayout.setError("密码错误");
        }
    }
}
