package com.faishalbadri.notepad.presenter.alquran;

import com.faishalbadri.notepad.data.alquran.AlquranItem;
import com.faishalbadri.notepad.repository.alquran.AlquranDataResource;
import com.faishalbadri.notepad.repository.alquran.AlquranRepository;

import java.util.List;

public class AlquranPresenter implements AlquranContract.alquranPresenter {

    private AlquranRepository alquranRepository;
    private AlquranContract.alquranView alquranView;

    public AlquranPresenter(AlquranRepository alquranRepository) {
        this.alquranRepository = alquranRepository;
    }

    @Override
    public void getAlquranAutoComplete(String key) {
        alquranRepository.getAlquranAutoComplete(key, new AlquranDataResource.AlquranGetCallback() {
            @Override
            public void onSuccessAlquranAutoComplete(List<AlquranItem> alquranItems) {
                alquranView.onSuccessAlquranAutoComplete(alquranItems);
            }

            @Override
            public void onErrorAlquranAutoComplete(String msg) {
                alquranView.onErrorAlquranAutoComplete(msg);
            }
        });
    }

    @Override
    public void onAttachView(AlquranContract.alquranView view) {
        alquranView = view;
    }

    @Override
    public void onDettachView() {

    }
}
