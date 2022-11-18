package com.faishalbadri.notepad.presenter.summarization;

import com.faishalbadri.notepad.repository.summarization.SummarizationDataResource;
import com.faishalbadri.notepad.repository.summarization.SummarizationRepository;

public class SummarizationPresenter implements SummarizationContract.summarizationPresenter {

    private SummarizationRepository summarizationRepository;
    private SummarizationContract.summarizationView summarizationView;

    public SummarizationPresenter(SummarizationRepository summarizationRepository) {
        this.summarizationRepository = summarizationRepository;
    }

    @Override
    public void getSummarization(String text) {
        summarizationRepository.getSummarization(text, new SummarizationDataResource.SummarizationGetCallback() {
            @Override
            public void onSuccessSummarization(String judul, String Isi) {
                summarizationView.onSuccessSummarization(judul, Isi);
            }

            @Override
            public void onErrorSummarization(String msg) {
                summarizationView.onErrorSummarization(msg);
            }
        });
    }

    @Override
    public void onAttachView(SummarizationContract.summarizationView view) {
        summarizationView = view;
    }

    @Override
    public void onDettachView() {

    }
}
