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
public class User implements Serializable {
    @SerializedName("userId")
    private int userId;
    @SerializedName("phone")
    private String phone;
    @SerializedName("createDate")
    private Date createDate;
    @SerializedName("lastLogin")
    private String lastLogin;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("midName")
    private String midName;

    @SerializedName("lastName")
    private String lastName;
    @SerializedName("address")
    private String address;
    @SerializedName("biography")
    private String biography;

    @SerializedName("major")
    private String major;
    @SerializedName("department")
    private String department;
    @SerializedName("gender")
    private int gender;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("dob")
    private Date dob;

    @SerializedName("isFollowed")
    private int isFollowed;
}
