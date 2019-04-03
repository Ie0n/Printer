package com.daily.jcy.printer.presenter;

import com.daily.jcy.printer.contract.SettingContract;
import com.daily.jcy.printer.model.SettingModel;
import com.daily.jcy.printer.model.data.bean.Login;

public class SettingPresenter extends SettingContract.Presenter {

    private SettingContract.View mView;
    private SettingContract.Model mModel;

    @Override
    public void attachView(SettingContract.View view) {
        super.attachView(view);
        mView = view;
        mModel = createModel();
    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showResult() {

    }

    @Override
    public SettingContract.Model createModel() {
        return new SettingModel();
    }

    @Override
    public void setPassword(Login login) {
        mModel.setPassword(login);
    }

    @Override
    public void openPassword(Login login) {
        mModel.openPassword(login);
    }

    @Override
    public void closePassword(Login login) {
        mModel.closePassword(login);
    }

    @Override
    public boolean isOpen() {
        return mModel.isOpen();
    }

    @Override
    public String getPassword() {
        return mModel.getPassword();
    }
}
