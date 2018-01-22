package com.bandphotoviewer.NetworkManager;

import com.google.gson.JsonObject;
import com.bandphotoviewer.Model.AuthorizationInfo;
import com.bandphotoviewer.Utils.Pref;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by user on 2018. 1. 10..
 */

public class RequestRetrofitFactory {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private BandService tokenService = ApiFactory.getInstance().getRetrofitForAuthToken();
    private BandService bandService = ApiFactory.getInstance().getRetrofitForBandService();

    private Pref pref = Pref.getInstance();

    public CompositeDisposable getCompositeDisposable(){
        return mCompositeDisposable;
    }

    public Single<AuthorizationInfo> requestForAuthToken(String received_authorization_code) {
        return tokenService.getAuthToken(received_authorization_code,
                        ApiFactory.getInstance().getBase64Encode())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<JsonObject> getBandList() {
        return bandService.getBandList(getAccessTokenFromPref())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<JsonObject> getAlbumList(String bandKey) {
        return bandService.getBandAlbums(getAccessTokenFromPref(), bandKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<JsonObject> getPhotoList(String bandKey, String albumKey) {
        return bandService.getBandPhotos(getAccessTokenFromPref(), bandKey, albumKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

//
//    public Flowable<PagableModel> getNextPages(String after){
//        return bandService.getNextPage()
//    }

    public void saveJsonToPref(Object modelObject) {
        if (modelObject != null) {
            if (modelObject instanceof AuthorizationInfo) {
                pref.putJson(Pref.ACCESS_TOKEN_KEY, modelObject);
            }
        }
    }

    public String getAccessTokenFromPref() {
        AuthorizationInfo authorizationInfo = pref.getObject(Pref.ACCESS_TOKEN_KEY, null, AuthorizationInfo.class);
        return authorizationInfo.getAccess_token();
    }

}
