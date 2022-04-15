package com.faishalbadri.notepad.presenter.home;

import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.repository.home.HomeRepository;

public class HomePresenter implements HomeContract.homePresenter {

    private HomeRepository homeRepository;
    private HomeContract.homeView homeView;

    public HomePresenter(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @Override
    public void getNotes() {
        homeRepository.getNotes(dataNotes -> homeView.onSuccessGetNotes(dataNotes));
    }

    @Override
    public void searchNotes(String key) {
        homeRepository.searchNotes(key, dataNotes -> homeView.onSuccessGetNotes(dataNotes));
    }

    @Override
    public void deleteNotes(DataNotes dataNotes) {
        homeRepository.deleteNotes(dataNotes, () -> homeView.onSuccessDeleteNotes());
    }

    @Override
    public void pinNotes(int id) {
        homeRepository.pinNotes(id, () -> homeView.onSuccessPinNotes());
    }

    @Override
    public void unpinNotes(int id) {
        homeRepository.unpinNotes(id, () -> homeView.onSuccessUnpinNotes());
    }

    @Override
    public void addNotes() {
        homeRepository.addNotes(id -> homeView.onSuccessAddNotes(id));
    }

    @Override
    public void onAttachView(HomeContract.homeView view) {
        homeView = view;
    }

    @Override
    public void onDettachView() {

    }
}
