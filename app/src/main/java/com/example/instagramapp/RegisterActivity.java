package com.example.instagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.instagramapp.ModelAPI.Department;
import com.example.instagramapp.ModelAPI.DepartmentResponse;
import com.example.instagramapp.ModelAPI.Major;
import com.example.instagramapp.ModelAPI.MajorResponse;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    Spinner spDepartment, spMajor;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        DepartmentHandler();

    }

    public void AnhXa() {
        //Anh xa
        spDepartment= (Spinner) findViewById(R.id.layout_department);
        spMajor = (Spinner) findViewById(R.id.layout_major);
    }

    public void DepartmentHandler(){
        apiService.getAllDepartment().enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call,@NonNull Response<DepartmentResponse> response) {
                if(response.isSuccessful()){
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

    public void MajorHandler(int id){
        apiService.getMajorByDepartmentId(id).enqueue(new Callback<MajorResponse>() {
            @Override
            public void onResponse(Call<MajorResponse> call, Response<MajorResponse> response) {
                if(response.isSuccessful()){
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