package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

public class LockAccountActivity extends AppCompatActivity {

    // Declare your views
    private Button cirLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_account);
        AnhXa();
        cirLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LockAccountActivity.this, Login.class);
            startActivity(intent);
        });
    }

    public void AnhXa(){
        // Initialize your views
        cirLoginButton = findViewById(R.id.cirLoginButton);


    }
}