package com.example.instagramapp.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.Adapter.FollowAdapter;
import com.example.instagramapp.ModelAPI.FollowResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.R;
import com.example.instagramapp.Search.UserSearchProfileActivity;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Followings extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FollowAdapter followAdapter;
    private APIService apiService;
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followings);

        recyclerView = findViewById(R.id.Following_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService.getFollowings(15,0,20).enqueue(new Callback<FollowResponse>() {


            @Override
        public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                userList = response.body().getListFollow();
                followAdapter = new FollowAdapter(Followings.this, userList);
                recyclerView.setAdapter(followAdapter);
                if (response.isSuccessful()) {
                    Toast.makeText(Followings.this, "loading", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Followings.this, UserSearchProfileActivity.class));
                    finish();
                } else {
                    // Handle the error

                }
            } else {
                Toast.makeText(Followings.this, "Failed to load followers", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<FollowResponse> call, Throwable throwable) {
            Toast.makeText(Followings.this, "An error occurred", Toast.LENGTH_SHORT).show();

        }
    });
    }
}