package com.example.instagramapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.instagramapp.R;
import com.example.instagramapp.Stories.StoryAdapter;
//import com.example.instagramapp.Utils.HomeFragmentPostViewListAdapter;
import com.example.instagramapp.Utils.UniversalImageLoader;
import com.example.instagramapp.models.Comments;
import com.example.instagramapp.models.Photo;
import com.example.instagramapp.models.Story;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    //vars
    private ArrayList<Photo> mPhotos;
    private ArrayList<Photo> mPaginatedPhotos;
    private ArrayList<String> mFollowing;
    private ListView mListView;
//    private HomeFragmentPostViewListAdapter mAdapter;
    private int mResults;
    ImageView message;

    private RecyclerView recyclerView_story;
    private StoryAdapter storyAdapter;
    private List<Story> storyList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,null);
        mListView = v.findViewById(R.id.FragmentHome_postListView);
        mFollowing = new ArrayList<>();
        mPhotos = new ArrayList<>();
        mPaginatedPhotos = new ArrayList<>();

        message = v.findViewById(R.id.FragmentHome_msg);
        recyclerView_story = v.findViewById(R.id.FragmentHome_story_recyclerView);
        recyclerView_story.setHasFixedSize(true);
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerView_story.setLayoutManager(linearlayoutManager);
        storyList = new ArrayList<>();
        storyAdapter = new StoryAdapter(getContext(),storyList);
        recyclerView_story.setAdapter(storyAdapter);
        return v;
    }


}
