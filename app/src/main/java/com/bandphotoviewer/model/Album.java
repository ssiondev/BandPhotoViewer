package com.bandphotoviewer.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017. 12. 20..
 */

public class Album {

    @SerializedName("photo_album_key")
    private String photoAlbumKey; //앨범 식별자

    @SerializedName("name")
    private String name;            //앨범명

    @SerializedName("photo_count")
    private int photoCount;    //앨범 내 사진 수

    @SerializedName("created_at")
    private long createdAt;    //앨범 생성일시


    protected Album(Parcel in) {
        photoAlbumKey = in.readString();
        name = in.readString();
        photoCount = in.readInt();
        createdAt = in.readLong();
    }

    public String getPhotoAlbumKey() {
        return photoAlbumKey;
    }

    public void setPhotoAlbumKey(String photoAlbumKey) {
        this.photoAlbumKey = photoAlbumKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

}
