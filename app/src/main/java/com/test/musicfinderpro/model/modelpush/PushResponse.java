package com.test.musicfinderpro.model.modelpush;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.musicfinderpro.model.Result;

import java.util.List;

/**
 * Created by Sujan on 05/03/2018.
 */

public class PushResponse {

    @SerializedName("multicast_id")
    @Expose
    private Long multicastId;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("failure")
    @Expose
    private Integer failure;
    @SerializedName("canonical_ids")
    @Expose
    private Integer canonicalIds;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Long getMulticastId() {
        return multicastId;
    }

    public Integer getSuccess() {
        return success;
    }

    public Integer getFailure() {
        return failure;
    }

    public Integer getCanonicalIds() {
        return canonicalIds;
    }

    public List<Result> getResults() {
        return results;
    }

}
