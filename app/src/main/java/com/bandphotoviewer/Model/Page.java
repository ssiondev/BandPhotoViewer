package com.bandphotoviewer.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by user on 2018. 1. 18..
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
