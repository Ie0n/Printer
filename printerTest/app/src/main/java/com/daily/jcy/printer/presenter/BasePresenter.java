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
//    public abstract void showResult(String result);
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055
//    public static BasePresenter getInstance() {
//    }

    public abstract void showResult();
<<<<<<< HEAD

=======
>>>>>>> a2437fb7320af0ad1818d88a72852064d8c6e055

    public abstract M createModel();

}

