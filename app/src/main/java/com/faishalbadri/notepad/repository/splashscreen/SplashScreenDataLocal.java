package com.faishalbadri.notepad.repository.splashscreen;

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
            DataNotes dataNotes = new DataNotes();
            dataNotes.setNotes_title("Catatan faishal 1");
            dataNotes.setNotes_desc("Catatan Hadist Kiamat Besar asdasd asdas asdas asdas asdasd as ds ad sa asdasdasdas asdassadas as dasdasdsad asdasdasdas asdasdasdasd asdasdasdas aasdasd asdasdasdsa asdsa asdasdasd asdsadsa asdasdas sadasdasd asdasdas asdasda asdasdsa asdsaa");
            dataNotes.setDates(Calendar.getInstance().getTime());
            dataNotes.setPinned(0);
            database.roomInterface().addNotes(dataNotes);
            dataNotes.setNotes_title("Catatan udin 1");
            dataNotes.setNotes_desc("Catatan Hadist Kiamat Besar asdasd asdas asdas asdas asdasd as ds ad sa asdasdasdas asdassadas as dasdasdsad asdasdasdas asdasdasdasd asdasdasdas aasdasd asdasdasdsa asdsa asdasdasd asdsadsa asdasdas sadasdasd asdasdas asdasda asdasdsa asdsaa");
            dataNotes.setDates(Calendar.getInstance().getTime());
            dataNotes.setPinned(1);
            database.roomInterface().addNotes(dataNotes);
            dataNotes.setNotes_title(" Catatan mamat 2");
            dataNotes.setNotes_desc("Catatan Hadist Kiamat Besar asdasd asdas asdas dasdasd asdasdasdas aasdasd asdasdasdsa asdsa asdasdasd asdsadsa asdasdas sadasdasd asdasdas asdasda asdasdsa asdsaa");
            dataNotes.setDates(Calendar.getInstance().getTime());
            dataNotes.setPinned(0);
            database.roomInterface().addNotes(dataNotes);
            dataNotes.setNotes_title("Catatan Hadist Kiamat Besar dan Kiamat kecil serta contoh contohnya 2");
            dataNotes.setNotes_desc("Catatan Hadist Kiamat Besar asdasd asdas asdas asdas asdasd as ds ad sa");
            dataNotes.setDates(Calendar.getInstance().getTime());
            dataNotes.setPinned(1);
            database.roomInterface().addNotes(dataNotes);
        }
        notesGetCallback.onSuccessGetNotes();
    }
}
