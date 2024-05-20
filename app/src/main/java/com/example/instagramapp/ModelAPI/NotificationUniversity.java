package com.example.instagramapp.ModelAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationUniversity implements Serializable {
    @SerializedName("id")
    private int idNoti;
    @SerializedName("title")
    private String tittle;
    @SerializedName("text")
    private String discription;
}
