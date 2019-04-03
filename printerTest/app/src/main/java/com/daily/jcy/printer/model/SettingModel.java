package com.daily.jcy.printer.model;

import android.util.Log;

import com.daily.jcy.printer.ObjectBox;
import com.daily.jcy.printer.contract.SettingContract;
import com.daily.jcy.printer.model.data.bean.Login;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class SettingModel implements SettingContract.Model {

    private Box<Login> loginBox;

    public SettingModel() {
        loginBox = ObjectBox.getBoxStore().boxFor(Login.class);
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void setResult(String result) {

    }

    // 设置密码
    @Override
    public void setPassword(Login login) {
        loginBox.put(login);
    }

    // 开
    @Override
    public void openPassword(Login login) {
        login.setOpen(true);
        loginBox.put(login);
    }

    // 关
    @Override
    public void closePassword(Login login) {
        login.setOpen(false);
        loginBox.put(login);
    }

    @Override
    public String getPassword() {
        Login login = loginBox.get(Login.LOGIN_ID);
        if (login != null) {
            return login.getPassword();
        } else {
            return null;
        }
    }

    @Override
    public boolean isOpen() {
        Login login = loginBox.get(Login.LOGIN_ID);
        if (login != null) {
            return login.isOpen();
        } else {
            return false;
        }
    }
}
