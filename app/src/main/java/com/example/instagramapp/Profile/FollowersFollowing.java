package com.example.instagramapp.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.instagramapp.R;
import com.example.instagramapp.Utils.SearchUsersAdapter;
import com.example.instagramapp.models.Users;

public class FollowersFollowing extends AppCompatActivity {

    String id,title,number;

    List<String> idList;
    RecyclerView recyclerView;
    TextView Title,Number;
    SearchUsersAdapter usersAdapter;
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
        usersAdapter = new SearchUsersAdapter(this,usersList);
        recyclerView.setAdapter(usersAdapter);
        idList = new ArrayList<>();
    }


}