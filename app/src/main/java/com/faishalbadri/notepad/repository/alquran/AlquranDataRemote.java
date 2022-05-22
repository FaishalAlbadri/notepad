package com.faishalbadri.notepad.repository.alquran;

import android.content.Context;
import android.database.Observable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.remote.APIClient;
import com.faishalbadri.notepad.api.remote.APIInterface;
import com.faishalbadri.notepad.api.remote.Server;
import com.faishalbadri.notepad.data.alquran.QuranData;
import com.faishalbadri.notepad.data.alquran.QuranItem;
import com.faishalbadri.notepad.data.alquran.QuranResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlquranDataRemote implements AlquranDataResource {

    private APIInterface apiInterface;
    private Call<QuranResponse> alquranResponseCall;

    public AlquranDataRemote(Context context) {
        apiInterface = APIClient.getRetrofit(context).create(APIInterface.class);
    }

    @Override
    public void getAlquranAutoComplete(String key, @NonNull @NotNull AlquranGetCallback alquranGetCallback) {
        Log.i("data", String.valueOf(new QuranData(key)));
        alquranResponseCall = apiInterface.quran(new QuranData(key));
        alquranResponseCall.enqueue(new Callback<QuranResponse>() {
            @Override
            public void onResponse(Call<QuranResponse> call, Response<QuranResponse> response) {
                try {
                    int countData = response.body().getResult().size();
                    if (countData > 0) {
                        QuranResponse quranResponse = response.body();
                        List<QuranItem> quranItems = quranResponse.getResult();
                        alquranGetCallback.onSuccessAlquranAutoComplete(quranItems);
                    } else {
                        alquranGetCallback.onErrorAlquranAutoComplete("Data kosong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<QuranResponse> call, Throwable t) {
                alquranGetCallback.onErrorAlquranAutoComplete(Server.CHECK_INTERNET_CONNECTION);
            }
        });
    }
}
