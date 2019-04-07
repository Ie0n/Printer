package com.daily.jcy.printer.presenter;

import com.daily.jcy.printer.contract.DialogContarct;
import com.daily.jcy.printer.model.DialogModel;

public class DialogPresenter extends DialogContarct.Presenter {

    private DialogContarct.Model model;

    public DialogPresenter() {
        model = createModel();
    }

    @Override
    public boolean checkFoodNumber(String id) {
        return model.checkFoodNumber(id);
    }

    @Override
    public boolean checkClientNumber(String id) {
        return model.checkClientNumber(id);
    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showResult() {

    }

    @Override
    public DialogContarct.Model createModel() {
        return new DialogModel();
    }
}
