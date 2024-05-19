package com.example.instagramapp.retrofit;

import com.example.instagramapp.ModelAPI.Account;
import com.example.instagramapp.ModelAPI.AuthenticationRequest;
import com.example.instagramapp.ModelAPI.AuthenticationResponse;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.models.ResponseDTO;
import com.example.instagramapp.ModelAPI.ChangePasswordRespone;
import com.example.instagramapp.ModelAPI.ResetPassword;
import com.example.instagramapp.ModelAPI.SingleUserResponse;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.ModelAPI.LikePostResponse;
import com.example.instagramapp.ModelAPI.ResponseDTO;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;

import java.util.List;
import com.example.instagramapp.ModelAPI.CheckedLikeResponse;
import com.example.instagramapp.ModelAPI.CurrentUserResponse;
import com.example.instagramapp.ModelAPI.Department;
import com.example.instagramapp.ModelAPI.DepartmentResponse;
import com.example.instagramapp.ModelAPI.LikePostResponse;
import com.example.instagramapp.ModelAPI.MajorResponse;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.ModelAPI.ResponseDTO;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;

import java.util.List;

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

    @GET("users/{id}")
    Call<SingleUserResponse> getUser(@Path("id") String id);

    @PATCH(value = "users")
    Call<SingleUserResponse> getEditProfile(@Body Users user);
    // Đăng nhập
    @POST("auth/login")
    Call<AuthenticationResponse> login(@Body AuthenticationRequest request);

    @POST("auth/change-password")
    Call<ChangePasswordRespone> changePassword(@Body ResetPassword resetPassword);

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

    @GET("users/{id}/followers")
    Call<FollowResponse> getFollowers(@Path("id") int id);

    // like
    @POST("users/{userId}/likeList/posts/{postId}")
    Call<LikePostResponse> likePost(@Path("userId") int userId, @Path("postId") int postId);

    // unlike post
    @DELETE("users/{userId}/likeList/posts/{postId}")
    Call<LikePostResponse> unlikePost(@Path("userId") int userId, @Path("postId") int postId);

    @POST("users/current")
    Call<CurrentUserResponse> getCurrentUser();

    @GET("users")
    Call<List<User>> getAllUsers( @Query("pageNum") int pageNum,
                                  @Query("pageSize") int pageSize);
    //find user by id
    @GET("users/{userId}")
    Call<CurrentUserResponse> getUserById(@Path("userId") int userId);


    // find all department
    @GET("department")
    Call<DepartmentResponse> getAllDepartment();

    // find department by id
    @GET("department/{id}")
    Call<Department> getDepartmentById(@Path("id") int id);

    @GET("department/{id}/major")
    Call<MajorResponse> getMajorByDepartmentId(@Path("id") int id);

    @POST("auth/register")
    Call<AuthenticationResponse> register(@Body Account accountDTO);

    // find one post
    @GET("posts/{postId}")
    Call<Post> getPostById(@Path("postId") int postId);

    @GET("posts/{postId}/like/check")
    Call<CheckedLikeResponse> checkUserLikePost(@Path("postId") int postId);



}
