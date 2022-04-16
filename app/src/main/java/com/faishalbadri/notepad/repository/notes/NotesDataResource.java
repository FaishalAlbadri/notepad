package com.faishalbadri.notepad.repository.notes;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.data.DataNotes;

import java.util.Date;
import java.util.List;

public interface NotesDataResource {

    void getNotes(int id, @NonNull NotesGetCallback notesGetCallback);

    void deleteNotes(int id, @NonNull NotesDeleteCallback notesDeleteCallback);

    void updateNotes(int id, String title, String desc, @NonNull NotesUpdateCallback notesUpdateCallback);

    void pinNotes(int id, @NonNull NotesPinCallback notesPinCallback);

    void unpinNotes(int id, @NonNull NotesUnpinCallback notesUnpinCallback);

    interface NotesGetCallback {
        void onSuccessGetNotes(List<DataNotes> dataNotes);
    }

    interface NotesDeleteCallback {
        void onSuccessDeleteNotes();
    }

    interface NotesUpdateCallback {
        void onSuccessUpdateNotes();
    }

    interface NotesPinCallback {
        void onSuccessPinNotes();
    }

    interface NotesUnpinCallback {
        void onSuccessUnpinNotes();
    }
}
