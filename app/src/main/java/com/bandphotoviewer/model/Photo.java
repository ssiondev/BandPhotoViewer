package com.bandphotoviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 2017. 12. 20..
 */

public class Photo implements Parcelable {

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


    public Photo(Parcel in){
        readInParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readInParcel(Parcel in){
        photoKey = in.readString();
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
        created_at = in.readLong();
    }

    public void writeToParcel(Parcel dest, int flag){
        dest.writeString(photoKey);
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeLong(created_at);
    }

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

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {

        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }

    };
}

