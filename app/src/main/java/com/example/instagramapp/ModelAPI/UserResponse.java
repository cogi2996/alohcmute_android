package com.example.instagramapp.ModelAPI;

import com.example.instagramapp.ModelAPI.Users;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private com.example.instagramapp.ModelAPI.Users user;

    public UserResponse() {

    }

    public UserResponse(String message, com.example.instagramapp.ModelAPI.Users user) {
        this.message = message;
        this.user = user;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.example.instagramapp.ModelAPI.Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
