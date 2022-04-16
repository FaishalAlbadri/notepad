package com.faishalbadri.notepad.repository.splashscreen;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataNotes;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class SplashScreenDataLocal implements SplashScreenDataResource {

    private RoomClient database;

    public SplashScreenDataLocal(RoomClient database) {
        this.database = database;
    }

    @Override
    public void getNotes(@NonNull @NotNull NotesGetCallback notesGetCallback) {
        int size = database.roomInterface().getNotesAll().size();

        if (size == 0) {
            Log.i("dummy", "delete dummy data");
        }
        notesGetCallback.onSuccessGetNotes();
    }
}
