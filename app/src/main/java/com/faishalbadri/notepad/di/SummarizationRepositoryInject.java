package com.faishalbadri.notepad.di;

import android.content.Context;

import com.faishalbadri.notepad.repository.summarization.SummarizationDataRemote;
import com.faishalbadri.notepad.repository.summarization.SummarizationRepository;

public class SummarizationRepositoryInject {

    public static SummarizationRepository provideTo(Context context) {
        return new SummarizationRepository(new SummarizationDataRemote(context));
    }
}
