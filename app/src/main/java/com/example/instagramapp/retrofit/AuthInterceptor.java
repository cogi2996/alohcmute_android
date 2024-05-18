package com.example.instagramapp.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJmdWxsTmFtZSI6Ik5ndXnhu4VuIFF14buRYyBUdeG6pW4iLCJ1c2VySWQiOjQxLCJzdWIiOiJiaWRhbmcudHV5bG9hbkBnbWFpbC5jb20iLCJpYXQiOjE3MTYwNjAwNzgsImV4cCI6MTcxNjE0NjQ3OH0.14m6w2X16A8Lh_THP0coEAskf2iGZv89Bb7oTIHkWFo";

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

