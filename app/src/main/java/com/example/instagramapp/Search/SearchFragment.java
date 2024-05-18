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
    private SearchUserAdapter searchUserAdapter;
    private List<User> mUser;
    EditText search;

    APIService apiService;
    private UserResponse userResponse;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search,null);
        //AnhXa();
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search = (EditText)v.findViewById(R.id.search_user);
        loadUser(0,20);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchUsersByName(s.toString(), 0, 20);
                    Log.d(TAG, s.toString());
                } else {
                    loadUser(0,20);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return v;
    }


    private void AnhXa() {
    }

    private void loadUser(int pageNum, int pageSize) {
        mUser = new ArrayList<>();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        apiService.getAllUsers(pageNum,pageSize).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                Log.d("Notice", String.valueOf(response.code()));
                Log.d("Notice", String.valueOf(response.errorBody()));

                if (response.isSuccessful()) {
                    List<User> tmp = response.body();
                    if (tmp != null) {
                        mUser.addAll(tmp);
                        if (searchUserAdapter == null) {
                            searchUserAdapter = new SearchUserAdapter(mUser,getContext());
                            recyclerView.setAdapter(searchUserAdapter);
                        } else {
                            searchUserAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Handle the case when response.body() is null
                        Log.d(TAG, "Response body is null");
                        // You can show an error message or do something else
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Log.d(TAG, "Loi o day5");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                Log.d("LogFail", throwable.getMessage());

            }
        });


    }

    private void searchUsersByName(String name, int pageNum, int pageSize) {
        Log.d(TAG, "Loi o day12");
        mUser = new ArrayList<>();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.searchUserByName(name, pageNum, pageSize).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Loi o day13");
                    mUser.clear();
                    userResponse = response.body();
                    mUser = userResponse.getListUser();
                    searchUserAdapter = new SearchUserAdapter(mUser, getContext());
                    recyclerView.setAdapter(searchUserAdapter);
                    Log.d(TAG, "Loi o day14");

                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("LogFail", t.getMessage());
            }
        });
    }
}
