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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    RecyclerView recyclerViewComments;
    EditText commentText;
    ImageView sendButton, closeButton;
    CommentAdapter commentAdapter;
    List<Comment> comments;
    DatabaseReference commentsReference;
    private DatabaseReference myRef;

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
//        String postId = getIntent().getExtras().getString("2","2");
        commentsReference = FirebaseDatabase.getInstance().getReference().child("comments").child(postId);

        readComments();
//        boolean openKeyboard = getIntent().getExtras().getBoolean("openKeyboard");// có chức năng mở bàn phím
//        if (openKeyboard) {
//            commentText.requestFocus();
////            KeyboardManager.openKeyboard(this);
//        }

        sendButton.setOnClickListener(v -> {
            String commentString = commentText.getText().toString();
            if (commentString.isEmpty()) return;
            DocumentReference userReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid());
//            String commentId = DateTimeFormatter.getCurrentTime();
            String commentId = commentsReference.push().getKey(); // get a unique id for the comment
            Comment comment = new Comment(userReference.getId(), commentString, commentId);

//            KeyboardManager.closeKeyboard(this);
            commentText.clearFocus();
            commentText.setText("");
            commentsReference.child(commentId).setValue(comment).addOnSuccessListener(unused -> {
                comments.add(comment);
                commentAdapter.notifyDataSetChanged();
            });
        });
        closeButton.setOnClickListener(view -> finish());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View focused = getCurrentFocus();
        if (focused != null) {
//            KeyboardManager.closeKeyboard(this);
        }
        return super.dispatchTouchEvent(event);
    }

    private void readComments() {
        commentsReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                comments.clear();
                for (DataSnapshot commentSnapshot : task.getResult().getChildren()) {
                    comments.add(commentSnapshot.getValue(Comment.class));
                }
                commentAdapter.notifyDataSetChanged();
            }
        });
    }
}