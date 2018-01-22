package com.bandphotoviewer.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bandphotoviewer.Utils.Pref;

/**
 * Created by user on 2018. 1. 11..
 */

public class BandListModel extends AbstractModel implements Parcelable {

    private Pref pref = Pref.getInstance();

    private String name;   //밴드 이름
    private String band_key; // 밴드 식별자
    private String cover; // 밴드 커버 이미지 url
    private int member_count; // 밴드의 멤버 수

    public BandListModel(){}

    public BandListModel(Parcel in){
        readFromParcel(in);
    }

    public BandListModel(String name, String band_key, String cover, int member_count) {
        this.name = name;
        this.band_key = band_key;
        this.cover = cover;
        this.member_count = member_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void readFromParcel(Parcel in){
        name = in.readString();
        band_key = in.readString();
        cover = in.readString();
        member_count = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(name);
        dest.writeString(band_key);
        dest.writeString(cover);
        dest.writeInt(member_count);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBand_key() {
        return band_key;
    }

    public void setBand_key(String band_key) {
        this.band_key = band_key;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }


    public static final Creator<BandListModel> CREATOR = new Creator<BandListModel> () {
        @Override
        public BandListModel createFromParcel(Parcel source) {
            final BandListModel bandListModel = new BandListModel();
            bandListModel.setBand_key(source.readString());
            bandListModel.setCover(source.readString());
            bandListModel.setName(source.readString());
            bandListModel.setMember_count(source.readInt());
            return bandListModel;
        }

        @Override
        public BandListModel[] newArray(int size) {
            return new BandListModel[size];
        }
    };
}
