package com.example.instagramapp.modelFirebase;

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
public class ResponseDTO implements Serializable {
    @SerializedName("success")
    private String message;
    @SerializedName("data")
    private List<Post> listPost;

}
