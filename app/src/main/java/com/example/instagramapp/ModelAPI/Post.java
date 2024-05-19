package com.example.instagramapp.ModelAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//
public class Post implements Serializable {
    @SerializedName("postId")
    private int postId;
    @SerializedName("postText")
    private String postText;
    @SerializedName("postCreateTime")
    private Date postCreateTime;
    @SerializedName("postImage")
    private String postImage;
    @SerializedName("countLike")
    private int countLike;
    @SerializedName("liked")
    private boolean liked;
    @SerializedName("userDTO")
    private User user;


}
