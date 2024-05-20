package com.example.instagramapp.ModelAPI;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRespone {
    private boolean success;

    @SerializedName("message")
    private String message;

    // Getters và Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
