package com.bandphotoviewer.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public class Page{

    @SerializedName("previous_params")
    HashMap<String, String> previousParams;

    @SerializedName("next_params")
    HashMap<String, String> nextParams;

    public HashMap<String, String> getPreviousParams() {
        return previousParams;
    }

    public HashMap<String, String> getNextParams() {
        return nextParams;
    }

    public void setNextParams(HashMap<String, String> nextParams) {
        this.nextParams = nextParams;
    }
}
