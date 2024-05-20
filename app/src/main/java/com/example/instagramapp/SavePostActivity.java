package com.example.instagramapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.Adapter.SavePostAdapter;
import com.example.instagramapp.ModelAPI.SavePost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavePostActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SavePostAdapter savePostAdapter;
    private List<SavePost> savePostList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String userId = "41";

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewSave);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        savePostList = new ArrayList<>();
        savePostAdapter = new SavePostAdapter(this, savePostList);
        recyclerView.setAdapter(savePostAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("savePost");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                savePostList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SavePost savePost = postSnapshot.getValue(SavePost.class);
                    savePostList.add(savePost);
                }
                savePostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}