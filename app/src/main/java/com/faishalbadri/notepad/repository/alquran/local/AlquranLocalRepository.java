package com.faishalbadri.notepad.repository.alquran.local;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class AlquranLocalRepository implements AlquranLocalDataResource {

    private AlquranLocalDataResource alquranLocalDataResource;

    public AlquranLocalRepository(AlquranLocalDataResource alquranLocalDataResource) {
        this.alquranLocalDataResource = alquranLocalDataResource;
    }

    @Override
    public void getSurat(@NonNull @NotNull SuratGetCallback suratGetCallback) {
        alquranLocalDataResource.getSurat(suratGetCallback);
    }

    @Override
    public void getAyat(String surat, int verse, @NonNull @NotNull AyatGetCallback ayatGetCallback) {
        alquranLocalDataResource.getAyat(surat, verse, ayatGetCallback);
    }
}
