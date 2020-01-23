package com.example.photographer.apiservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Muskan Hussain on 13-01-2020
 */
public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.mathpix.com/";

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                              .baseUrl(BASE_URL)
                              .addConverterFactory(GsonConverterFactory.create())
                              .build();
        }
        return retrofit;
    }
}
