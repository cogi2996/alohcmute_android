package com.example.instagramapp.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.instagramapp.Adapter.PostAdapter;
import com.example.instagramapp.LockAccountActivity;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.ModelAPI.ResponseDTO;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import com.example.instagramapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView rcPost;
    PostAdapter postAdapter;
    APIService apiService;
    List<Post> listPost;
    ResponseDTO responseDTO;
    private int pageNum = 0; // pageNum variable to track page number
    private boolean isLoading = false; // flag to prevent multiple requests
    private static final int PAGE_SIZE = 2; // number of items to load per page
    private static final String TAG = "HomeFragment";
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCreateView");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");
        Log.d(TAG, "Token: " + token);
        AnhXa(v);
        setupRecyclerView();
        setupSwipeRefreshLayout();
        loadNewPost();
        return v;
    }

    private void AnhXa(View v) {
        rcPost = v.findViewById(R.id.recyclerview_posts);
        progressBar = v.findViewById(R.id.load_progress);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout);
    }

    private void setupRecyclerView() {
        listPost = new ArrayList<>();
        postAdapter = new PostAdapter(listPost, getContext());
        rcPost.setAdapter(postAdapter);
        rcPost.setHasFixedSize(true);
        rcPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rcPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && linearLayoutManager != null) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == listPost.size() - 1) {
                        // Load more posts when scrolled to the bottom
                        loadNewPost();
                    }
                }
            }
        });
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Load posts again when pulled down
            pageNum = 0;
            listPost.clear();
            postAdapter.notifyDataSetChanged();
            loadNewPost();
        });
    }

    private void loadNewPost() {
        Log.d("KKKKKK", "scrolltop " + pageNum);
        if (pageNum == 0) {
            progressBar.setVisibility(View.VISIBLE); // Show the progress bar
        }
        isLoading = true; // Set loading flag
        apiService = RetrofitClient.getRetrofitAuth(getContext()).create(APIService.class);
        apiService.getNewPosts(pageNum, PAGE_SIZE).enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, @NonNull Response<ResponseDTO> response) {
                Log.d("CALLXXX", "" + pageNum);
                isLoading = false; // Reset loading flag
                progressBar.setVisibility(View.GONE); // Hide the progress bar
                swipeRefreshLayout.setRefreshing(false); // Hide the swipe refresh indicator
                if (response.body() == null) {
                    Log.d(TAG, "Response body is null");
                    return;
                }
                responseDTO = response.body();
                Log.d("xyxxy", responseDTO.getMessage());
                if(responseDTO.getMessage().equals("LOCK")){
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.clear();
                    myEdit.apply();
                    Intent z = new Intent(getContext(), LockAccountActivity.class);
                    startActivity(z);
                    getActivity().finish();
                }
                List<Post> newPosts = responseDTO.getListPost();
                listPost.addAll(newPosts);
                postAdapter.notifyDataSetChanged();
                pageNum++; // Increment page number for next load
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                isLoading = false; // Reset loading flag
                progressBar.setVisibility(View.GONE); // Hide the progress bar
                swipeRefreshLayout.setRefreshing(false); // Hide the swipe refresh indicator
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
