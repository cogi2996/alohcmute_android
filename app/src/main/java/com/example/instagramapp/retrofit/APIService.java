package com.example.instagramapp.retrofit;

import com.example.instagramapp.modelFirebase.AuthenticationRequest;
import com.example.instagramapp.modelFirebase.AuthenticationResponse;
import com.example.instagramapp.modelFirebase.ResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    @GET("posts")
    Call<ResponseDTO> getNewPosts(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    // Đăng nhập
    @POST("auth/login")
    Call<AuthenticationResponse> login(@Body AuthenticationRequest request);










}
