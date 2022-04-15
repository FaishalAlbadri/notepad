package com.faishalbadri.notepad.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.faishalbadri.notepad.util.TimestampConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "note")
public class DataNotes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_notes;

    @ColumnInfo(name = "notes_title")
    private String notes_title;

    @ColumnInfo(name = "notes_desc")
    private String notes_desc;

    @ColumnInfo(name = "notes_date")
    @TypeConverters(TimestampConverter.class)
    private Date dates;

    @ColumnInfo(name = "pinned")
    private int pinned;

    public DataNotes() {
    }

    public DataNotes(int id_notes, String notes_title, String notes_desc, Date dates, int pinned) {
        this.id_notes = id_notes;
        this.notes_title = notes_title;
        this.notes_desc = notes_desc;
        this.dates = dates;
        this.pinned = pinned;
    }

    public int getId_notes() {
        return id_notes;
    }

    public void setId_notes(int id_notes) {
        this.id_notes = id_notes;
    }

    public String getNotes_title() {
        return notes_title;
    }

    public void setNotes_title(String notes_title) {
        this.notes_title = notes_title;
    }

    public String getNotes_desc() {
        return notes_desc;
    }

    public void setNotes_desc(String notes_desc) {
        this.notes_desc = notes_desc;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }
}
