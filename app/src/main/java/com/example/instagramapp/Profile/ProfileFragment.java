package com.example.instagramapp.Profile;

import android.content.Intent;
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
import com.example.instagramapp.ModelAPI.ImagePostDTO;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.ModelAPI.PostByIdResponse;
import com.example.instagramapp.ModelAPI.SingleUserResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.Search.SearchUserAdapter;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.database.DatabaseReference;

import com.example.instagramapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private static final int NUM_GRID_COLUMNS = 3;
    private static final String TAG = "ProfileFragment";

    ImageView account_setting_menu, userImage;
    Button editProfile;
    ImageView profilePhoto;
    GridView gridView;
    TextView posts, followers, followings, name, biography, department, username, userId;
    LinearLayout follower, following;
    String noFollowers, noFollowings;
    DatabaseReference databaseReference;
    private ProgressBar mProgressBar;
    private List<User> mUser;
    private List<Post> mPost;
    private PostByIdResponse postByIdResponse;
    ImageAdapter imageAdapter;
    private List<ImagePostDTO> imagePostDTO;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);
        userId = (TextView) v.findViewById(R.id.userId);
        account_setting_menu = (ImageView) v.findViewById(R.id.account_settingMenu);
        editProfile = (Button) v.findViewById(R.id.edit_profile);
        profilePhoto = (ImageView) v.findViewById(R.id.user_img);
        gridView = (GridView) v.findViewById(R.id.gridview1);
        posts = (TextView) v.findViewById(R.id.txtPosts);
        followers = (TextView) v.findViewById(R.id.txtFollowers);
        followings = (TextView) v.findViewById(R.id.txtFollowing);
        name = (TextView) v.findViewById(R.id.firstName);
        biography = (TextView) v.findViewById(R.id.bio);
        department = (TextView) v.findViewById(R.id.department);

        username = (TextView) v.findViewById(R.id.profileName);
        follower = (LinearLayout) v.findViewById(R.id.FragmentProfile_followerLinearLayout);
        following = (LinearLayout) v.findViewById(R.id.FragmentProfile_followingLinearLayout);
        mProgressBar = (ProgressBar) v.findViewById(R.id.profileProgressBar);
        getUserData(userId.getText().toString());
        loadImagePost(41, 0);

        account_setting_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),Account_Settings.class);
                startActivity(intent);
            }
        });
//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),EditProfile.class);
//                startActivity(intent);
//            }
//        });

        follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),Followers.class);
                startActivity(intent);

            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Followings.class);
                startActivity(intent);

            }
        });
        return v;
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
                    imageAdapter = new ImageAdapter(requireContext(),
                            R.layout.layout_grid_imageview,
                            imagePostDTO
                    );

                    gridView.setAdapter(imageAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PostByIdResponse> call, Throwable throwable) {
                Log.d("LogFail", throwable.getMessage());
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
                        biography.setText(user.getBiography());
                        department.setText(user.getDepartment());
                        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                            Glide.with(ProfileFragment.this)
                                    .load(user.getAvatar())
                                    .into(profilePhoto);
                        } else {
                            // Use a default avatar image if the user doesn't have one
                            profilePhoto.setImageResource(R.drawable.user);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<SingleUserResponse> call, Throwable throwable) {
            }
        });
    }
}