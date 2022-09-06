package com.faishalbadri.notepad.api.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.faishalbadri.notepad.data.DataAlquranAyat;
import com.faishalbadri.notepad.data.DataAlquranSurat;
import com.faishalbadri.notepad.data.DataNotes;
import com.faishalbadri.notepad.util.TimestampConverter;

import java.util.Date;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomInterface {

    @Insert(onConflict = REPLACE)
    void addAlquranSurat(DataAlquranSurat dataAlquranSurat);

    @Insert(onConflict = REPLACE)
    void addAlquranAyat(DataAlquranAyat dataAlquranAyat);

    @Insert(onConflict = REPLACE)
    void addNotes(DataNotes dataNotes);

    @Delete
    void deleteNotes(DataNotes dataNotes);

    @Query("DELETE FROM note WHERE id_notes = :id")
    void deleteByIdNotes(int id);

    @Query("SELECT * FROM note ORDER BY pinned DESC, notes_date DESC")
    List<DataNotes> getNotesAll();

    @Query("SELECT * FROM surat ORDER BY id ASC")
    List<DataAlquranSurat> getAlquranSuratAll();

    @Query("SELECT * FROM ayat ORDER BY id ASC")
    List<DataAlquranAyat> getAlquranAyatAll();

    @Query("SELECT * FROM note ORDER BY notes_date DESC LIMIT 1")
    List<DataNotes> getLastNotes();

    @Query("SELECT * FROM note WHERE id_notes = :id")
    List<DataNotes> getNotesById(int id);

    @Query("SELECT * FROM note WHERE notes_title LIKE :key OR notes_desc LIKE :key ORDER BY pinned DESC, notes_date DESC")
    List<DataNotes> searchNotes(String key);

    @TypeConverters(TimestampConverter.class)
    @Query("UPDATE note SET pinned = 1, notes_date = :date WHERE id_notes = :id")
    void pinNotes(int id, Date date);

    @TypeConverters(TimestampConverter.class)
    @Query("UPDATE note SET pinned = 0, notes_date = :date WHERE id_notes = :id")
    void unpinNotes(int id, Date date);

    @TypeConverters(TimestampConverter.class)
    @Query("UPDATE note SET notes_title = :title, notes_desc = :desc ,notes_date = :date WHERE id_notes = :id")
    void updateNotes(int id, String title, String desc, Date date);
}
