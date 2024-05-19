package com.example.instagramapp.Search;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse_findOne;
import com.example.instagramapp.Profile.ProfileFragment;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.database.DatabaseReference;

import com.example.instagramapp.R;

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
    private void getUserData(String userId) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getUser(String.valueOf(41)).enqueue(new Callback<UserResponse_findOne>() {
            @Override
            public void onResponse(Call<UserResponse_findOne> call, Response<UserResponse_findOne> response) {
                if (response.isSuccessful()) {
                    UserResponse_findOne userResponse = response.body();
                    if (userResponse != null && userResponse.getMessage().equals("success")) {
                        User user = userResponse.getUser();
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
            public void onFailure(Call<UserResponse_findOne> call, Throwable throwable) {
            }
        });
    }
}