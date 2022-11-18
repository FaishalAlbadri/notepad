package com.faishalbadri.notepad.repository.summarization;

import androidx.annotation.NonNull;

public interface SummarizationDataResource {

    void getSummarization(String text, @NonNull SummarizationGetCallback summarizationGetCallback);

    interface SummarizationGetCallback {

        void onSuccessSummarization(String judul, String Isi);
        void onErrorSummarization(String msg);

    }
}
