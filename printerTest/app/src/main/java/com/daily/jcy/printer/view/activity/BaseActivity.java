package com.daily.jcy.printer.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.presenter.BasePresenter;
import com.daily.jcy.printer.utils.LogUtils;
import com.daily.jcy.printer.view.BaseView;

import java.util.regex.Pattern;

public class BaseActivity extends AppCompatActivity implements TextWatcher, BaseView , View.OnClickListener,View.OnFocusChangeListener {

    private BasePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }


    public String editTextClear(EditText editText, String string) {
        editText.setText("");
        string = null;
        return null;
    }

    // 判断是为为整数
    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    @Override
    public void showResult(String text) {
    }


    // TextWatcher接口
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }


}
