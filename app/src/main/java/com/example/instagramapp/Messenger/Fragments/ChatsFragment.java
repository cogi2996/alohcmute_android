package com.example.instagramapp.Messenger.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.Messenger.Adapter.ChatAdapter;
import com.example.instagramapp.Messenger.Model.Chat;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.R;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList;
    private List<Users> userList;
    private DatabaseReference userRef;
    private String userId, myId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.ChatsFragment_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatList = new ArrayList<>();
        userList = new ArrayList<>();

        userId = "16";
        myId = "41";

        userRef = FirebaseDatabase.getInstance().getReference("Users");

        checkAndLoadUsers();

        return view;
    }

    private void checkAndLoadUsers() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(userId) || !snapshot.hasChild(myId)) {
                    loadUserFromApi(myId);
                    loadUserFromApi(userId);
                }
                loadChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private void loadUserFromApi(String userId) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<UserResponse> call = apiService.getUser(userId);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Users user = response.body().getUser();
                    userRef.child(userId).setValue(user).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            loadChats();
                            Log.d("Message", "User" + userId + " found");
                        } else {
                            Toast.makeText(getContext(), "Failed to add user to Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load user from API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChats() {
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats").child("subChats");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (((chat.getSender().equals(myId) && chat.getReceiver().equals(userId))
                            || (chat.getSender().equals(userId) && chat.getReceiver().equals(myId))) && chat.isIsseen() == false){
                        chatList.add(chat);
                        Log.d("Message", "Chat found");
                    }
                }
                loadUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private void loadUsers() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user.getUserId().equals(userId)) {
                        userList.add(user);
                        Log.d("Message", "User found");
                    }
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private void updateUI() {
        chatAdapter = new ChatAdapter(getContext(), chatList, userList, userId, myId);
        recyclerView.setAdapter(chatAdapter);
    }
}