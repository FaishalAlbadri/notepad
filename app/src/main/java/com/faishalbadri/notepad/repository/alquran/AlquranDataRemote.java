package com.faishalbadri.notepad.repository.alquran;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.faishalbadri.notepad.api.remote.APIClient;
import com.faishalbadri.notepad.api.remote.APIInterface;
import com.faishalbadri.notepad.api.remote.Server;
import com.faishalbadri.notepad.data.alquran.AlquranItem;
import com.faishalbadri.notepad.data.alquran.AlquranResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlquranDataRemote implements AlquranDataResource {

    private APIInterface apiInterface;
    private Call<AlquranResponse> alquranResponseCall;

    public AlquranDataRemote(Context context) {
        apiInterface = APIClient.getRetrofit(context).create(APIInterface.class);
    }

    @Override
    public void getAlquranAutoComplete(String key, @NonNull @NotNull AlquranGetCallback alquranGetCallback) {
        Log.i("keyword autocomplete", key);
        alquranResponseCall = apiInterface.autoquran(key);
        alquranResponseCall.enqueue(new Callback<AlquranResponse>() {
            @Override
            public void onResponse(Call<AlquranResponse> call, Response<AlquranResponse> response) {
                try {
                    if (response.body().getMsg().equals("Berhasil")) {
                        AlquranResponse alquranResponse = response.body();
                        List<AlquranItem> alquranItems = alquranResponse.getAlquran();
                        alquranGetCallback.onSuccessAlquranAutoComplete(alquranItems);
                    } else {
                        alquranGetCallback.onErrorAlquranAutoComplete("Data kosong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AlquranResponse> call, Throwable t) {
                alquranGetCallback.onErrorAlquranAutoComplete(Server.CHECK_INTERNET_CONNECTION);
            }
        });
    }
}
