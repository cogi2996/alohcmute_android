package com.example.instagramapp.Messages.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=BAVZJ4OplBlYcLGWrsuf12IXxgBPeZWy0tZmToXfo855EOooKD3JBj9_YsDJ7QIFuJcbfF-NMz3Cc_tlwfWk1nQ"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);


}
