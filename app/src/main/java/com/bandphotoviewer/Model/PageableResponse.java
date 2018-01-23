package com.bandphotoviewer.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018. 1. 22..
 */

public class PageableResponse<T> {

    @SerializedName("result_code")
    int reslutCode;

    @SerializedName("result_data")
    Paging resultData;

    @SerializedName("items")
    T items;

    public int getReslutCode() {
        return reslutCode;
    }

    public void setReslutCode(int reslutCode) {
        this.reslutCode = reslutCode;
    }

    public Paging getResultData() {
        return resultData;
    }

    public void setResultData(Paging resultData) {
        this.resultData = resultData;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }
}
