package com.faishalbadri.notepad.repository.notes;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.local.RoomClient;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class NotesDataLocal implements NotesDataResource {

    private RoomClient database;

    public NotesDataLocal(RoomClient database) {
        this.database = database;
    }

    @Override
    public void getNotes(int id,@NonNull @NotNull NotesGetCallback notesGetCallback) {
        notesGetCallback.onSuccessGetNotes(database.roomInterface().getNotesById(id));
    }

    @Override
    public void deleteNotes(int id, @NonNull @NotNull NotesDeleteCallback notesDeleteCallback) {
        database.roomInterface().deleteByIdNotes(id);
        notesDeleteCallback.onSuccessDeleteNotes();
    }

    @Override
    public void updateNotes(int id, String title, String desc, @NonNull @NotNull NotesUpdateCallback notesUpdateCallback) {
        database.roomInterface().updateNotes(id, title, desc, Calendar.getInstance().getTime());
        notesUpdateCallback.onSuccessUpdateNotes();
    }

    @Override
    public void pinNotes(int id, @NonNull @NotNull NotesPinCallback notesPinCallback) {
        database.roomInterface().pinNotes(id, Calendar.getInstance().getTime());
        notesPinCallback.onSuccessPinNotes();
    }

    @Override
    public void unpinNotes(int id, @NonNull @NotNull NotesUnpinCallback notesUnpinCallback) {
        database.roomInterface().unpinNotes(id, Calendar.getInstance().getTime());
        notesUnpinCallback.onSuccessUnpinNotes();
    }
}
