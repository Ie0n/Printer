package com.daily.jcy.printer.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.daily.jcy.printer.R;
import com.daily.jcy.printer.utils.LogUtils;

public class SettingActivity extends BaseActivity implements Switch.OnCheckedChangeListener {

    private RelativeLayout settingPassword;
    private Switch switchPassword;
    private boolean isOpen;
    private View dialogView;
    private LayoutInflater inflater;
    private AlertDialog.Builder builder;
    private String password;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        inflater = LayoutInflater.from(this);
        initView();
    }

    private void initView() {
        settingPassword = findViewById(R.id.setting_layout_setting_password);
        switchPassword = findViewById(R.id.setting_switch_password);
        settingPassword.setOnClickListener(this);
        switchPassword.setOnCheckedChangeListener(this);

        dialogView = inflater.inflate(R.layout.dialog_edit, null);
        EditText editText = dialogView.findViewById(R.id.edit_password);
        editText.addTextChangedListener(this);

        // Dialog
        builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("请输入密码")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.log("//-", "确认");
                        // 存入数据库操作

                    }
                });
        dialog = builder.create();

    }

    // Switch监听
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // 如果选中,存入数据库，设置flag

        } else {
            // 未选中，设置flag

        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        password = s.toString();
        LogUtils.log("//-", password);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == settingPassword) {
            dialog.show();
            
        }
    }
}
