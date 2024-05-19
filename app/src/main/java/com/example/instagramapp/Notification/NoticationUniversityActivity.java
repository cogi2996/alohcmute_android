package com.example.instagramapp.Notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.instagramapp.ModelAPI.NotificationUniversity;
import com.example.instagramapp.ModelAPI.NotificationUniversityResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.R;
import com.example.instagramapp.Search.SearchUserAdapter;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticationUniversityActivity extends Fragment {
    private static final String TAG ="NoticationUniversity" ;
    private RecyclerView recyclerView;
    private NotificationUniversityAdapter notificationUniversityAdapter;
    private List<NotificationUniversity> mNotificationUniversity;

    APIService apiService;
    private NotificationUniversityResponse notificationUniversityResponse;
    TextView tittle,discription;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_notication_university,null);
        //AnhXa();
        recyclerView = (RecyclerView)v.findViewById(R.id.FragmentUniversity_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadNotiUniversity(0,20);
        return v;
    }

    private void loadNotiUniversity(int pageNum, int pageSize) {
        mNotificationUniversity = new ArrayList<>();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        apiService.getAllNotiUniversity(pageNum,pageSize).enqueue(new Callback<NotificationUniversityResponse>() {
            @Override
            public void onResponse(Call<NotificationUniversityResponse> call, Response<NotificationUniversityResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Loi o day13");

                    notificationUniversityResponse = response.body();
                    mNotificationUniversity = notificationUniversityResponse.getListNotificationUniversity();
                    notificationUniversityAdapter = new NotificationUniversityAdapter(mNotificationUniversity, getContext());
                    recyclerView.setAdapter(notificationUniversityAdapter);
                    Log.d(TAG, "Loi o day14");

                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<NotificationUniversityResponse> call, Throwable throwable) {
                Log.d("LogFail", throwable.getMessage());
            }
        });
    }
    /*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notication_university);
        tittle = (TextView)findViewById(R.id.tvTitle);

        recyclerView = (RecyclerView).findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search = (EditText)v.findViewById(R.id.search_user);
        backFromPost = (ImageView)findViewById(R.id.back_from_post);
    }*/
}