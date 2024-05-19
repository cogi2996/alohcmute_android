package com.example.instagramapp.Profile;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.instagramapp.ModelAPI.FollowResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.R;
import com.example.instagramapp.Utils.SearchUsersAdapter;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFollowing extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchUsersAdapter followAdapter;
    private APIService apiService;
    private List<User> followList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_following);

        recyclerView = findViewById(R.id.FollowFollowing_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        apiService = RetrofitClient.getRetrofitAuth().create(APIService.class);
        apiService.getFollowers(15).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    followList = response.body().getListFollow();
//                    followAdapter = new FollowAdapter(FollowersFollowing.this, followList);
                    recyclerView.setAdapter(followAdapter);
                } else {
                    Toast.makeText(FollowersFollowing.this, "Failed to load followers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable throwable) {
                Toast.makeText(FollowersFollowing.this, "An error occurred", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
