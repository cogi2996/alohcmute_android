package com.example.instagramapp.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJmdWxsTmFtZSI6IsSQ4bq3bmcgQ8O0bmcgVHXhuqVuIiwidXNlcklkIjoxNSwic3ViIjoidGVzdFVzZXIxMjNAZ21haWwuY29tIiwiaWF0IjoxNzE2MDM4NzE4LCJleHAiOjE3MTYxMjUxMTh9.blBpMOvQG85uW3CC4SDjmEvuYJHmA-QlOqOz7uMUc-w";

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

