package com.faishalbadri.notepad.api.remote;

import com.faishalbadri.notepad.data.alquran.QuranData;
import com.faishalbadri.notepad.data.alquran.QuranResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("qurans/retrieve/")
    Call<QuranResponse> quran(
            @Body final QuranData quranData
    );
}
