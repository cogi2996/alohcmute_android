package com.example.instagramapp.Messenger.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.Messenger.ChatActivity;
import com.example.instagramapp.Messenger.MessageActivity;
import com.example.instagramapp.Messenger.Model.Chat;
import com.example.instagramapp.ModelAPI.Users;
import com.example.instagramapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<Chat> mChat;
    private List<Users> mUsers;
    private String userId, myId;


    public ChatAdapter(Context mContext, List<Chat> mChat, List<Users> mUsers, String userId, String myId) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.mUsers = mUsers;
        this.userId = userId;
        this.myId = myId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mChat.get(position);

        holder.message.setText(chat.getMessage());

        // Find the sender's full name using the userId
        for (Users user : mUsers) {
            if (user.getUserId().equals(userId)) {
                String fullName = user.getFirstName() + " " + user.getMidName() + " " + user.getLastName();
                holder.sender.setText(fullName);
                break;
            }
        }

        holder.seenStatus.setText(chat.isIsseen() ? "Seen" : "Delivered");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện click vào item chat
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("myId", myId);
                intent.putExtra("userId", userId);
                // Truyền thông tin cần thiết tới ChatActivity nếu cần
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats").child("subChats");
                HashMap<String, Object> hashMap = new HashMap<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            if (((chat.getSender().equals(myId) && chat.getReceiver().equals(userId))
                                    || (chat.getSender().equals(userId) && chat.getReceiver().equals(myId))) && chat.getMessage().equals(holder.message.getText()) && chat.isIsseen() == false){
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
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sender;
        public TextView message;
        public TextView seenStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.profile_name);
            message = itemView.findViewById(R.id.profile_messages);
            seenStatus = itemView.findViewById(R.id.profile_seen);
        }
    }
}