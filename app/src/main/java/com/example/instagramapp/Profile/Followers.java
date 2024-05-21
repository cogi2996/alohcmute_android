package com.example.instagramapp.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class Followers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FollowAdapter followAdapter;
    private APIService apiService;
    private List<User> userList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        recyclerView = findViewById(R.id.Follow_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getRetrofitAuth(Followers.this).create(APIService.class);
        apiService.getFollowers(41).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList = response.body().getListFollow();
                    if (userList != null && userList.size() > 0) {
                        followAdapter = new FollowAdapter(Followers.this, userList);
                        recyclerView.setAdapter(followAdapter);
                    } else {
                        Toast.makeText(Followers.this, "Không có người theo dõi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Followers.this, "Không có người theo dõi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable throwable) {
                Toast.makeText(Followers.this, "Không có người theo dõi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
