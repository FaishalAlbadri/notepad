package com.faishalbadri.notepad.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "surat")
public class DataAlquranSurat implements Serializable {

    @PrimaryKey()
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    public DataAlquranSurat() {
    }

    public DataAlquranSurat(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
