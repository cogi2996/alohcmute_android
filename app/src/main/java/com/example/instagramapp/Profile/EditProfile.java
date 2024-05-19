package com.example.instagramapp.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.instagramapp.ModelAPI.UserResponse;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.example.instagramapp.R;
import com.example.instagramapp.ModelAPI.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    ImageView mProfilePhoto;
    EditText firstName, middleName, lastName, bio, major, department, address;
    TextView Email, phoneNumber, gender, userId;
    TextView submit;
    int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;

    StorageReference storageReference, reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userId = (TextView) findViewById(R.id.userId);
        userId.setText(String.valueOf(41));

        mProfilePhoto = (ImageView) findViewById(R.id.user_img);
        firstName = (EditText) findViewById(R.id.firstName);
        middleName = (EditText) findViewById(R.id.middleName);
        lastName = (EditText) findViewById(R.id.lastName);
        bio = (EditText) findViewById(R.id.Bio);
        major = (EditText) findViewById(R.id.major);
        department = (EditText) findViewById(R.id.department);
        address = (EditText) findViewById(R.id.address);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        Email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);

        submit = (TextView) findViewById(R.id.btnDone);

        final UserResponse updatedUser = new UserResponse();
        getUserData(userId.getText().toString());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.v("UserId", userId.getText().toString());
//                Log.v("EditText", firstName.getText().toString());
                final Users user = new Users(
                        userId.getText().toString(),
                        phoneNumber.getText().toString(),
                        "",
                        firstName.getText().toString(),
                        middleName.getText().toString(),
                        lastName.getText().toString(),
                        bio.getText().toString(),
                        major.getText().toString(),
                        department.getText().toString(),
                        address.getText().toString(),
                        gender.getText().toString(),
                        "",
                        ""
                );
//
                updatedUser.setUser(user);
                callPutUpdateProfile(updatedUser);
            }
        });

        mProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void getUserData(String userId) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<UserResponse> call = apiService.getUser(userId);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Users userResponse = response.body().getUser();
                    // Now you can use the userResponse object to update your UI
                    if (response.body().getMessage().equals("success")) {
                        Log.d("test", "here");
                        firstName.setText(userResponse.getFirstName());
                        middleName.setText(userResponse.getMidName());
                        lastName.setText(userResponse.getLastName());
                        bio.setText(userResponse.getBiography());
                        major.setText(userResponse.getMajor());
                        department.setText(userResponse.getDepartment());
                        address.setText(userResponse.getAddress());
                        phoneNumber.setText(userResponse.getPhone());

                        if (userResponse.getAvatar() != null && !userResponse.getAvatar().isEmpty()) {
                            Glide.with(EditProfile.this)
                                    .load(userResponse.getAvatar())
                                    .into(mProfilePhoto);
                            Uri avatarUri = Uri.parse(userResponse.getAvatar());
                            mProfilePhoto.setImageURI(avatarUri);
                        }
                    }
                } else {
                    // Handle the error
                    Log.d("test", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle the error
//                Log.d("test", "here3");
                Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT);
            }
        });

//        Log.d("test", "here4");
    }

    private void callPutUpdateProfile(UserResponse users) {

        if (imageUri != null) {
            storageReference = FirebaseStorage.getInstance().getReference();
            reff = storageReference.child("photos/users/" + "41" + "/profilePhoto");
            reff.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Users user = users.getUser();
                            user.setAvatar(uri.toString());
                            users.setUser(user);
                            APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                            Call<UserResponse> call = apiService.getEditProfile(users.getUser());
                            call.enqueue(new Callback<UserResponse>() {
                                @Override
                                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                    Toast.makeText(EditProfile.this, "Profile Update", Toast.LENGTH_SHORT);
                                    storageReference = FirebaseStorage.getInstance().getReference();
                                    submit.setVisibility(View.GONE);
                                    if (response.isSuccessful()) {
                                        Log.d("test", response.body().getMessage());
                                    } else {
                                        // Handle the error
                                        Log.d("test", "here2");
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserResponse> call, Throwable throwable) {
                                    Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT);
                                }
                            });
                        }
                    });

                }
            });
        }
    }

    private void openFileChooser () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            mProfilePhoto.setImageURI(imageUri);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}