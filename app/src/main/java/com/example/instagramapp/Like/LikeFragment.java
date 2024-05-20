package com.example.instagramapp.Like;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.ModelAPI.NotificationUniversity;
import com.example.instagramapp.ModelAPI.NotificationUniversityResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.Notification.NotificationUniversityAdapter;
import com.example.instagramapp.R;
import com.example.instagramapp.Search.SearchUserAdapter;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeFragment extends Fragment {

    private static final String TAG ="LikeFragment" ;
    private RecyclerView recyclerView;
    private NotificationUniversityAdapter notificationUniversityAdapter;
    private List<NotificationUniversity> mNotificationUniversity;

    APIService apiService;
    private NotificationUniversityResponse notificationUniversityResponse;
    TextView tittle,discription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_like,null);

        recyclerView = (RecyclerView)v.findViewById(R.id.FragmentLike_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mNotificationUniversity = new ArrayList<>();
        apiService = RetrofitClient.getRetrofitAuth(getContext()).create(APIService.class);
        apiService.getAllNotiUniversity(0, 20).enqueue(new Callback<List<NotificationUniversity>>() {
            @Override
            public void onResponse(Call<List<NotificationUniversity>> call, Response<List<NotificationUniversity>> response) {
                if (response.isSuccessful()) {
                    mNotificationUniversity = response.body();
                    notificationUniversityAdapter = new NotificationUniversityAdapter(mNotificationUniversity, getContext());
                    recyclerView.setAdapter(notificationUniversityAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<NotificationUniversity>> call, Throwable throwable) {
                // Handle failure case
            }
        });

        return v;
    }
}