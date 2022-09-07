package com.faishalbadri.notepad.presenter.alquran.local;

import com.faishalbadri.notepad.repository.alquran.local.AlquranLocalRepository;

public class AlquranLocalPresenter implements AlquranLocalContract.alquranLocalPresenter {

    private AlquranLocalContract.alquranLocalView alquranLocalView;
    private AlquranLocalRepository alquranLocalRepository;

    public AlquranLocalPresenter(AlquranLocalRepository alquranLocalRepository) {
        this.alquranLocalRepository = alquranLocalRepository;
    }

    @Override
    public void getSurat() {
        alquranLocalRepository.getSurat(dataAlquranSurats -> alquranLocalView.oSuccessAlquranSurat(dataAlquranSurats));
    }

    @Override
    public void getAyat(String surat, int verse) {
        alquranLocalRepository.getAyat(surat, verse, dataAlquranAyats -> alquranLocalView.oSuccessAlquranAyat(dataAlquranAyats));
    }

    @Override
    public void onAttachView(AlquranLocalContract.alquranLocalView view) {
        alquranLocalView = view;
    }

    @Override
    public void onDettachView() {

    }
}
