package com.example.wms_extended.rest_api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static String BASE_URL = "http://216.48.177.253:5050/api/";
    private static String BASE_URL_SITE = "http://bandoiot:5050/api/";
    private static String BASE_URL_SITE_IP = "http://172.20.101.205:5050/api/";


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_SITE_IP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
