package com.faishalbadri.notepad.api.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.faishalbadri.notepad.data.DataNotes;

@Database(entities = {DataNotes.class}, version = 1)
public abstract class RoomClient extends RoomDatabase {

    private static RoomClient database;

    private static String DATABASE_NAME = "notesapp";

    public synchronized static RoomClient getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomClient.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract RoomInterface roomInterface();
}
