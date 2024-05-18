package com.example.instagramapp.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.database.DatabaseReference;

import com.example.instagramapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private static final int NUM_GRID_COLUMNS = 3;
    private static final String TAG ="ProfileFragment" ;

    ImageView account_setting_menu, userImage;
    Button editProfile;
    ImageView profilePhoto;
    GridView gridView;
    TextView posts,followers,followings,name, biography, department, username, userId;
    LinearLayout follower,following;
    String noFollowers,noFollowings;
    DatabaseReference databaseReference;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,null);
        userId = (TextView) v.findViewById(R.id.userId);
        userId.setText(String.valueOf(41));
        account_setting_menu = (ImageView) v.findViewById(R.id.account_settingMenu);
        editProfile = (Button)v.findViewById(R.id.edit_profile);
        profilePhoto = (ImageView)v.findViewById(R.id.user_img);
        gridView = (GridView) v.findViewById(R.id.gridview1);
        posts = (TextView)v.findViewById(R.id.txtPosts);
        followers = (TextView)v.findViewById(R.id.txtFollowers);
        followings = (TextView)v.findViewById(R.id.txtFollowing);
        name = (TextView)v.findViewById(R.id.firstName);
        biography = (TextView)v.findViewById(R.id.bio);
        department = (TextView)v.findViewById(R.id.department);

        username = (TextView)v.findViewById(R.id.profileName);
        follower = (LinearLayout)v.findViewById(R.id.FragmentProfile_followerLinearLayout);
        following = (LinearLayout)v.findViewById(R.id.FragmentProfile_followingLinearLayout);
        mProgressBar = (ProgressBar) v.findViewById(R.id.profileProgressBar);
        getUserData(userId.getText().toString());
        return v;
    }
    private void getUserData(String userId) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<UserResponse> call = apiService.getUser(userId);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Users userResponse = response.body().getUser();
                    if (response.body().getMessage().equals("success")) {
                        String profileName = userResponse.getLastName() + userResponse.getMidName() + userResponse.getFirstName();
                        username.setText(profileName);
                        name.setText(userResponse.getFirstName());
                        biography.setText(userResponse.getBiography());
                        department.setText(userResponse.getDepartment());

                        Glide.with(ProfileFragment.this)
                                .load(userResponse.getAvatar())
                                .into(profilePhoto);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle the error
//                Log.d("test", "here3");
//                Toast.makeText(ProfileFragment.this, "Error", Toast.LENGTH_SHORT);
            }
        });

//        Log.d("test", "here4");
    }


}