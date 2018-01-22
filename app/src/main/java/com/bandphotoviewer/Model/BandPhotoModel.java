package com.bandphotoviewer.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017. 12. 20..
 */

public class BandPhotoModel extends AbstractModel implements Parcelable {

    private String photo_key;   //사진 식별자
    private String url; //사진 URL
    private int width;  //사진 넓이
    private int height; //사진 높이
    private long created_at;    //생성 일시

    public BandPhotoModel(){}

    public BandPhotoModel(Parcel in){
        readInParcel(in);
    }

    public BandPhotoModel(String photo_key, String url, int width, int height, long created_at) {
        this.photo_key = photo_key;
        this.url = url;
        this.width = width;
        this.height = height;
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void readInParcel(Parcel in){
        photo_key = in.readString();
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
        created_at = in.readLong();
    }

    public void writeToParcel(Parcel dest, int flag){
        dest.writeString(photo_key);
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeLong(created_at);
    }

    public String getPhoto_key() {
        return photo_key;
    }

    public void setPhoto_key(String photo_key) {
        this.photo_key = photo_key;
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

    public static final Creator<BandPhotoModel> CREATOR = new Creator<BandPhotoModel>(){

        @Override
        public BandPhotoModel createFromParcel(Parcel source) {
            final BandPhotoModel bandPhotoModel = new BandPhotoModel();
            bandPhotoModel.setPhoto_key(source.readString());
            bandPhotoModel.setUrl(source.readString());
            bandPhotoModel.setHeight(source.readInt());
            bandPhotoModel.setWidth(source.readInt());
            bandPhotoModel.setCreated_at(source.readLong());
            return bandPhotoModel;
        }

        @Override
        public BandPhotoModel[] newArray(int size) {
            return new BandPhotoModel[size];
        }
    };
}
