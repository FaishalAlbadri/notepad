package com.faishalbadri.notepad.presenter.notes;

import com.faishalbadri.notepad.repository.notes.NotesRepository;

public class NotesPresenter implements NotesContract.notesPresenter {

    private NotesRepository notesRepository;
    private NotesContract.notesView notesView;

    public NotesPresenter(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void getNotes(int id) {
        notesRepository.getNotes(id, dataNotes -> notesView.onSuccessGetNotes(dataNotes));
    }

    @Override
    public void deleteNotes(int id) {
        notesRepository.deleteNotes(id, () -> notesView.onSuccessDeleteNotes());
    }

    @Override
    public void updateNotes(int id, String title, String desc) {
        notesRepository.updateNotes(id, title, desc, () -> notesView.onSuccessUpdateNotes());
    }

    @Override
    public void pinNotes(int id) {
        notesRepository.pinNotes(id, () -> notesView.onSuccessPinNotes());
    }

    @Override
    public void unpinNotes(int id) {
        notesRepository.unpinNotes(id, () -> notesView.onSuccessUnpinNotes());
    }

    @Override
    public void onAttachView(NotesContract.notesView view) {
        notesView = view;
    }

    @Override
    public void onDettachView() {

    }
}
