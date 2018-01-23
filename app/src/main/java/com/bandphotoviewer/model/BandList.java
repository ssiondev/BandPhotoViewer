package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 2018. 1. 22..
 */

public class BandList {

    List<Band> bands;

    @SerializedName("name")
    String name;

    @SerializedName("band_key")
    String bandKey;

    @SerializedName("cover")
    String cover;

    @SerializedName("member_count")
    int memberCount;

    public List<Band> getBands() {
        return bands;
    }

    public String getName() {
        return name;
    }

    public String getBandKey() {
        return bandKey;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public String getCover() {
        return cover;
    }
}
