package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018. 1. 22..
 */

public class PageableResponse<T> {

    @SerializedName("result_code")
    int reslutCode;

    @SerializedName("result_data")
    T resultData;

    public int getResultCode() {
        return reslutCode;
    }

    public void setReslutCode(int reslutCode) {
        this.reslutCode = reslutCode;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
