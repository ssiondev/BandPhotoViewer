package com.bandphotoviewer.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 2018. 1. 11..
 */

public class BandListManager {


    private static Integer RESULT_CODE = 1;
    private static String PREF_BAND_LIST_RESULT = "BandList";

    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public BandListManager(Context context) {
        this.mContext = context;
    }

    public void setResultCode(Integer resultCode){
        editor.putInt(PREF_BAND_LIST_RESULT, resultCode);
        editor.apply();
    }

    public Integer getResultCode() {
        return RESULT_CODE;
    }

    public void setBandName(String bandName) {
        pref = mContext.getSharedPreferences(PREF_BAND_LIST_RESULT, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_BAND_LIST_RESULT, bandName);
        editor.apply();
    }

    public String getBandName() {
        return PREF_BAND_LIST_RESULT;
    }

    public void setBandKey(String bandKey) {
        pref = mContext.getSharedPreferences(PREF_BAND_LIST_RESULT, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_BAND_LIST_RESULT, bandKey);
        editor.apply();
    }

    public String getBandKey() {
        return PREF_BAND_LIST_RESULT;
    }

    public void setBandCoverURL(String coverURL) {
        pref = mContext.getSharedPreferences(PREF_BAND_LIST_RESULT, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_BAND_LIST_RESULT, coverURL);
        editor.apply();
    }

    public String getBandCoverURL() {
        return PREF_BAND_LIST_RESULT;
    }

    public void setMemberCount(String memberCount){
        pref = mContext.getSharedPreferences(PREF_BAND_LIST_RESULT, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(PREF_BAND_LIST_RESULT, memberCount);
        editor.apply();
    }

    public String getMemberCount() {
        return PREF_BAND_LIST_RESULT;
    }
}
