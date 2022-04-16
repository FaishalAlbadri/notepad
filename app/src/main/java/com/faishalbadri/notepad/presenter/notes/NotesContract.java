package com.faishalbadri.notepad.presenter.notes;
import com.faishalbadri.notepad.base.BasePresenter;
import com.faishalbadri.notepad.data.DataNotes;

import java.util.List;

public class NotesContract {

    public interface notesView {

        void onSuccessGetNotes(List<DataNotes> dataNotes);

        void onSuccessDeleteNotes();

        void onSuccessUpdateNotes();

        void onSuccessPinNotes();

        void onSuccessUnpinNotes();

    }

    public interface notesPresenter extends BasePresenter<notesView> {
        void getNotes(int id);

        void deleteNotes(int id);

        void updateNotes(int id, String title, String desc);

        void pinNotes(int id);

        void unpinNotes(int id);
    }

}
