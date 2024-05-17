package com.example.instagramapp.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitAuth() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            // Add AuthInterceptor to OkHttpClient builder
            okHttpClientBuilder.addInterceptor(new AuthInterceptor());
            OkHttpClient okHttpClient = okHttpClientBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.56.1:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.56.1:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
