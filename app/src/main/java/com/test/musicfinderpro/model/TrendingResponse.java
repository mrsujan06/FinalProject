package com.test.musicfinderpro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sujan on 13/03/2018.
 */

public class TrendingResponse {

    @SerializedName("trending")
    @Expose
    private List<Trending> trending = null;

    public List<Trending> getTrending() {
        return trending;
    }

    public void setTrending(List<Trending> trending) {
        this.trending = trending;
    }
}
