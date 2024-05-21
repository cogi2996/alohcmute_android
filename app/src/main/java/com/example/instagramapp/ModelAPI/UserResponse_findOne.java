package com.example.instagramapp.ModelAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse_findOne {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private User user;
}
