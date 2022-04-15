package com.faishalbadri.notepad.repository.home;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.ui.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

public class HomeDataLocal implements HomeDataResource {

    private RoomClient database;

    public HomeDataLocal(RoomClient database) {
        this.database = database;
    }

    @Override
    public void getNotes(@NonNull @NotNull NotesGetCallback notesGetCallback) {
        notesGetCallback.onSuccessGetNotes(database.roomInterface().getNotesAll());
    }

    @Override
    public void searchNotes(String key, @NonNull @NotNull NotesGetCallback notesGetCallback) {
        String newKey = "%" + key + "%";
        notesGetCallback.onSuccessGetNotes(database.roomInterface().searchNotes(newKey));
    }

    @Override
    public void deleteNotes(DataNotes dataNotes, @NonNull @NotNull NotesDeleteCallback notesDeleteCallback) {
        database.roomInterface().deleteNotes(dataNotes);
        notesDeleteCallback.onSuccessDeleteNotes();
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

    @Override
    public void addNotes(@NonNull @NotNull NotesAddCallback notesAddCallback) {
        DataNotes dataNotes = new DataNotes();
        dataNotes.setNotes_title("");
        dataNotes.setNotes_desc("");
        dataNotes.setDates(Calendar.getInstance().getTime());
        dataNotes.setPinned(0);
        database.roomInterface().addNotes(dataNotes);

        List<DataNotes> list = database.roomInterface().getLastNotes();
        DataNotes newNotes = list.get(0);
        Log.i("new notes", "id terbaru : " + newNotes.getId_notes());
        notesAddCallback.onSuccessAddNotes(newNotes.getId_notes());

//        Handler handler = new Handler();
//        handler.postDelayed(() -> {
//        }, 500);
    }
}
