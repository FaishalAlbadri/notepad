package com.faishalbadri.notepad.repository.home;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.data.DataNotes;

import org.jetbrains.annotations.NotNull;

public class HomeRepository implements HomeDataResource {

    private HomeDataResource homeDataResource;

    public HomeRepository(HomeDataResource homeDataResource) {
        this.homeDataResource = homeDataResource;
    }

    @Override
    public void getNotes(@NonNull @NotNull NotesGetCallback notesGetCallback) {
        homeDataResource.getNotes(notesGetCallback);
    }

    @Override
    public void searchNotes(String key, @NonNull @NotNull NotesGetCallback notesGetCallback) {
        homeDataResource.searchNotes(key, notesGetCallback);
    }

    @Override
    public void deleteNotes(DataNotes dataNotes, @NonNull @NotNull NotesDeleteCallback notesDeleteCallback) {
        homeDataResource.deleteNotes(dataNotes, notesDeleteCallback);
    }

    @Override
    public void pinNotes(int id, @NonNull @NotNull NotesPinCallback notesPinCallback) {
        homeDataResource.pinNotes(id, notesPinCallback);
    }

    @Override
    public void unpinNotes(int id, @NonNull @NotNull NotesUnpinCallback notesUnpinCallback) {
        homeDataResource.unpinNotes(id, notesUnpinCallback);
    }

    @Override
    public void addNotes(@NonNull @NotNull NotesAddCallback notesAddCallback) {
        homeDataResource.addNotes(notesAddCallback);
    }
}
