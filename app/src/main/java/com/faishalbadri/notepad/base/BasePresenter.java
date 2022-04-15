package com.faishalbadri.notepad.base;

public interface BasePresenter<T> {

    void onAttachView(T view);

    void onDettachView();

}