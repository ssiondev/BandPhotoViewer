package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 *
 */

public class Page {

    @SerializedName("previous_params")
    Map<String, String> previousParams;

    @SerializedName("next_params")
    Map<String, String> nextParams;

    public Map<String, String> getPreviousParams() {
        return previousParams;
    }

    public Map<String, String> getNextParams() {
        return nextParams;
    }


}
