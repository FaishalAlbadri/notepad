package com.faishalbadri.notepad.repository.home;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.data.DataNotes;

import java.util.List;

public interface HomeDataResource {

    void getNotes(@NonNull NotesGetCallback notesGetCallback);

    void searchNotes(String key, @NonNull NotesGetCallback notesGetCallback);

    void deleteNotes(DataNotes dataNotes, @NonNull NotesDeleteCallback notesDeleteCallback);

    void pinNotes(int id, @NonNull NotesPinCallback notesPinCallback);

    void unpinNotes(int id, @NonNull NotesUnpinCallback notesUnpinCallback);

    void addNotes(@NonNull NotesAddCallback notesAddCallback);

    void addNotes(String judul, String isi, @NonNull NotesAddCallback notesAddCallback);

    interface NotesAddCallback {
        void onSuccessAddNotes(int id);
        void onSuccessAddNotes(int id, String judul, String isi);
    }

    interface NotesGetCallback {
        void onSuccessGetNotes(List<DataNotes> dataNotes);
    }

    interface NotesDeleteCallback {
        void onSuccessDeleteNotes();
    }

    interface NotesPinCallback {
        void onSuccessPinNotes();
    }

    interface NotesUnpinCallback {
        void onSuccessUnpinNotes();
    }
}
