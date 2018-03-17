package com.test.musicfinderpro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sujan on 13/03/2018.
 */

public class Trending implements Serializable{

    @SerializedName("idTrend")
    @Expose
    private String idTrend;
    @SerializedName("intChartPlace")
    @Expose
    private String intChartPlace;
    @SerializedName("idArtist")
    @Expose
    private String idArtist;
    @SerializedName("idAlbum")
    @Expose
    private String idAlbum;
    @SerializedName("idTrack")
    @Expose
    private Object idTrack;
    @SerializedName("strArtistMBID")
    @Expose
    private String strArtistMBID;
    @SerializedName("strAlbumMBID")
    @Expose
    private String strAlbumMBID;
    @SerializedName("strTrackMBID")
    @Expose
    private Object strTrackMBID;
    @SerializedName("strArtist")
    @Expose
    private String strArtist;
    @SerializedName("strAlbum")
    @Expose
    private String strAlbum;
    @SerializedName("strTrack")
    @Expose
    private Object strTrack;
    @SerializedName("strArtistThumb")
    @Expose
    private String strArtistThumb;
    @SerializedName("strAlbumThumb")
    @Expose
    private String strAlbumThumb;
    @SerializedName("strTrackThumb")
    @Expose
    private Object strTrackThumb;
    @SerializedName("strCountry")
    @Expose
    private String strCountry;
    @SerializedName("strType")
    @Expose
    private String strType;
    @SerializedName("intWeek")
    @Expose
    private String intWeek;
    @SerializedName("dateAdded")
    @Expose
    private String dateAdded;

    public String getIdTrend() {
        return idTrend;
    }

    public void setIdTrend(String idTrend) {
        this.idTrend = idTrend;
    }

    public String getIntChartPlace() {
        return intChartPlace;
    }

    public void setIntChartPlace(String intChartPlace) {
        this.intChartPlace = intChartPlace;
    }

    public String getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(String idArtist) {
        this.idArtist = idArtist;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Object getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(Object idTrack) {
        this.idTrack = idTrack;
    }

    public String getStrArtistMBID() {
        return strArtistMBID;
    }

    public void setStrArtistMBID(String strArtistMBID) {
        this.strArtistMBID = strArtistMBID;
    }

    public String getStrAlbumMBID() {
        return strAlbumMBID;
    }

    public void setStrAlbumMBID(String strAlbumMBID) {
        this.strAlbumMBID = strAlbumMBID;
    }

    public Object getStrTrackMBID() {
        return strTrackMBID;
    }

    public void setStrTrackMBID(Object strTrackMBID) {
        this.strTrackMBID = strTrackMBID;
    }

    public String getStrArtist() {
        return strArtist;
    }

    public void setStrArtist(String strArtist) {
        this.strArtist = strArtist;
    }

    public String getStrAlbum() {
        return strAlbum;
    }

    public void setStrAlbum(String strAlbum) {
        this.strAlbum = strAlbum;
    }

    public Object getStrTrack() {
        return strTrack;
    }

    public void setStrTrack(Object strTrack) {
        this.strTrack = strTrack;
    }

    public String getStrArtistThumb() {
        return strArtistThumb;
    }

    public void setStrArtistThumb(String strArtistThumb) {
        this.strArtistThumb = strArtistThumb;
    }

    public String getStrAlbumThumb() {
        return strAlbumThumb;
    }

    public void setStrAlbumThumb(String strAlbumThumb) {
        this.strAlbumThumb = strAlbumThumb;
    }

    public Object getStrTrackThumb() {
        return strTrackThumb;
    }

    public void setStrTrackThumb(Object strTrackThumb) {
        this.strTrackThumb = strTrackThumb;
    }

    public String getStrCountry() {
        return strCountry;
    }

    public void setStrCountry(String strCountry) {
        this.strCountry = strCountry;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getIntWeek() {
        return intWeek;
    }

    public void setIntWeek(String intWeek) {
        this.intWeek = intWeek;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
