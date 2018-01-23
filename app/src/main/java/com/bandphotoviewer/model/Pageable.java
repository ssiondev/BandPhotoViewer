package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018. 1. 23..
 */

public class Pageable<T> {
    @SerializedName("paging")
    Page page;
    T items;

    public Page getPage() {
        return page;
    }

    public T getItems() {
        return items;
    }
}
