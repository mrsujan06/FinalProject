package com.test.musicfinderpro.api;


import com.test.musicfinderpro.model.modelpush.PushRequestBody;
import com.test.musicfinderpro.model.modelpush.PushResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Sujan on 05/03/2018.
 */

public interface SendPushApi {

    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAAUnrwiKk:APA91bFe8ZQQLEN2VEBOmC_xgDsgaSduyrk1Iol7AFxcX9Kmtmn3aKxpl6fIyl2DZgiyXj6eJLg2bBSknFIycwsr3eueDWCKD2bJfZ0kZ4aJAxgozjHGVM2RbPA2ouSCYAdmtQn9OUPm"
    })
    @POST("/fcm/send")
    Observable<PushResponse>  sendPush(@Body PushRequestBody pushRequestBody);
}
