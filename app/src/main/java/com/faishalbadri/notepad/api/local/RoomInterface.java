package com.faishalbadri.notepad.api.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.util.TimestampConverter;

import java.util.Date;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomInterface {

    @Insert(onConflict = REPLACE)
    void addNotes(DataNotes dataNotes);

    @Delete
    void deleteNotes(DataNotes dataNotes);

    @Query("SELECT * FROM note ORDER BY pinned DESC, notes_date DESC")
    List<DataNotes> getNotesAll();

    @Query("SELECT * FROM note ORDER BY notes_date DESC LIMIT 1")
    List<DataNotes> getLastNotes();

    @Query("SELECT * FROM note WHERE notes_title LIKE :key OR notes_desc LIKE :key ORDER BY pinned DESC, notes_date DESC")
    List<DataNotes> searchNotes(String key);

    @TypeConverters(TimestampConverter.class)
    @Query("UPDATE note SET pinned = 1, notes_date = :date WHERE id_notes = :id")
    void pinNotes(int id, Date date);

    @TypeConverters(TimestampConverter.class)
    @Query("UPDATE note SET pinned = 0, notes_date = :date WHERE id_notes = :id")
    void unpinNotes(int id, Date date);
}
