package com.test.musicfinderpro.model.modelpush;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sujan on 05/03/2018.
 */

public class Result {

    @SerializedName("message_id")
    @Expose
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
