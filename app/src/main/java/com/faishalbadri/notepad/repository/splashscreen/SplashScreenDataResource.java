package com.faishalbadri.notepad.repository.splashscreen;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.data.DataNotes;

import java.util.List;

public interface SplashScreenDataResource {

    void getNotes(@NonNull NotesGetCallback notesGetCallback);

    interface NotesGetCallback {
        void onSuccessGetNotes();
    }
}
