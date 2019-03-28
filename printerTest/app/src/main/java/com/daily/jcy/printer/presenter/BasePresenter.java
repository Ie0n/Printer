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

    public abstract void showResult();

    public abstract M createModel();

}

