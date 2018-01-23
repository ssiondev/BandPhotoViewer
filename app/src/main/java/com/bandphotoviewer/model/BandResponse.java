package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * result data 내부에 떨어지는 list
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
