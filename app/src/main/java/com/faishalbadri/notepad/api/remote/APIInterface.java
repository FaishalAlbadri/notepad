package com.faishalbadri.notepad.api.remote;

import com.faishalbadri.notepad.data.alquran.AlquranResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("autocomplete")
    Call<AlquranResponse> autoquran(
            @Field("key") String key
    );
}
