package com.daily.jcy.printer.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.contract.SettingContract;
import com.daily.jcy.printer.model.data.bean.Login;
import com.daily.jcy.printer.presenter.SettingPresenter;
import com.daily.jcy.printer.utils.callback.OnPasswordDialogDismissListener;
import com.daily.jcy.printer.view.dialog.PasswordDialog;

public class SettingActivity extends BaseActivity implements Switch.OnCheckedChangeListener , SettingContract.View , OnPasswordDialogDismissListener {

    private RelativeLayout settingPassword;
    private Switch switchPassword;
    private String password;
    private SettingContract.Presenter presenter;
    private Login login;
    private boolean isOpen = false;
    private PasswordDialog dialog;
    private static final String TAG = "SettingActivity-oo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initPresenter();
        initView();
        setCustomActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isOpen = presenter.isOpen();
        switchPassword.setChecked(isOpen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initPresenter() {
        presenter = new SettingPresenter();
        presenter.attachView(this);
        password = presenter.getPassword();
        Log.i(TAG, "initPresenter: " + password);
    }

    private void initView() {
        settingPassword = findViewById(R.id.setting_layout_setting_password);
        switchPassword = findViewById(R.id.setting_switch_password);
        settingPassword.setOnClickListener(this);
        switchPassword.setOnCheckedChangeListener(this);

        login =  new Login(Login.LOGIN_ID, false, password);
        dialog = new PasswordDialog(this);
        dialog.setOnPasswordDialogDismissListener(this);
    }

    @Override
    public void onOkListener(String password) {
        if (password != null) {
            this.password = password;
            login.setPassword(password);
            presenter.setPassword(login);
        }
    }


    // Switch监听
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (password == null) {
            Toast.makeText(this, "还未设置密码", Toast.LENGTH_SHORT).show();
            buttonView.setChecked(false);
        } else {
            if (isChecked) {
                // 如果选中,存入数据库，设置flag
                presenter.openPassword(login);
            } else {
                // 未选中，设置flag
                presenter.closePassword(login);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        password = s.toString();
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == settingPassword) {
            dialog.setPassword(password);
            dialog.show();
        }
    }

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        getSupportActionBar().setCustomView(mActionBarView, lp);
        TextView text = mActionBarView.findViewById(R.id.title);
        text.setText("设置");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView back = mActionBarView.findViewById(R.id.pic);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
    }
}
