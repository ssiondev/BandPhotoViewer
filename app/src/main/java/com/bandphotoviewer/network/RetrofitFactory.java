package com.bandphotoviewer.network;

import android.util.Base64;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.bandphotoviewer.utils.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ssion.dev
 */

public class RetrofitFactory {
    private static RetrofitFactory instance = new RetrofitFactory();

    public static RetrofitFactory getInstance() {
        return instance;
    }

    private Retrofit createRetrofitForToken() {
        OkHttpClient okHttpClient = createOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.AUTH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Retrofit createRetrofitForBandService() {
        OkHttpClient okHttpClient = createOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

    public BandService getRetrofitForAuthToken() {
        Retrofit retrofit = createRetrofitForToken();
        return retrofit.create(BandService.class);
    }

    public BandService getRetrofitForBandService() {
        Retrofit retrofit = createRetrofitForBandService();
        return retrofit.create(BandService.class);
    }

    public String getBase64Encode() {
        String combine_for_encode = Constant.CLIENT_ID + ":" + Constant.CLIENT_SECRET;
        return "Basic " + Base64.encodeToString(combine_for_encode.getBytes(), Base64.URL_SAFE).trim();
    }

}