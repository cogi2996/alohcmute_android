package com.example.instagramapp.Profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.R;

public class FollowersFollowing extends AppCompatActivity {

    String id,title,number;

    List<String> idList;
    RecyclerView recyclerView;
    TextView Title,Number;
    List<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_following);
        Title = (TextView) findViewById(R.id.FollowFollowing_txt);
        Number = (TextView) findViewById(R.id.FollowFollowing_number);
        recyclerView = (RecyclerView) findViewById(R.id.FollowFollowing_recyclerView);
        Title.setText(title);
        Number.setText(number);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersList = new ArrayList<>();
        idList = new ArrayList<>();
    }


}