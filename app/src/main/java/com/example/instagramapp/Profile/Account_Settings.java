package com.example.instagramapp.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import com.example.instagramapp.Login;
import com.example.instagramapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account_Settings extends AppCompatActivity {

    TextView editProfile,logout;
    APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__settings);

        editProfile = (TextView)findViewById(R.id.edit_profile);
        logout = (TextView)findViewById(R.id.logout);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Account_Settings.this,EditProfile.class);
//                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                // Create an APIService instance
                apiService = RetrofitClient.getRetrofitAuth(Account_Settings.this).create(APIService.class);

                // Call the logout method
                Call<Void> call = apiService.logout();
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Clear the token
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("access_token");
                            editor.apply();

                            // Redirect the user to the login screen
                            Intent intent = new Intent(Account_Settings.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Handle the error
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle the error
                    }
                });
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account_Settings.this,ResetPasswordUser.class);
                startActivity(intent);
            }
        });


    }
}