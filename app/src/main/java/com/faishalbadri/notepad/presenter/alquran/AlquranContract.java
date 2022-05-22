package com.faishalbadri.notepad.presenter.alquran;

import com.faishalbadri.notepad.base.BasePresenter;
import com.faishalbadri.notepad.data.alquran.QuranItem;

import java.util.List;

public class AlquranContract {

    public interface alquranView {

        void onSuccessAlquranAutoComplete(List<QuranItem> alquranItems);

        void onErrorAlquranAutoComplete(String msg);

    }

    public interface alquranPresenter extends BasePresenter<alquranView> {

        void getAlquranAutoComplete(String key);

    }
}
