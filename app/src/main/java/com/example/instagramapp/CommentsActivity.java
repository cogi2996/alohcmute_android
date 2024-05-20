package com.example.instagramapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.Adapter.Comment;
import com.example.instagramapp.Adapter.CommentAdapter;
import com.example.instagramapp.ModelAPI.CurrentUserResponse;
import com.example.instagramapp.ModelAPI.LikePostResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    RecyclerView recyclerViewComments;
    EditText commentText;
    ImageView sendButton, closeButton;
    CommentAdapter commentAdapter;
    List<Comment> comments;
    private APIService apiService;

    DatabaseReference commentsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        recyclerViewComments = findViewById(R.id.recyclerview_comments);
        commentText = findViewById(R.id.et_comment);
        sendButton = findViewById(R.id.btn_send_message);
        closeButton = findViewById(R.id.btn_close);

        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, comments);
        recyclerViewComments.setHasFixedSize(true);
        recyclerViewComments.setAdapter(commentAdapter);
        String postId = getIntent().getExtras().getString("postId");
        commentsReference = FirebaseDatabase.getInstance().getReference().child("comments").child(postId);
        apiService = RetrofitClient.getRetrofitAuth(CommentsActivity.this).create(APIService.class);
        readComments();
        sendButton.setOnClickListener(v -> {
            String commentString = commentText.getText().toString();
            if (commentString.isEmpty()) return;
            DocumentReference userReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid());
            // get infor current user public comment
            Call<CurrentUserResponse> call = apiService.getCurrentUser();
            call.enqueue(new Callback<CurrentUserResponse>() {
                @Override
                public void onResponse(Call<CurrentUserResponse> call, Response<CurrentUserResponse> response) {
                    if (response.isSuccessful()) {
                        User user = response.body().getUser();
                        String commentId = commentsReference.push().getKey(); // get a unique id for the comment
                        Comment comment = new Comment(String.valueOf(user.getUserId()), commentString, commentId);
                        commentText.clearFocus();
                        commentText.setText("");
                        commentsReference.child(commentId).setValue(comment).addOnSuccessListener(unused -> {
                            // The comment will be added through ChildEventListener
                        });
                    }
                }

                @Override
                public void onFailure(Call<CurrentUserResponse> call, Throwable t) {
                    // Handle error
                }
            });
//
//            String commentId = commentsReference.push().getKey(); // get a unique id for the comment
//            Comment comment = new Comment(userReference.getId(), commentString, commentId);
//
//            commentText.clearFocus();
//            commentText.setText("");
//            commentsReference.child(commentId).setValue(comment).addOnSuccessListener(unused -> {
//                // The comment will be added through ChildEventListener
//            });
        });

        closeButton.setOnClickListener(view -> finish());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View focused = getCurrentFocus();
        if (focused != null) {
            // Close keyboard if necessary
        }
        return super.dispatchTouchEvent(event);
    }

    private void readComments() {
        commentsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                comments.add(comment);
                commentAdapter.notifyItemInserted(comments.size() - 1);
                recyclerViewComments.scrollToPosition(comments.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Comment updatedComment = dataSnapshot.getValue(Comment.class);
                String commentId = updatedComment.getCommentId();

                for (int i = 0; i < comments.size(); i++) {
                    if (comments.get(i).getCommentId().equals(commentId)) {
                        comments.set(i, updatedComment);
                        commentAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String commentId = dataSnapshot.getKey();
                for (int i = 0; i < comments.size(); i++) {
                    if (comments.get(i).getCommentId().equals(commentId)) {
                        comments.remove(i);
                        commentAdapter.notifyItemRemoved(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle if you care about the order of comments
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}
