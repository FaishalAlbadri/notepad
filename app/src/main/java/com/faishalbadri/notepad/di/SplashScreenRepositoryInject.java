package com.faishalbadri.notepad.di;

import android.content.Context;

import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.repository.splashscreen.SplashScreenDataLocal;
import com.faishalbadri.notepad.repository.splashscreen.SplashScreenRepository;

public class SplashScreenRepositoryInject {
    public static SplashScreenRepository provideTo(RoomClient database, Context context) {
        return new SplashScreenRepository(new SplashScreenDataLocal(database, context));
    }
}
