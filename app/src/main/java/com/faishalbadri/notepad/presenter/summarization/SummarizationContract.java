package com.faishalbadri.notepad.presenter.summarization;

import com.faishalbadri.notepad.base.BasePresenter;

public class SummarizationContract {

    public interface summarizationView {
        void onSuccessSummarization(String judul, String Isi);

        void onErrorSummarization(String msg);
    }

    public interface summarizationPresenter extends BasePresenter<summarizationView> {
        void getSummarization(String text);
    }
}
