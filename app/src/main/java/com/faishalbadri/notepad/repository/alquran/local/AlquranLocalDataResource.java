package com.faishalbadri.notepad.repository.alquran.local;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.data.DataAlquranAyat;
import com.faishalbadri.notepad.data.DataAlquranSurat;

import java.util.List;

public interface AlquranLocalDataResource {

    void getSurat(@NonNull SuratGetCallback suratGetCallback);

    void getAyat(String surat, int verse, @NonNull AyatGetCallback ayatGetCallback);

    interface SuratGetCallback {
        void oSuccessAlquranSurat(List<DataAlquranSurat> dataAlquranSurats);
    }

    interface AyatGetCallback {
        void oSuccessAlquranAyat(List<DataAlquranAyat> dataAlquranAyats);
    }
}
