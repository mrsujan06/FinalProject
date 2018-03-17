package com.test.musicfinderpro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sujan on 13/03/2018.
 */

public class ArtistResponse {

    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
