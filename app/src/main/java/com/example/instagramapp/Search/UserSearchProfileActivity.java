package com.example.instagramapp.Search;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.ImagePostDTO;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.ModelAPI.PostByIdResponse;
import com.example.instagramapp.ModelAPI.SingleUserResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse_findOne;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.Profile.ImageAdapter;
import com.example.instagramapp.Profile.ProfileFragment;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.database.DatabaseReference;

import com.example.instagramapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchProfileActivity extends AppCompatActivity {

    private static final String TAG ="UserSearchActivity" ;
    private static final int NUM_GRID_COLUMNS = 3;

    String searchedUserId;
    Button Follow,Following,Message;
    ImageView profilePhoto;
    GridView gridView;
    TextView posts,followers,followings,name, bio,department,username;
    LinearLayout follower,following;
    String noFollowers,noFollowings;
    DatabaseReference databaseReference;
    private ProgressBar mProgressBar;
    private List<Post> mPost;
    private PostByIdResponse postByIdResponse;
    ImageAdapter imageAdapter;
    private List<ImagePostDTO> imagePostDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_profile);
        searchedUserId = getIntent().getStringExtra("SearchedUserid");
        Follow = (Button)findViewById(R.id.UserSearchProfile_Followbtn);
        Following = (Button)findViewById(R.id.UserSearchProfile_Followingbtn);
        Message = (Button)findViewById(R.id.UserSearchProfile_messages);
        profilePhoto = (ImageView)findViewById(R.id.UserSearchProfile_user_img);
        gridView = (GridView)findViewById(R.id.UserSearchProfile_gridview1);
        posts = (TextView)findViewById(R.id.UserSearchProfile_txtPosts);
        followers = (TextView)findViewById(R.id.UserSearchProfile_txtFollowers);
        followings = (TextView)findViewById(R.id.UserSearchProfile_txtFollowing);
        name = (TextView)findViewById(R.id.UserSearchProfile_display_name);
        bio = (TextView)findViewById(R.id.UserSearchProfile_bio);
        department = (TextView)findViewById(R.id.UserSearchProfile_department);
        username = (TextView)findViewById(R.id.UserSearchProfile_profileName);
        follower = (LinearLayout)findViewById(R.id.UserSearchProfile_noFollowers);
        following = (LinearLayout)findViewById(R.id.UserSearchProfile_noFollowing);
        mProgressBar = (ProgressBar)findViewById(R.id.UserSearchProfile_ProgressBar);
    }
    private void loadImagePost(int userId, int pageNum ) {
        mProgressBar.setVisibility(View.VISIBLE);
        mPost = new ArrayList<>();
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getUserPosts(userId, pageNum).enqueue(new Callback<PostByIdResponse>() {
            @Override
            public void onResponse(Call<PostByIdResponse> call, Response<PostByIdResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    postByIdResponse = response.body();
                    imagePostDTO = postByIdResponse.getListPost();

                    // Tạo Adapter
                    imageAdapter = new ImageAdapter(UserSearchProfileActivity.this,
                            R.layout.layout_grid_imageview,
                            imagePostDTO
                    );

                    gridView.setAdapter(imageAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }
                else {
                    // Nếu không tải được hình ảnh từ API, set hình ảnh cứng
//                    imageAdapter = new ImageAdapter(requireContext(),
//                            R.layout.layout_grid_imageview,
//                            getDefaultImagePostDTO()
//                    );
//                    gridView.setAdapter(imageAdapter);
//                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PostByIdResponse> call, Throwable throwable) {
                Log.d("LogFail", throwable.getMessage());

                // Nếu không tải được hình ảnh từ API, set hình ảnh cứng
//                imageAdapter = new ImageAdapter(requireContext(),
//                        R.layout.layout_grid_imageview,
//                        getDefaultImagePostDTO()
//                );
//                gridView.setAdapter(imageAdapter);
//                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
    private void getUserData(String userId) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getUser(String.valueOf(41)).enqueue(new Callback<SingleUserResponse>() {
            @Override
            public void onResponse(Call<SingleUserResponse> call, Response<SingleUserResponse> response) {
                if (response.isSuccessful()) {
                    SingleUserResponse userResponse = response.body();
                    if (userResponse != null && userResponse.getMessage().equals("success")) {
                        Users user = userResponse.getUser();
                        String profileName = user.getLastName() + user.getMidName() + user.getFirstName();
                        username.setText(profileName);
                        name.setText(user.getFirstName());
                        bio.setText(user.getBiography());
                        department.setText(user.getDepartment());

                        Glide.with(UserSearchProfileActivity.this)
                                .load(user.getAvatar())
                                .into(profilePhoto);
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleUserResponse> call, Throwable throwable) {
            }
        });
    }
}