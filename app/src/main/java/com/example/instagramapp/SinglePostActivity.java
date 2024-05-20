package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.CheckedLikeResponse;
import com.example.instagramapp.ModelAPI.LikePostResponse;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePostActivity extends AppCompatActivity {
    private CircleImageView imgProfile;
    private TextView txtUsername;
    private ImageView postImage;
    private TextView postText;
    private ImageView btnLike;
    private ImageView btnComment;
    private ImageView btnSave;
    private TextView countLike;
    private TextView caption;
    private TextView txtCommentCount;
    private TextView postCreateTime;
    APIService apiService;
    private SimpleDateFormat simpleDateFormat;
    Post post;

    int userId = 42;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        AnhXa();
        // Get the post ID from the intent
//        int postId = getIntent().getIntExtra("postId", 65);
        int postId = 65;
        // Create the API service
        apiService = RetrofitClient.getRetrofitAuth(this).create(APIService.class);

        // Load the post
        apiService.getPostById(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    post = response.body();

                    // Update the views with the post data
                    txtUsername.setText(post.getUser().getFullName());
                    postText.setText(post.getPostText());
                    countLike.setText(String.valueOf(post.getCountLike()));
                    // For images, you might need to use a library like Picasso or Glide
                    // For example, if you're using Glide:
                    Glide.with(SinglePostActivity.this).load(post.getPostImage()).into(postImage);
                    Glide.with(SinglePostActivity.this).load(post.getUser().getAvatar()).into(imgProfile);

                    // check user like post
                    update();


                    // If the post is liked by the user, change the like button image


                    // Set visibility of countLike, caption, txtCommentCount based on their availability
                    countLike.setVisibility(post.getCountLike() > 0 ? View.VISIBLE : View.GONE);

                    // Assuming getCommentCount() method exists in Post model
//                    txtCommentCount.setText(String.format("View all %d comments", post.getCountLike()));
                    txtCommentCount.setVisibility(post.getCountLike() > 0 ? View.VISIBLE : View.GONE);

                    // Format and set the post creation time
                    // Assuming getFormattedTime() method exists in Post model
                    simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa");
                    String dateTime = simpleDateFormat.format(post.getPostCreateTime()).toString();
                    postCreateTime.setText(dateTime);


                } else {
                    // Handle error: unsuccessful response
                    Toast.makeText(SinglePostActivity.this, "Failed to load post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                // Handle error: network failure
                Toast.makeText(SinglePostActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listeners for the like and comment buttons
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.isLiked()) {
                    post.setCountLike(post.getCountLike() - 1);
                    post.setLiked(false);
                    btnLike.setImageResource(R.drawable.ic_heart_outlined);
                    apiService.unlikePost(userId, post.getPostId()).enqueue(new Callback<LikePostResponse>() {
                        @Override
                        public void onResponse(Call<LikePostResponse> call, Response<LikePostResponse> response) {
                            if (response.isSuccessful()) {
                                // Update the like count on the UI
                                countLike.setText(String.valueOf(post.getCountLike()));
                                update();
                            }
                        }

                        @Override
                        public void onFailure(Call<LikePostResponse> call, Throwable t) {
                            // Handle error: network failure
                            Toast.makeText(SinglePostActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    post.setCountLike(post.getCountLike() + 1);
                    post.setLiked(true);
                    btnLike.setImageResource(R.drawable.ic_heart);
                    apiService.likePost(userId, post.getPostId()).enqueue(new Callback<LikePostResponse>() {
                        @Override
                        public void onResponse(Call<LikePostResponse> call, Response<LikePostResponse> response) {
                            if (response.isSuccessful()) {
                                // Update the like count on the UI
                                countLike.setText(String.valueOf(post.getCountLike()));
                                update();
                            }
                        }

                        @Override
                        public void onFailure(Call<LikePostResponse> call, Throwable t) {
                            // Handle error: network failure
                            Toast.makeText(SinglePostActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle comment button click
                // ...
                Intent intent = new Intent(SinglePostActivity.this, CommentsActivity.class);
                Log.d("jksdjfjjj", "onBindViewHolder: "+post.getPostId());
                intent.putExtra("postId", String.valueOf(post.getPostId()));
                SinglePostActivity.this.startActivity(intent);
            }
        });
    }

    public void update(){
        if (post.isLiked()) {
            btnLike.setImageResource(R.drawable.ic_heart);
        } else {
            btnLike.setImageResource(R.drawable.ic_heart_outlined);
        }
        int n = post.getCountLike();
        if (n > 0) {
            countLike.setVisibility(View.VISIBLE);
            String str = n + " lượt thích";
            countLike.setText(str);
        } else {
            countLike.setVisibility(View.GONE);
        }
    }

    public void AnhXa() {
        imgProfile = findViewById(R.id.img_profile);
        txtUsername = findViewById(R.id.txt_username);
        postImage = findViewById(R.id.postImage);
        postText = findViewById(R.id.postText);
        btnLike = findViewById(R.id.btn_like);
        btnComment = findViewById(R.id.btn_comment);
        btnSave = findViewById(R.id.btn_save);
        countLike = findViewById(R.id.countLike);
        caption = findViewById(R.id.caption);
        txtCommentCount = findViewById(R.id.txt_comment_count);
        postCreateTime = findViewById(R.id.postCreateTime);
    }

}