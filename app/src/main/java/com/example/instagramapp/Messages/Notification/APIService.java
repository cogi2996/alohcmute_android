package com.example.instagramapp.Messages.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=BKvU0HFHO4Frz1CmMofcCvUSCHihGID_vn0V0PE3FqpBYuwuz6gsobMgiDFx3KUyOe67Z45-Q3-EcpPAa0fX1RM"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);


}
