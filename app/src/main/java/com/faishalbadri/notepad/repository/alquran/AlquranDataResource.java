package com.faishalbadri.notepad.repository.alquran;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.data.alquran.QuranItem;

import java.util.List;

public interface AlquranDataResource {

    void getAlquranAutoComplete(String key, @NonNull AlquranGetCallback alquranGetCallback);

    interface AlquranGetCallback {

        void onSuccessAlquranAutoComplete(List<QuranItem> alquranItems);

        void onErrorAlquranAutoComplete(String msg);
    }
}
