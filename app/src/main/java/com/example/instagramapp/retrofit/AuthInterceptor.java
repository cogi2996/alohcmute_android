package com.example.instagramapp.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token =
            "eyJhbGciOiJIUzI1NiJ9.eyJmdWxsTmFtZSI6IlBoYW4gTmfhu41jIFRo4buDIiwidXNlcklkIjo0MSwic3ViIjoiYmlkYW5nLnR1eWxvYW5AZ21haWwuY29tIiwiaWF0IjoxNzE2MDkyNTg3LCJleHAiOjE3MTYxNzg5ODd9.RUW0lNtInaYgElOOtenwdxJIFPp0ugsZPMg3p8bENkE";

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

