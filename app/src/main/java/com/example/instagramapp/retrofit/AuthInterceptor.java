package com.example.instagramapp.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJmdWxsTmFtZSI6Ik5ndXnhu4VuIFF14buRYyBUdeG6pW4iLCJ1c2VySWQiOjQxLCJzdWIiOiJiaWRhbmcudHV5bG9hbkBnbWFpbC5jb20iLCJpYXQiOjE3MTU4NzQ1MzQsImV4cCI6MTcxNTk2MDkzNH0.QPO7bp-XeLTSX8SaKO0J9i2ZYlRo3Y07OKgie0XaUH4";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(newRequest);
    }
}

