package com.example.instagramapp.ModelAPI;

import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("userId")
    private String userId;
    @SerializedName("phone")
    private String phone;
    @SerializedName("createDate")
    private String createDate;
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
    private String gender;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("dob")
    private String dob;

    public Users() {
    }

    public Users(String userId, String phone, String createDate, String firstName, String midName, String lastName, String address, String biography, String major, String department, String gender, String avatar, String dob) {
        this.userId = userId;
        this.phone = phone;
        this.createDate = createDate;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.address = address;
        this.biography = biography;
        this.major = major;
        this.department = department;
        this.gender = gender;
        this.avatar = avatar;
        this.dob = dob;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}