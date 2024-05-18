package com.example.instagramapp.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.Adapter.PostAdapter;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.ModelAPI.ResponseDTO;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import java.util.List;

import com.example.instagramapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    //    tuan-add
    RecyclerView rcPost;
    PostAdapter postAdapter;
    APIService apiService;
    List<Post> listPost;
    ResponseDTO responseDTO;


    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,null);
        Log.d("tokenInHomeFrageq","here");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");
        Log.d("tokenInHomeFrage", token);
        AnhXa(v);
        loadNewPost();
        return v;
    }
    private void AnhXa(View v) {
        rcPost = v.findViewById(R.id.recyclerview_posts);
    }


    private void loadNewPost() {
        apiService = RetrofitClient.getRetrofitAuth(getContext()).create(APIService.class);
        apiService.getNewPosts(0, 7).enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, @NonNull Response<ResponseDTO> response) {
                String message = response.body().getMessage();
                Log.d("HHHHHHUUU", message);
                // check response not null
                if (response.body() == null) {
                    Log.d("XSS", "2");
                    return;
                }
                Log.d("SUCXX", "3");
                responseDTO = response.body();
                listPost = responseDTO.getListPost();
                Log.d("PIMG", listPost.get(0).getPostText());
                postAdapter = new PostAdapter(listPost, getContext());
                rcPost.setAdapter(postAdapter);
                rcPost.setHasFixedSize(true);
                rcPost.setLayoutManager(new LinearLayoutManager(getContext()));
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Log.d("LogFail", t.getMessage());
            }
        });
    }




}
