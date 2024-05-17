package com.example.instagramapp.Like;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.instagramapp.R;
import com.example.instagramapp.Utils.LikeNotificationAdapter;
import com.example.instagramapp.models.Notification;

public class LikeFragment extends Fragment {

    private static final String TAG ="LikeFragment" ;
    private RecyclerView recyclerView;
    private LikeNotificationAdapter likeNotificationAdapter;
    private List<Notification> notificationList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_like,null);

        recyclerView = (RecyclerView)v.findViewById(R.id.FragmentLike_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList = new ArrayList<>();
        likeNotificationAdapter = new LikeNotificationAdapter(getContext(),notificationList);
        recyclerView.setAdapter(likeNotificationAdapter);
        return v;
    }






}
