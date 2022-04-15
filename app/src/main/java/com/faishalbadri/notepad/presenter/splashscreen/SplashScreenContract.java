package com.faishalbadri.notepad.presenter.splashscreen;

import com.faishalbadri.notepad.base.BasePresenter;
import com.faishalbadri.notepad.data.DataNotes;

import java.util.List;

public class SplashScreenContract {

    public interface splashScreenView {
        void onSuccessGetNotes();
    }

    public interface splashScreenresenter extends BasePresenter<splashScreenView> {
        void getNotes();
    }
}
