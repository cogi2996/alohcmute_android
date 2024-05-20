package com.example.instagramapp.ModelAPI;

public class SavePost {
    private String userTitle;
    private String timeStamp;
    private String userImgUrl;

    public SavePost() {
    }

    public SavePost(String userTitle, String timeStamp, String userImgUrl) {
        this.userTitle = userTitle;
        this.timeStamp = timeStamp;
        this.userImgUrl = userImgUrl;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

}
