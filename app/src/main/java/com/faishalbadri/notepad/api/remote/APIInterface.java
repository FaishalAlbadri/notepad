package com.faishalbadri.notepad.api.remote;

import com.faishalbadri.notepad.data.text.SummarizationResponse;
import com.faishalbadri.notepad.data.alquran.QuranData;
import com.faishalbadri.notepad.data.alquran.QuranResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIInterface {

    @POST("qurans/retrieve/")
    Call<QuranResponse> quran(
            @Body final QuranData quranData
    );

    @FormUrlEncoded
    @POST
    Call<SummarizationResponse> summarizationText(
            @Url String fullURL,
            @Field("text") String text
    );
}