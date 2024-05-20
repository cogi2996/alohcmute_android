package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

public class OTPActivity extends AppCompatActivity {

    EditText layout_otp;
    Button btn_otp;
    APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        AnhXa();
        apiService = RetrofitClient.getRetrofitAuth(OTPActivity.this).create(APIService.class);


    }

    public void AnhXa(){
        layout_otp = findViewById(R.id.layout_otp);
        btn_otp = findViewById(R.id.cirLoginButton);
    }


}