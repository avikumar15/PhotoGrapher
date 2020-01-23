package com.example.photographer.apiservice;

import com.example.photographer.model.MathpixRequest;
import com.example.photographer.model.MathpixResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Muskan Hussain on 13-01-2020
 */
public interface ApiService {
    @Headers("Content-Type: application/json")

    @POST("v3/latex")
    Call<MathpixResponse> getLatex(@Header("app_id") String appId,
                                   @Header("app_key") String appKey,
                                   @Body MathpixRequest mathpixRequest);
}
