package com.faishalbadri.notepad.api.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.faishalbadri.notepad.data.DataAlquranAyat;
import com.faishalbadri.notepad.data.DataAlquranSurat;
import com.faishalbadri.notepad.data.DataNotes;

@Database(entities = {DataNotes.class, DataAlquranSurat.class, DataAlquranAyat.class}, version = 2)
public abstract class RoomClient extends RoomDatabase {

    private static RoomClient database;

    private static String DATABASE_NAME = "notesapp";

    public synchronized static RoomClient getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomClient.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract RoomInterface roomInterface();
}
