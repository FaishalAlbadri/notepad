package com.faishalbadri.notepad.presenter.alquran.local;


import com.faishalbadri.notepad.base.BasePresenter;
import com.faishalbadri.notepad.data.DataAlquranAyat;
import com.faishalbadri.notepad.data.DataAlquranSurat;

import java.util.List;

public class AlquranLocalContract {

    public interface alquranLocalView {

        void oSuccessAlquranSurat(List<DataAlquranSurat> dataAlquranSurats);

        void oSuccessAlquranAyat(List<DataAlquranAyat> dataAlquranAyats);

    }

    public interface alquranLocalPresenter extends BasePresenter<alquranLocalView> {

        void getSurat();

        void getAyat(String surat, int verse);
    }
}
