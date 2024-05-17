package com.example.instagramapp.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.example.instagramapp.R;
import com.example.instagramapp.Utils.Heart;
import com.example.instagramapp.Utils.SquareImageView;
import com.example.instagramapp.Utils.UniversalImageLoader;
import com.example.instagramapp.models.Likes;
import com.example.instagramapp.models.Photo;
import com.example.instagramapp.models.Users;

public class ViewPostFragment extends Fragment {

    private static final String TAG = "ViewPostFragment";

    public ViewPostFragment(){
        super();
        setArguments(new Bundle());
    }



    //widgets
    private SquareImageView mPostImage;
    private TextView mCaption, mUsername, mTimestamp,mTags,mLikes,mtotalComments;
    private ImageView mBackArrow, mComments, mHeartRed, mHeart, mProfileImage,moption,msend;
    String lcaption,ltags,lusername;
    private ProgressBar mProgressBar;

    //vars
    Photo mPhoto;
    private Heart mheart;
    Boolean mLikedByCurrentUser;
    StringBuilder mUsers;
    Users user;
    String mLikesString = "";

    private GestureDetector mGestureDetector;

    DatabaseReference databaseReference,ref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_posts_viewer, container, false);
        mPostImage = (SquareImageView) view.findViewById(R.id.post_imagee);
        mBackArrow = (ImageView) view.findViewById(R.id.back_from_post_viewer);
        mCaption = (TextView) view.findViewById(R.id.txt_caption);
        mTags = (TextView) view.findViewById(R.id.txt_tags);
        mUsername = (TextView) view.findViewById(R.id.username);
        mTimestamp = (TextView) view.findViewById(R.id.txt_timePosted);
        mtotalComments = (TextView) view.findViewById(R.id.txt_commments);
        mLikes = (TextView) view.findViewById(R.id.txt_likes);
        mComments = (ImageView) view.findViewById(R.id.img_comments);
        mHeartRed = (ImageView) view.findViewById(R.id.img_heart_red);
        mHeart = (ImageView) view.findViewById(R.id.img_heart);
        mProfileImage = (ImageView) view.findViewById(R.id.user_img);
        moption = (ImageView) view.findViewById(R.id.option);
        msend = (ImageView) view.findViewById(R.id.img_send);
        mProgressBar = (ProgressBar) view.findViewById(R.id.viewpostProgressBar);
        mheart = new Heart(mHeart, mHeartRed);
        return view;
    }




}
