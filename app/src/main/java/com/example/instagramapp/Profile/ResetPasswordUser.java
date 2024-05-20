package com.example.instagramapp.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.instagramapp.Login;
import com.example.instagramapp.MainActivity;
import com.example.instagramapp.ModelAPI.ChangePasswordRespone;
import com.example.instagramapp.ModelAPI.ResetPassword;
import com.example.instagramapp.R;
import com.example.instagramapp.retrofit.APIService;
import com.example.instagramapp.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordUser extends AppCompatActivity {

    private EditText oldPasswordInput, newPasswordInput, confirmPasswordInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        oldPasswordInput = findViewById(R.id.old_password_input);
        newPasswordInput = findViewById(R.id.new_password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
    }

    public void setNewPasswordBtn(View view) {
        String oldPassword = oldPasswordInput.getText().toString();
        String newPassword = newPasswordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị hộp thoại xác nhận
        new AlertDialog.Builder(this)
                .setTitle("Confirm Password Change")
                .setMessage("Are you sure you want to change your password?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Nếu người dùng chọn "Yes", tiếp tục với việc thay đổi mật khẩu
                        changePassword(oldPassword, newPassword);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void changePassword(String oldPassword, String newPassword) {
        APIService apiService = RetrofitClient.getRetrofitAuth(ResetPasswordUser.this).create(APIService.class);
        ResetPassword codeAPI = new ResetPassword(oldPassword, newPassword);

        Call<ChangePasswordRespone> call = apiService.changePassword(codeAPI);
        call.enqueue(new Callback<ChangePasswordRespone>() {
            @Override
            public void onResponse(Call<ChangePasswordRespone> call, Response<ChangePasswordRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChangePasswordRespone changePasswordResponse = response.body();
                    if (changePasswordResponse.getMessage() != null) {
                        Toast.makeText(ResetPasswordUser.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        goToLoginScreen();
                    } else {
                        Toast.makeText(ResetPasswordUser.this, changePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetPasswordUser.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordRespone> call, Throwable t) {
                Toast.makeText(ResetPasswordUser.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(ResetPasswordUser.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goToHomeFromSetNewPassword(View view) {
        finish();
    }
}