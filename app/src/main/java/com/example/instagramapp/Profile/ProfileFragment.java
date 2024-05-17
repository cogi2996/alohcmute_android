package com.example.instagramapp.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.instagramapp.R;
import com.example.instagramapp.Utils.GridImageAdapter;
import com.example.instagramapp.models.Comments;
import com.example.instagramapp.models.Likes;
import com.example.instagramapp.models.Photo;
import com.example.instagramapp.models.Users;

public class ProfileFragment extends Fragment {

    private static final int NUM_GRID_COLUMNS = 3;
    private static final String TAG ="ProfileFragment" ;

    ImageView account_setting_menu;
    Button editProfile;
    ImageView profilePhoto;
    GridView gridView;
    TextView posts,followers,followings,name, description,website,username;
    LinearLayout follower,following;
    String noFollowers,noFollowings;
    DatabaseReference databaseReference;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,null);
        account_setting_menu = (ImageView) v.findViewById(R.id.account_settingMenu);
        editProfile = (Button)v.findViewById(R.id.edit_profile);
        profilePhoto = (ImageView)v.findViewById(R.id.user_img);
        gridView = (GridView) v.findViewById(R.id.gridview1);
        posts = (TextView)v.findViewById(R.id.txtPosts);
        followers = (TextView)v.findViewById(R.id.txtFollowers);
        followings = (TextView)v.findViewById(R.id.txtFollowing);
        name = (TextView)v.findViewById(R.id.display_name);
        description = (TextView)v.findViewById(R.id.description);
        website = (TextView)v.findViewById(R.id.website);
        username = (TextView)v.findViewById(R.id.profileName);
        follower = (LinearLayout)v.findViewById(R.id.FragmentProfile_followerLinearLayout);
        following = (LinearLayout)v.findViewById(R.id.FragmentProfile_followingLinearLayout);
        mProgressBar = (ProgressBar) v.findViewById(R.id.profileProgressBar);
        return v;
    }



}
