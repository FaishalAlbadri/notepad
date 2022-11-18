package com.faishalbadri.notepad.repository.summarization;

import androidx.annotation.NonNull;

public class SummarizationRepository implements SummarizationDataResource {

    private SummarizationDataResource summarizationDataResource;

    public SummarizationRepository(SummarizationDataResource summarizationDataResource) {
        this.summarizationDataResource = summarizationDataResource;
    }

    @Override
    public void getSummarization(String text, @NonNull SummarizationGetCallback summarizationGetCallback) {
        summarizationDataResource.getSummarization(text, summarizationGetCallback);
    }
}
