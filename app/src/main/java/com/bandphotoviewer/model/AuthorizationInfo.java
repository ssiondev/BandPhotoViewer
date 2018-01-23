package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017. 12. 20..
 */

public class AuthorizationInfo {

    @SerializedName("access_token")
    private String access_token;    //접근 권한이 있는 토큰

    @SerializedName("token_type")
    private String token_type;  // 토큰 타입

    @SerializedName("refresh_token")
    private String refresh_token;   //토큰 만료 시 발급되는 재사용 토큰

    @SerializedName("expires_in")
    private int expires_in;    // 토큰 유효기간

    @SerializedName("user_key")
    private String user_key;    // 유저 식별키

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }
}
