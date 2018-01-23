package com.bandphotoviewer.model;

/**
 * Created by user on 2018. 1. 11..
 */

public class Band {

    private String name;   //밴드 이름

    private String band_key; // 밴드 식별자

    private String cover; // 밴드 커버 이미지 url

    private int member_count; // 밴드의 멤버 수

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


}
