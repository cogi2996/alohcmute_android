package com.example.instagramapp.ModelAPI;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private int userId;
    private String phone;
    private Date createDate;
    private Date lastLogin;
    private String firstName;
    private String midName;
    private String lastName;
    private String address;
    private String biography;
    private String major;
    private String department;
    private String text;
    private String avatar;
    private int gender;
    private Date dob;

    public String getFullName() {
        return getLastName() + " " + getMidName() + " " + getFirstName();
    }
}
