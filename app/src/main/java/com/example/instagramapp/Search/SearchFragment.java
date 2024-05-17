package com.example.instagramapp.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.instagramapp.R;
import com.example.instagramapp.Utils.SearchUsersAdapter;
import com.example.instagramapp.models.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final String TAG ="SearchFragment" ;
    private RecyclerView recyclerView;
    private SearchUsersAdapter searchUsersAdapter;
    private List<User> mUser;
    EditText search;

    APIService apiService;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search,null);
        //AnhXa();
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search = (EditText)v.findViewById(R.id.search_user);
        mUser = new ArrayList<>();
        loadUser();
        return v;
    }

    private void AnhXa() {
    }

    private void loadUser() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mUser = response.body();
                //SearchUsersAdapter searchUsersAdapter = new SearchUsersAdapter(mUser);
                //recyclerView.setAdapter(searchUsersAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                Log.d("LogFail", throwable.getMessage());
            }
        });


    }


}
