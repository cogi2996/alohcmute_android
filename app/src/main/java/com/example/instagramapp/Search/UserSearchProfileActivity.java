package com.example.instagramapp.Search;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import com.example.instagramapp.R;

public class UserSearchProfileActivity extends AppCompatActivity {

    private static final String TAG ="UserSearchActivity" ;
    private static final int NUM_GRID_COLUMNS = 3;

    String searchedUserId;
    Button Follow,Following,Message;
    ImageView profilePhoto;
    GridView gridView;
    TextView posts,followers,followings,name, description,website,username;
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
        description = (TextView)findViewById(R.id.UserSearchProfile_description);
        website = (TextView)findViewById(R.id.UserSearchProfile_website);
        username = (TextView)findViewById(R.id.UserSearchProfile_profileName);
        follower = (LinearLayout)findViewById(R.id.UserSearchProfile_noFollowers);
        following = (LinearLayout)findViewById(R.id.UserSearchProfile_noFollowing);
        mProgressBar = (ProgressBar)findViewById(R.id.UserSearchProfile_ProgressBar);
    }

}