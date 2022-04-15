package com.faishalbadri.notepad.repository.splashscreen;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class SplashScreenRepository implements SplashScreenDataResource{

    private SplashScreenDataResource splashScreenDataResource;

    public SplashScreenRepository(SplashScreenDataResource splashScreenDataResource) {
        this.splashScreenDataResource = splashScreenDataResource;
    }

    @Override
    public void getNotes(@NonNull @NotNull NotesGetCallback notesGetCallback) {
        splashScreenDataResource.getNotes(notesGetCallback);
    }
}
