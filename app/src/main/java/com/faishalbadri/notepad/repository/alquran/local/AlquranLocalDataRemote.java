package com.faishalbadri.notepad.repository.alquran.local;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.data.DataAlquranSurat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlquranLocalDataRemote implements AlquranLocalDataResource {

    private RoomClient database;

    public AlquranLocalDataRemote(RoomClient database) {
        this.database = database;
    }

    @Override
    public void getSurat(@NonNull @NotNull SuratGetCallback suratGetCallback) {
        suratGetCallback.oSuccessAlquranSurat(database.roomInterface().getAlquranSuratAll());
    }

    @Override
    public void getAyat(String surat, int verse, @NonNull @NotNull AyatGetCallback ayatGetCallback) {
        List<DataAlquranSurat> dataAlquranSurats = database.roomInterface().getSuratByName(surat);
        if (dataAlquranSurats.size() > 0) {
            int idSurat = dataAlquranSurats.get(0).getId();
            ayatGetCallback.oSuccessAlquranAyat(database.roomInterface().getAyatBySurat(verse, idSurat));
        }
    }
}
