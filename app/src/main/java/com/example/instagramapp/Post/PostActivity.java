package com.example.instagramapp.Post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramapp.ModelAPI.AuthenticationRequest;
import com.example.instagramapp.ModelAPI.AuthenticationResponse;
import com.example.instagramapp.ModelAPI.NewPostRequest;
import com.example.instagramapp.ModelAPI.Post;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
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
import java.util.List;
import java.util.UUID;

import com.example.instagramapp.Home;
import com.example.instagramapp.R;
import com.example.instagramapp.Utils.methods;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private static final String TAG ="PostActivity" ;
    ImageView backFromPost,addedImage;
    EditText addedCaption,AddedTag;
    Button btnDang;
    DatabaseReference databaseReference,data;
    StorageReference storageReference,reff;

    methods method;
    APIService apiService;
    String postText;
    String postImage;
    int PICK_IMAGE_REQUEST=1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        backFromPost = (ImageView)findViewById(R.id.back_from_post);
        addedImage = (ImageView)findViewById(R.id.added_image);
        addedCaption = (EditText)findViewById(R.id.added_caption);
        btnDang = (Button)findViewById(R.id.btn_dang);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnDang.setOnClickListener((v) ->{NewPost();});
        openFileChooser();
    }

    private void NewPost() {
        //postText = addedCaption.getEditText().getText().toString().trim();
        //postImage = addedImage.getEditText().getText().toString().trim();
        //postText = addedCaption.getText().toString().trim();
        //postImage = addedImage.getText().toString().trim();
        /*
        postText = addedCaption.getText().toString().trim();
        postImage = imageUri.toString();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        NewPostRequest newPostRequest = NewPostRequest.builder()
                .postText(postText)
                .postImage(postImage)
                .build();*/
        // login

        if (imageUri != null) {
            storageReference = FirebaseStorage.getInstance().getReference();
            String imageId = java.util.UUID.randomUUID().toString();
            reff = storageReference.child("anh/post/" + imageId + "/ImagePost");
            //reff = storageReference.child("photos/post/" + "/ImagePost");
            reff.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            postText = addedCaption.getText().toString().trim();
                            postImage = uri.toString();
                            apiService = RetrofitClient.getRetrofit().create(APIService.class);
                            NewPostRequest newPostRequest = NewPostRequest.builder()
                                    .postText(postText)
                                    .postImage(postImage)
                                    .build();
                            apiService.createPost(newPostRequest).enqueue(new Callback<Post>() {
                                @Override
                                public void onResponse(Call<Post> call, Response<Post> response) {
                                    Log.d(TAG, "Loi o onResponse");
                                    storageReference = FirebaseStorage.getInstance().getReference();
                                    btnDang.setVisibility(View.GONE);
                                    if (response.isSuccessful()) {
                                        Log.d("test", response.body().getPostImage());
                                        Log.d("test", response.body().getPostText());
                                        Toast.makeText(PostActivity.this, "Posted successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PostActivity.this,Home.class));
                                        finish();
                                    } else {
                                        // Handle the error
                                        Log.d("test", "here2");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Post> call, Throwable throwable) {
                                    Log.d("LogFail", throwable.getMessage());
                                    Log.d(TAG, "Loi o onFailure");
                                }
                            });
                        }
                    });

                }
            });
        }
        //Log.d("POINT0", newPostRequest.getPostText() + " " + newPostRequest.getPostImage());
        /*
        apiService.createPost(newPostRequest).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "Loi o onResponse");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                Log.d("LogFail", throwable.getMessage());
                Log.d(TAG, "Loi o onFailure");
            }
        });*/
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            addedImage.setImageURI(imageUri);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}