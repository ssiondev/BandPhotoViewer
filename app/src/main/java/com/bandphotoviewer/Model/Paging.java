package com.bandphotoviewer.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018. 1. 22..
 */

public class Paging {
    @SerializedName("paging")
    Page page;

    public Page getPage() {
        return page;
    }
}
