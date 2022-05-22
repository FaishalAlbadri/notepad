package com.faishalbadri.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.faishalbadri.notepad.R;
import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.di.SplashScreenRepositoryInject;
import com.faishalbadri.notepad.presenter.splashscreen.SplashScreenContract;
import com.faishalbadri.notepad.presenter.splashscreen.SplashScreenPresenter;
import com.faishalbadri.notepad.util.IntroManager;

public class SplashScreen extends AppCompatActivity implements SplashScreenContract.splashScreenView {

    private SplashScreenPresenter splashScreenPresenter;
    private IntroManager introManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        splashScreenPresenter = new SplashScreenPresenter(SplashScreenRepositoryInject.provideTo(RoomClient.getInstance(this)));
        splashScreenPresenter.onAttachView(this);
        splashScreenPresenter.getNotes();

        introManager = new IntroManager(this);

    }


    @Override
    public void onSuccessGetNotes() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Log.i("asdasdasdas", String.valueOf(introManager.isFirstTimeLaunch()));
            if (introManager.isFirstTimeLaunch()) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            } else {
                introManager.setFirstTimeLaunch(true);
                startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                finish();
            }

        },3000);
    }
}