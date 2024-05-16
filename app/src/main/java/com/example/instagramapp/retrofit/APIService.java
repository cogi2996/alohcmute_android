package com.example.instagramapp.retrofit;

import com.example.instagramapp.models.ResponseDTO;
import com.example.instagramapp.models.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIService {
    @GET("posts")
    Call<ResponseDTO> getNewPosts(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    @PATCH(value = "api/v1/users/")
    Call<Users> getEditProfile(@Body Users user);
}
