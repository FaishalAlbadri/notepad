package com.faishalbadri.notepad.repository.splashscreen;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataAlquranAyat;
import com.faishalbadri.notepad.data.DataAlquranSurat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreenDataLocal implements SplashScreenDataResource {

    private RoomClient database;
    private Context context;

    public SplashScreenDataLocal(RoomClient database, Context context) {
        this.database = database;
        this.context = context;
    }

    @Override
    public void getNotes(@NonNull @NotNull NotesGetCallback notesGetCallback) {

        int size = database.roomInterface().getAlquranSuratAll().size();
        int sizeayat = database.roomInterface().getAlquranAyatAll().size();

        Log.i("size", String.valueOf(size));
        Log.i("sizeayat", String.valueOf(sizeayat));

        if (size == 0) {
            try {
                JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
                JSONArray jsonArray = jsonObject.getJSONArray("alquran");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectsurah = jsonArray.getJSONObject(i);

                    int id = Integer.parseInt(jsonObjectsurah.getString("id"));
                    String name = jsonObjectsurah.getString("name");
                    JSONArray jsonArrayAyat = jsonObjectsurah.getJSONArray("ayat");
                    for (int j = 0; j < jsonArrayAyat.length(); j++) {
                        JSONObject jsonObjectAyat = jsonArrayAyat.getJSONObject(j);

                        int idAyat = Integer.parseInt(jsonObjectAyat.getString("id"));
                        String idSurah = jsonObjectAyat.getString("id_surah");
                        String verse = jsonObjectAyat.getString("verse");
                        String ayatIndo = jsonObjectAyat.getString("ayat_indo");
                        Log.i("ayat", idAyat +  ". " + ayatIndo);

                        DataAlquranAyat dataAlquranAyat = new DataAlquranAyat();
                        dataAlquranAyat.setId(idAyat);
                        dataAlquranAyat.setId_surah(idSurah);
                        dataAlquranAyat.setVerse(verse);
                        dataAlquranAyat.setAyat_indo(ayatIndo);
                        database.roomInterface().addAlquranAyat(dataAlquranAyat);
                    }
                    Log.i("surat", id +  ". " + name);

                    DataAlquranSurat dataAlquranSurat = new DataAlquranSurat();
                    dataAlquranSurat.setId(id);
                    dataAlquranSurat.setName(name);
                    database.roomInterface().addAlquranSurat(dataAlquranSurat);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        notesGetCallback.onSuccessGetNotes();
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("alquran.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
