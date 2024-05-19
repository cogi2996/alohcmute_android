package com.example.instagramapp.ModelAPI;

import com.example.instagramapp.ModelAPI.Post;
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
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Post> listPost;

}
