package com.example.instagramapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.instagramapp.Like.LikeFragment;
import com.example.instagramapp.Post.PostActivity;
import com.example.instagramapp.Profile.ProfileFragment;
import com.example.instagramapp.Search.SearchFragment;
import com.example.instagramapp.home.HomeFragment;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigationView = findViewById(R.id.insta_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            loadfragment(new HomeFragment());

        } else {
            loadfragment(new HomeFragment());

        }

    }

    private boolean loadfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.Home) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.search) {
            fragment = new SearchFragment();
        } else if (itemId == R.id.post) {
            fragment = null;
            startActivity(new Intent(Home.this, PostActivity.class));
        } else if (itemId == R.id.likes) {
            fragment = new LikeFragment();
        } else if (itemId == R.id.profile) {
            fragment = new ProfileFragment();
        }

        return loadfragment(fragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}