package com.example.instagramapp.Post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.example.instagramapp.Home;
import com.example.instagramapp.R;
import com.example.instagramapp.Utils.methods;

public class PostActivity extends AppCompatActivity {

    ImageView postNow,backFromPost,addedImage;
    EditText addedCaption,AddedTag;

    DatabaseReference databaseReference,data;
    StorageReference storageReference,ref;

    methods method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postNow = (ImageView)findViewById(R.id.post_now);
        backFromPost = (ImageView)findViewById(R.id.back_from_post);
        addedImage = (ImageView)findViewById(R.id.added_image);
        addedCaption = (EditText)findViewById(R.id.added_caption);
        AddedTag = (EditText)findViewById(R.id.added_tags);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

}