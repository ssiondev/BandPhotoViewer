package com.bandphotoviewer.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017. 12. 20..
 */

public class BandAlbumModel implements Parcelable {

    private String photo_album_key; //앨범 식별자
    private String name;            //앨범명
    private int photo_count;    //앨범 내 사진 수
    private long created_at;    //앨범 생성일시


    public BandAlbumModel() {
    }

    public BandAlbumModel(Parcel in){
        readInParcel(in);
    }

    public BandAlbumModel(String photo_album_key, String name, int photo_count, Long created_at) {
        this.photo_album_key = photo_album_key;
        this.name = name;
        this.photo_count = photo_count;
        this.created_at = created_at;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void readInParcel(Parcel in) {
        photo_album_key = in.readString();
        name = in.readString();
        photo_count = in.readInt();
        created_at = in.readLong();
    }

    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(photo_album_key);
        dest.writeString(name);
        dest.writeInt(photo_count);
        dest.writeLong(created_at);
    }

    public String getPhoto_album_key() {
        return photo_album_key;
    }

    public void setPhoto_album_key(String photo_album_key) {
        this.photo_album_key = photo_album_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(int photo_count) {
        this.photo_count = photo_count;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public static final Creator<BandAlbumModel> CREATOR = new Creator<BandAlbumModel> () {
        @Override
        public BandAlbumModel createFromParcel(Parcel source) {
            final BandAlbumModel bandAlbumModel = new BandAlbumModel();
            bandAlbumModel.setName(source.readString());
            bandAlbumModel.setPhoto_album_key(source.readString());
            bandAlbumModel.setPhoto_count(source.readInt());
            bandAlbumModel.setCreated_at(source.readLong());
            return bandAlbumModel;
        }

        @Override
        public BandAlbumModel[] newArray(int i) {
            return new BandAlbumModel[0];
        }
    };
}
