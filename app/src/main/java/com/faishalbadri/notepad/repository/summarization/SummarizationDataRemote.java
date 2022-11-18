package com.faishalbadri.notepad.repository.summarization;

import android.content.Context;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.remote.APIClient;
import com.faishalbadri.notepad.api.remote.APIInterface;
import com.faishalbadri.notepad.api.remote.Server;
import com.faishalbadri.notepad.data.text.SummarizationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummarizationDataRemote implements SummarizationDataResource {

    private APIInterface apiInterface;
    private Call<SummarizationResponse> summarizationResponseCall;

    public SummarizationDataRemote(Context context) {
        apiInterface = APIClient.getRetrofit(context).create(APIInterface.class);
    }

    @Override
    public void getSummarization(String text, @NonNull SummarizationGetCallback summarizationGetCallback) {
        summarizationResponseCall = apiInterface.summarizationText(Server.SUMMARIZATION_URL, text);
        summarizationResponseCall.enqueue(new Callback<SummarizationResponse>() {
            @Override
            public void onResponse(Call<SummarizationResponse> call, Response<SummarizationResponse> response) {
                try {
                    if (response.body().getResult().size() > 0) {
                        SummarizationResponse summarizationResponse = response.body();
                        List<String> sumList = summarizationResponse.getResult();
                        String hasil = sumList.get(0);
                        String[] strList = hasil.split(" ");
                        String judul = strList[0] + " " + strList[1] + " " + strList[2];
                        summarizationGetCallback.onSuccessSummarization(judul, hasil);
                    } else {
                        summarizationGetCallback.onErrorSummarization("Can't Summarization Text");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SummarizationResponse> call, Throwable t) {
                summarizationGetCallback.onErrorSummarization(Server.CHECK_INTERNET_CONNECTION);
            }
        });
    }
}
