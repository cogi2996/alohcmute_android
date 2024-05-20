package com.example.instagramapp.ModelAPI;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckedLikeResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private boolean liked;

}
