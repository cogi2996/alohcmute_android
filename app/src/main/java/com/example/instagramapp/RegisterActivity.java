package com.example.instagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.instagramapp.ModelAPI.Account;
import com.example.instagramapp.ModelAPI.AuthenticationResponse;
import com.example.instagramapp.ModelAPI.Department;
import com.example.instagramapp.ModelAPI.DepartmentResponse;
import com.example.instagramapp.ModelAPI.Major;
import com.example.instagramapp.ModelAPI.MajorResponse;
import com.example.instagramapp.ModelAPI.User;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Spinner spDepartment, spMajor;
    APIService apiService;


    // Fields for user input
    private EditText layoutLastName;
    private EditText layoutMidName;
    private EditText layoutFirstName;
    private EditText layoutEmail;
    private EditText layoutPass;
    private EditText layoutAddress;
    private EditText layoutBiography;
    private EditText layoutPhone;
    private Button cirLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        DepartmentHandler();
        // Register button click event
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String lastName = layoutLastName.getText().toString();
                String midName = layoutMidName.getText().toString();
                String firstName = layoutFirstName.getText().toString();
                String email = layoutEmail.getText().toString();
                String pass = layoutPass.getText().toString();
                String address = layoutAddress.getText().toString();
                String biography = layoutBiography.getText().toString();
                String department = ((Department) spDepartment.getSelectedItem()).getName();
                String major = ((Major) spMajor.getSelectedItem()).getName();
                String phone = layoutPhone.getText().toString();
                // Validate user input
                if (lastName.isEmpty()) {
                    layoutLastName.setError("Last name is required");
                    layoutLastName.requestFocus();
                    return;
                }
                if (midName.isEmpty()) {
                    layoutMidName.setError("Middle name is required");
                    layoutMidName.requestFocus();
                    return;
                }
                if (firstName.isEmpty()) {
                    layoutFirstName.setError("First name is required");
                    layoutFirstName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    layoutEmail.setError("Email is required");
                    layoutEmail.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    layoutPass.setError("Password is required");
                    layoutPass.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    layoutAddress.setError("Address is required");
                    layoutAddress.requestFocus();
                    return;
                }
                if (biography.isEmpty()) {
                    layoutBiography.setError("Biography is required");
                    layoutBiography.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    layoutPhone.setError("Phone is required");
                    layoutPhone.requestFocus();
                    return;
                }

                // Create a new user object
                User user = new User();
                user.setLastName(lastName);
                user.setMidName(midName);
                user.setFirstName(firstName);
                user.setAddress(address);
                user.setBiography(biography);
                user.setDepartment(department);
                user.setMajor(major);
                user.setPhone(phone);

                Account account = Account.builder()
                        .email(email)
                        .password(pass)
                        .userDTO(user)
                        .build();

                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                apiService.register(account).enqueue(new Callback<AuthenticationResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                        if (response.isSuccessful()) {
                            // Handle the response here
                            Log.d("LLLLLLLL", "onResponse: " + response.body().getAccess_token());
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                        Log.d("KKKKK", "onFailure: " + t.getMessage());
                    }
                });


            }
                // Call the API to register the user

        });

    }

    public void AnhXa() {
        //Anh xa
        spDepartment = (Spinner) findViewById(R.id.layout_department);
        spMajor = (Spinner) findViewById(R.id.layout_major);
        layoutLastName = findViewById(R.id.layout_lastName);
        layoutMidName = findViewById(R.id.layout_midName);
        layoutFirstName = findViewById(R.id.layout_firstName);
        layoutEmail = findViewById(R.id.layout_email);
        layoutPass = findViewById(R.id.layout_pass);
        layoutAddress = findViewById(R.id.layout_address);
        layoutBiography = findViewById(R.id.layout_biography);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        layoutPhone = findViewById(R.id.layout_phone);

    }

    public void DepartmentHandler() {
        apiService.getAllDepartment().enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, @NonNull Response<DepartmentResponse> response) {
                if (response.isSuccessful()) {
                    List<Department> listDepartment = response.body().getData();
                    renderDepartment(listDepartment);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<DepartmentResponse> call, Throwable t) {
                Log.d("KKKKK", "onFailure: " + t.getMessage());
            }
        });
    }


    public void renderDepartment(List<Department> listDepartment) {
        //Set up spinner
        spDepartment = findViewById(R.id.layout_department);
        // Tạo ArrayAdapter
        ArrayAdapter<Department> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDepartment);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Thiết lập ArrayAdapter cho Spinner
        spDepartment.setAdapter(adapter);

        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Department selectedDepartment = (Department) parent.getItemAtPosition(position);
                int departmentId = selectedDepartment.getId();
                Log.d("KKKKK", "onItemSelected: " + departmentId);
                // Handle the selected department ID here
                MajorHandler(departmentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected if necessary
            }
        });
    }

    public void MajorHandler(int id) {
        apiService.getMajorByDepartmentId(id).enqueue(new Callback<MajorResponse>() {
            @Override
            public void onResponse(Call<MajorResponse> call, Response<MajorResponse> response) {
                if (response.isSuccessful()) {
                    List<Major> listMajor = response.body().getData();
                    renderMajor(listMajor);
                }
            }

            @Override
            public void onFailure(Call<MajorResponse> call, Throwable t) {
                Log.d("KKKKK", "onFailure: " + t.getMessage());
            }
        });

    }

    public void renderMajor(List<Major> listMajor) {
        // Tạo một Spinner mới hoặc tìm Spinner hiện tại
        Spinner spMajor = findViewById(R.id.layout_major); // Thay đổi id layout_major thành id thực tế của Spinner Major
        // Tạo ArrayAdapter
        ArrayAdapter<Major> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listMajor);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Thiết lập ArrayAdapter cho Spinner
        spMajor.setAdapter(adapter);

        spMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Major selectedMajor = (Major) parent.getItemAtPosition(position);
                int majorId = selectedMajor.getId();
                Log.d("KKKKK", "onItemSelected: " + majorId);
                // Handle the selected major ID here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected if necessary
            }
        });
    }


}