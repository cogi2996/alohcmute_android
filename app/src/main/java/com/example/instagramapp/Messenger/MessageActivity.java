package com.example.instagramapp.Messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.SingleUserResponse;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.example.instagramapp.Messenger.Adapter.MessengerAdapter;
import com.example.instagramapp.Messenger.Model.Chat;
import com.example.instagramapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username,fullname;

    FirebaseUser fuser;
    DatabaseReference reference;

    private Toolbar toolbar;
    Intent intent;
    boolean notify = false;

    TextView btn_send;
    ImageView back_btn;
    EditText text_send;

    MessengerAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;
    String userid, myId;

    String msg = "";

    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.MessageActivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = (TextView)findViewById(R.id.MessageActivity_firstName);
        fullname = (TextView)findViewById(R.id.MessageActivity_fullname);
        profile_image = (CircleImageView)findViewById(R.id.MessageActivity_user_img);
        btn_send =findViewById(R.id.MessageActivity_btn_send);
        text_send = findViewById(R.id.MessageActivity_text_send);

//        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        recyclerView = findViewById(R.id.MessageActivity_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        myId = intent.getStringExtra("myId");
        userid = intent.getStringExtra("userId");

        reference = FirebaseDatabase.getInstance().getReference("Users").child(myId);

        loadUserFromApi(myId, userid);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(myId, userid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
                closeKeyboard();
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, ChatActivity.class);
                // Truyền thông tin cần thiết tới ChatActivity nếu cần
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats").child("subChats");
                HashMap<String, Object> hashMap = new HashMap<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            if (((chat.getSender().equals(myId) && chat.getReceiver().equals(userid))
                                    || (chat.getSender().equals(userid) && chat.getReceiver().equals(myId))) && chat.isIsseen() == false){
                                dataSnapshot.getRef().child("isseen").setValue(true);
                                Log.d("Message", "Chat found");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle possible errors.
                    }
                });
                startActivity(intent);
            }
        });

    }

    private void loadUserFromApi(String myId, String userId) {

        APIService apiService = RetrofitClient.getRetrofitAuth(MessageActivity.this).create(APIService.class);
        Call<SingleUserResponse> call = apiService.getUser(userId);
        call.enqueue(new Callback<SingleUserResponse>() {
            @Override
            public void onResponse(Call<SingleUserResponse> call, Response<SingleUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Users user = response.body().getUser();
                    userid = user.getUserId();

                    // Hiển thị thông tin người dùng
                    String getFullName = user.getLastName() + " " + user.getMidName() + " " + user.getFirstName();
                    username.setText(user.getFirstName());
                    fullname.setText(getFullName);
                    Glide.with(MessageActivity.this)
                            .load(user.getAvatar())
                            .into(profile_image);
                    readMesagges(myId, userId, user.getAvatar());
                } else {
                    // Xử lý lỗi nếu có
                }
            }

            @Override
            public void onFailure(Call<SingleUserResponse> call, Throwable t) {
                // Xử lý lỗi nếu có
            }
        });
    }
    private void sendMessage(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        reference.child("subChats").push().setValue(hashMap);

    }

    private void readMesagges(final String myid, final String userid, final String imageurl){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats").child("subChats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getSender().equals(myid) && chat.getReceiver().equals(userid) ||
                            chat.getSender().equals(userid) && chat.getReceiver().equals(myid)) {
                        mchat.add(chat);
                    }
                    messageAdapter = new MessengerAdapter(MessageActivity.this, mchat, imageurl, myid, userid);
                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}