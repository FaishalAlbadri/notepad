package com.faishalbadri.notepad.presenter.splashscreen;


import com.faishalbadri.notepad.repository.splashscreen.SplashScreenDataResource;
import com.faishalbadri.notepad.repository.splashscreen.SplashScreenRepository;

public class SplashScreenPresenter implements SplashScreenContract.splashScreenresenter {

    private SplashScreenRepository splashScreenRepository;
    private SplashScreenContract.splashScreenView splashScreenView;

    public SplashScreenPresenter(SplashScreenRepository splashScreenRepository) {
        this.splashScreenRepository = splashScreenRepository;
    }

    @Override
    public void getNotes() {
        splashScreenRepository.getNotes(() -> splashScreenView.onSuccessGetNotes());
    }

    @Override
    public void onAttachView(SplashScreenContract.splashScreenView view) {
        splashScreenView = view;
    }

    @Override
    public void onDettachView() {

    }
}
