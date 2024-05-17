package com.example.instagramapp.ModelAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable   {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
}
