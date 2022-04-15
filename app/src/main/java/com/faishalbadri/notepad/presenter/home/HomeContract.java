package com.faishalbadri.notepad.presenter.home;


import com.faishalbadri.notepad.base.BasePresenter;
import com.faishalbadri.notepad.data.DataNotes;

import java.util.List;

public class HomeContract {

    public interface homeView {
        void onSuccessGetNotes(List<DataNotes> dataNotes);

        void onSuccessDeleteNotes();

        void onSuccessPinNotes();

        void onSuccessUnpinNotes();

        void onSuccessAddNotes(int id);
    }

    public interface homePresenter extends BasePresenter<homeView> {
        void getNotes();

        void searchNotes(String key);

        void deleteNotes(DataNotes dataNotes);

        void pinNotes(int id);

        void unpinNotes(int id);

        void addNotes();
    }

}
