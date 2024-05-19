package com.example.instagramapp.retrofit;

import com.example.instagramapp.ModelAPI.AuthenticationRequest;
import com.example.instagramapp.ModelAPI.AuthenticationResponse;
import com.example.instagramapp.ModelAPI.LikePostResponse;
import com.example.instagramapp.ModelAPI.PostByIdResponse;
import com.example.instagramapp.ModelAPI.ResponseDTO;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;

import java.util.List;
import com.example.instagramapp.ModelAPI.FollowResponse;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.ModelAPI.UserResponse_findOne;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
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
    @GET("users/{id}")
    Call<UserResponse_findOne> getUser(@Path("id") String id);

    @PATCH(value = "users")
    Call<UserResponse> getEditProfile(@Body User user);



    // like
    @POST("users/{userId}/likeList/posts/{postId}")
    Call<LikePostResponse> likePost(@Path("userId") int userId, @Path("postId") int postId);
    // unlike post
    @DELETE("users/{userId}/likeList/posts/{postId}")
    Call<LikePostResponse> unlikePost(@Path("userId") int userId, @Path("postId") int postId);


    @GET("users/{id}/followers")
    Call<FollowResponse> getFollowers(@Path("id") int id);
    @GET("users/{id}/followings")
    Call<FollowResponse> getFollowings(@Path("id") int id,
                                       @Query("pageNum") int pageNum,
                                       @Query("pageSize") int pageSize);

    @GET("users/{userId}/posts")
    Call<PostByIdResponse> getUserPosts(
            @Path("userId") int userId,
            @Query("pageNum") int pageNum);
}
