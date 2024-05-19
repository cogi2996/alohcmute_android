package com.example.instagramapp.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static AuthInterceptor authInterceptor;
    private static OkHttpClient okHttpClient;


    public static Retrofit getRetrofitAuth(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");
        Log.d("RetrofitToken1", token);
        if(!token.equals("")){
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(token))
                    .build();
        }
        else{
            okHttpClient = new OkHttpClient.Builder()
                    .build();
        }
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.56.1:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
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
