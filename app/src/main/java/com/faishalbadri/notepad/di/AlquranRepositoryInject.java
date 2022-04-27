package com.faishalbadri.notepad.di;


import android.content.Context;

import com.faishalbadri.notepad.repository.alquran.AlquranDataRemote;
import com.faishalbadri.notepad.repository.alquran.AlquranRepository;

public class AlquranRepositoryInject {
    public static AlquranRepository provideTo(Context context) {
        return new AlquranRepository(new AlquranDataRemote(context));
    }
}
