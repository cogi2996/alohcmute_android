package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.instagramapp.ModelAPI.OTPRequest;
import com.example.instagramapp.ModelAPI.OTPResponse;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    // Declare your views
    private EditText layoutOtp;
    private Button cirLoginButton;

    // Declare your API Service
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        AnhXa();

        // Initialize your API Service
        apiService = RetrofitClient.getRetrofitAuth(OTPActivity.this).create(APIService.class);

        // Listen for click event on cirLoginButton
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = layoutOtp.getText().toString();
                OTPRequest otpRequest = new OTPRequest(otp);
                Call<OTPResponse> call = apiService.registerOTP(otpRequest);
                call.enqueue(new Callback<OTPResponse>() {
                    @Override
                    public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            Toast.makeText(OTPActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                            Intent z = new Intent(OTPActivity.this, Home.class);
                            startActivity(z);
                            finish();

                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(OTPActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OTPResponse> call, Throwable t) {
                        // Handle network failure
                        Toast.makeText(OTPActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void AnhXa(){
        // Initialize your views
        layoutOtp = findViewById(R.id.layout_otp);
        cirLoginButton = findViewById(R.id.cirLoginButton);
    }
}