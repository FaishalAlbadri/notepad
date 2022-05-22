package com.faishalbadri.notepad.data.alquran;

import com.google.gson.annotations.SerializedName;

public class QuranData {

    @SerializedName("query")
    private String query;

    public QuranData(String query) {
        this.query = query;
    }
}
