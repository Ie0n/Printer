package com.daily.jcy.printer.presenter;

import com.daily.jcy.printer.view.IMainView;

public class SearchPresenterImpl implements ISearchPresenter{

    private IMainView mainView;

    public SearchPresenterImpl(IMainView view){
        mainView = view;
    }

    @Override
    public void doSearch(Long userId) {

    }
}
