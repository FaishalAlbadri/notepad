package com.faishalbadri.notepad.repository.alquran;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class AlquranRepository implements AlquranDataResource {

    private AlquranDataResource alquranDataResource;

    public AlquranRepository(AlquranDataResource alquranDataResource) {
        this.alquranDataResource = alquranDataResource;
    }

    @Override
    public void getAlquranAutoComplete(String key, @NonNull @NotNull AlquranGetCallback alquranGetCallback) {
        alquranDataResource.getAlquranAutoComplete(key, alquranGetCallback);
    }
}
