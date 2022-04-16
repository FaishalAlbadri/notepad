package com.faishalbadri.notepad.repository.notes;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class NotesRepository implements NotesDataResource {

    private NotesDataResource notesDataResource;

    public NotesRepository(NotesDataResource notesDataResource) {
        this.notesDataResource = notesDataResource;
    }

    @Override
    public void getNotes(int id, @NonNull @NotNull NotesGetCallback notesGetCallback) {
        notesDataResource.getNotes(id, notesGetCallback);
    }

    @Override
    public void deleteNotes(int id, @NonNull @NotNull NotesDeleteCallback notesDeleteCallback) {
        notesDataResource.deleteNotes(id, notesDeleteCallback);
    }

    @Override
    public void updateNotes(int id, String title, String desc, @NonNull @NotNull NotesUpdateCallback notesUpdateCallback) {
        notesDataResource.updateNotes(id, title, desc, notesUpdateCallback);
    }

    @Override
    public void pinNotes(int id, @NonNull @NotNull NotesPinCallback notesPinCallback) {
        notesDataResource.pinNotes(id, notesPinCallback);
    }

    @Override
    public void unpinNotes(int id, @NonNull @NotNull NotesUnpinCallback notesUnpinCallback) {
        notesDataResource.unpinNotes(id, notesUnpinCallback);
    }
}
