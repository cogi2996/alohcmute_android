package com.example.instagramapp.retrofit;

import com.example.instagramapp.ModelAPI.AuthenticationRequest;
import com.example.instagramapp.ModelAPI.AuthenticationResponse;
import com.example.instagramapp.ModelAPI.LikePostResponse;
import com.example.instagramapp.ModelAPI.ResponseDTO;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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




    //tin
    @GET("users/search")
    Call<UserResponse> searchUserByName(
            @Query("name") String name,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    @GET("users")
    Call<List<User>> getAllUsers( @Query("pageNum") int pageNum,
                                  @Query("pageSize") int pageSize);





    // like
    @POST("users/{userId}/likeList/posts/{postId}")
    Call<LikePostResponse> likePost(@Path("userId") int userId, @Path("postId") int postId);
    // unlike post
    @DELETE("users/{userId}/likeList/posts/{postId}")
    Call<LikePostResponse> unlikePost(@Path("userId") int userId, @Path("postId") int postId);

    @POST("users/current")
    Call<UserDTO> getCurrentUser();

}
