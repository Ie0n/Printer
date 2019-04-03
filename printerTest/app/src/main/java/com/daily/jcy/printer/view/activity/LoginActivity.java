package com.daily.jcy.printer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.data.bean.Login;

import io.objectbox.Box;

public class LoginActivity extends BaseActivity {

    private TextInputLayout editLayout;
    private TextInputEditText editPassword;
    private String password= "";
    private Box<Login> loginBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Login login = loginBox.get(Login.LOGIN_ID);
        // 是null说明还没设置过
        if (login == null) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (!login.isOpen()) {
            // 如果设置了但是没开
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void initView() {
        editLayout = findViewById(R.id.txt_edit_layout);
        editPassword = findViewById(R.id.txt_edit_password);
        editPassword.addTextChangedListener(this);

        loginBox = ObjectBox.getBoxStore().boxFor(Login.class);
        Login login = loginBox.get(Login.LOGIN_ID);
        if (login != null && login.getPassword() != null) {
            password = login.getPassword();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        if (password.equals(s.toString())) {
            // 正确
            startActivity(new Intent(this, MainActivity.class));
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
