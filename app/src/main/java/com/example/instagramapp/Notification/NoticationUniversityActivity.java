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

    public class NoticationUniversityActivity extends AppCompatActivity {
        private static final String TAG ="NoticationUniversity" ;
        private RecyclerView recyclerView;
        private NotificationUniversityAdapter notificationUniversityAdapter;
        private List<NotificationUniversity> mNotificationUniversity;

        APIService apiService;
        private NotificationUniversityResponse notificationUniversityResponse;
        TextView tittle,discription;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notication_university);
            recyclerView = (RecyclerView)findViewById(R.id.FragmentUniversity_recyclerView);

            if (recyclerView != null) {
                loadNotiUniversity(0, 20);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this, LinearLayoutManager. VERTICAL, false);
                recyclerView.setLayoutManager (linearLayoutManager);
            } else {
                // Handle the case where recyclerView is null
                Log.e(TAG, "recyclerView is null");
            }
        }

        private void loadNotiUniversity(int pageNum, int pageSize) {
            Log.e(TAG, "recyclerView is null 2");
            mNotificationUniversity = new ArrayList<>();
            apiService = RetrofitClient.getRetrofitAuth(NoticationUniversityActivity.this).create(APIService.class);
            apiService.getAllNotiUniversity(pageNum, pageSize).enqueue(new Callback<List<NotificationUniversity>>() {
                @Override
                public void onResponse(Call<List<NotificationUniversity>> call, Response<List<NotificationUniversity>> response) {

                    Log.d("Notice", String.valueOf(response.code()));
    //                Log.d("Notice", String.valueOf(response.errorBody()));
                    Log.d("url", String.valueOf(response.raw()));
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Loi o day13");

                        mNotificationUniversity = response.body();
                        //mNotificationUniversity = notificationUniversityResponse.getListNotificationUniversity();
                        notificationUniversityAdapter = new NotificationUniversityAdapter(mNotificationUniversity, NoticationUniversityActivity.this);
                        recyclerView.setAdapter(notificationUniversityAdapter);
                        Log.d(TAG, "Loi o day14");
                    }
                }

                @Override
                public void onFailure(Call<List<NotificationUniversity>> call, Throwable throwable) {

                }
            });

        }

    }