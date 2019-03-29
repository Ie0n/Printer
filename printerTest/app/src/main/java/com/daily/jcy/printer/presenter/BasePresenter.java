package com.daily.jcy.printer.presenter;

import com.daily.jcy.printer.model.BaseModel;
import com.daily.jcy.printer.view.BaseView;

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected V view;
    protected M model;

    public BasePresenter() {
        model = createModel();
    }

    public void attachView(V view) {
        this.view = view;
    }

   public void detachView() {
        this.view = null;
    }

<<<<<<< HEAD
    public abstract void showResult(String result);
=======
//    public static BasePresenter getInstance() {
//    }

    public abstract void showResult();
>>>>>>> 2ced59842afddb468f74fd71e2a07d21bf2bdcda

    public abstract M createModel();

}

