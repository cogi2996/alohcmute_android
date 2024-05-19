package com.example.instagramapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.CommentsActivity;
import com.example.instagramapp.ModelAPI.CurrentUserResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.R;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context mContext;
    List<Comment> mComments;
    APIService apiService;

    public CommentAdapter(Context mContext, List<Comment> mComments) {
        this.mContext = mContext;
        this.mComments = mComments;
        apiService = RetrofitClient.getRetrofitAuth(mContext).create(APIService.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        DocumentReference publisherReference = FirebaseFirestore.getInstance().collection("Users").document(comment.getPublisherId());
        publisherReference.get().addOnSuccessListener(snapshot -> {
//            User user = snapshot.toObject(User.class);
//            Glide.with(mContext).load(user.getProfileImageUrl()).into(holder.profileImage);
//            holder.username.setText(user.getUsername());
            Call<CurrentUserResponse> call = apiService.getUserById(Integer.parseInt(comment.getPublisherId()));
            call.enqueue(new Callback<CurrentUserResponse>() {
                @Override
                public void onResponse(Call<CurrentUserResponse> call, Response<CurrentUserResponse> response) {
                    if (response.isSuccessful()) {
                        User user = response.body().getUser();
                        holder.username.setText(user.getFullName());
                        Glide.with(mContext)
                                .load(user.getAvatar()) // data picture
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(holder.profileImage);
                    }
                }

                @Override
                public void onFailure(Call<CurrentUserResponse> call, Throwable t) {
                    // Handle error
                }
            });
//            holder.time.setText(DateTimeFormatter.getTimeDifference(comment.getCommentId(), true));
            holder.comment.setText(comment.getText());

//            holder.container.setOnClickListener(view -> {
//                Intent intent = new Intent(mContext, OtherProfileActivity.class);
//                intent.putExtra("userid", user.getId());
//                mContext.startActivity(intent);
//            });
        });
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView username, comment, time;
        View container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            profileImage = itemView.findViewById(R.id.img_profile);
            username = itemView.findViewById(R.id.txt_username);
            time = itemView.findViewById(R.id.txt_time);
            comment = itemView.findViewById(R.id.txt_comment);
        }
    }
}
