package com.bandphotoviewer.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018. 1. 22..
 */

public class BandResponse<T> {

    @SerializedName("result_code")
    int resultCode;

    @SerializedName("result_data")
    T resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
