package com.bandphotoviewer.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017. 12. 20..
 */

public class AuthorizationInfo implements Parcelable {

    private String access_token;    //접근 권한이 있는 토큰
    private String token_type;  // 토큰 타입
    private String refresh_token;   //토큰 만료 시 발급되는 재사용 토큰
    private int expires_in;    // 토큰 유효기간
    private String user_key;    // 유저 식별키


    public AuthorizationInfo(){}

    public AuthorizationInfo(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AuthorizationInfo(String access_token, String token_type, String refresh_token, int expires_in, String user_key) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.user_key = user_key;
    }

    public void writeToParcel(Parcel dest, int flag){
        dest.writeString(access_token);
        dest.writeString(token_type);
        dest.writeString(refresh_token);
        dest.writeInt(expires_in);
        dest.writeString(user_key);
    }

    public void readFromParcel(Parcel in){
        access_token = in.readString();
        token_type = in.readString();
        refresh_token = in.readString();
        expires_in = in.readInt();
        user_key = in.readString();
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getUser_key() {
        return user_key;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }


    public static final Creator CREATOR = new Creator() {
        @Override
        public AuthorizationInfo createFromParcel(Parcel parcel) {
            return new AuthorizationInfo();
        }

        @Override
        public AuthorizationInfo[] newArray(int i) {
            return new AuthorizationInfo[0];
        }
    };
}
