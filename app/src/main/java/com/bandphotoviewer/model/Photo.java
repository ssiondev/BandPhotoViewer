package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017. 12. 20..
 */

public class Photo {

    @SerializedName("photo_key")
    private String photoKey;   //사진 식별자

    @SerializedName("url")
    private String url; //사진 URL

    @SerializedName("width")
    private int width;  //사진 넓이

    @SerializedName("height")
    private int height; //사진 높이

    @SerializedName("created_at")
    private long created_at;    //생성 일시

    public String getPhotoKey() {
        return photoKey;
    }

    public void setPhotoKey(String photoKey) {
        this.photoKey = photoKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
