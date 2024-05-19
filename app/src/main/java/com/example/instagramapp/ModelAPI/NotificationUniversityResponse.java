package com.example.instagramapp.ModelAPI;

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
public class NotificationUniversityResponse implements Serializable {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<NotificationUniversity> listNotificationUniversity;
}
