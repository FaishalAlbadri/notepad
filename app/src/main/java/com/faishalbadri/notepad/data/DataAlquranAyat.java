package com.faishalbadri.notepad.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ayat")
public class DataAlquranAyat implements Serializable {

    @PrimaryKey()
    private int id;

    @ColumnInfo(name = "id_surah")
    private String id_surah;

    @ColumnInfo(name = "verse")
    private String verse;

    @ColumnInfo(name = "ayat_indo")
    private String ayat_indo;

    @Ignore
    public DataAlquranAyat() {
    }

    public DataAlquranAyat(int id, String id_surah, String verse, String ayat_indo) {
        this.id = id;
        this.id_surah = id_surah;
        this.verse = verse;
        this.ayat_indo = ayat_indo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_surah() {
        return id_surah;
    }

    public void setId_surah(String id_surah) {
        this.id_surah = id_surah;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getAyat_indo() {
        return ayat_indo;
    }

    public void setAyat_indo(String ayat_indo) {
        this.ayat_indo = ayat_indo;
    }
}
