package com.bandphotoviewer.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;


/**
 * Created by user on 2018. 1. 6..
 */

public class Pref {
    public static final String PREF_BANDPHOTOVIEWER = "BandPhotoViewer";
    public static final String ACCESS_TOKEN_KEY = "access_token_key";

    public static final String BAND_LIST_KEY = "bands";
    public static final String BAND_ALBUM_KEY = "albums";
    public static final String BAND_PHOTO_KEY = "photos";

    private static Pref pref;
    private SharedPreferences sharedPreferences;

    private Pref(){}

    public static Pref getInstance() {
        if(pref == null){
            pref = new Pref();
        }
        return pref;
    }

    private void setSharedPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_BANDPHOTOVIEWER, Context.MODE_PRIVATE);
    }

    public void setContext(Context context){
        if(sharedPreferences == null){
            setSharedPreferences(context.getApplicationContext());
        }
    }

    public void putJson(String key, Object o){
        Gson gson = new Gson();
        String json = gson.toJson(o);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("test", String.format("key: %s json: %s", key, json));
        editor.putString(key, json);
        editor.apply();
    }

    public <T> T getObject(String key, String dft, Class<T> klass){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, dft);
        if(json == null){
          return null;
        }
        return gson.fromJson(json, klass);
    }

//    public List<? extends AbstractModel> convertToPhotoList(String key) {
//        String json = pref.getString(key, null);
//        Type listType = new TypeToken<ArrayList<? extends AbstractModel>>() {
//        }.getType();
//        Gson gson = new Gson();
//        ArrayList<? extends AbstractModel> list = gson.fromJson(json, listType);
//        return list;
//    }

    public void putString(String key, String val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,val);
        editor.apply();
    }

    public void putInteger(String key, Integer val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,val);
        editor.apply();
    }

    public void putBoolean(String key, Boolean val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,val);
        editor.apply();
    }

    public String getString(String key, String dfv){
        return sharedPreferences.getString(key, dfv);
    }

    public int getInt(String key, int dfv){
        return sharedPreferences.getInt(key, 0);
    }

    public boolean getInt(String key, boolean dfv){
        return sharedPreferences.getBoolean(key, dfv);
    }
}
