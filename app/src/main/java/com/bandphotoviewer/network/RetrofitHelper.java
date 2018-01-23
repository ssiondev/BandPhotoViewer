package com.bandphotoviewer.network;

import com.bandphotoviewer.model.Album;
import com.bandphotoviewer.model.BandList;
import com.bandphotoviewer.model.Pageable;
import com.bandphotoviewer.model.Photo;
import com.bandphotoviewer.model.BandResponse;
import com.bandphotoviewer.model.PageableResponse;
import com.bandphotoviewer.model.AuthorizationInfo;
import com.bandphotoviewer.utils.Pref;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by user on 2018. 1. 10..
 */

public class RetrofitHelper {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private BandService tokenService = RetrofitFactory.getInstance().getRetrofitForAuthToken();
    private BandService bandService = RetrofitFactory.getInstance().getRetrofitForBandService();

    private Pref pref = Pref.getInstance();

    public CompositeDisposable getCompositeDisposable(){
        return mCompositeDisposable;
    }

    public Single<AuthorizationInfo> requestForAuthToken(String received_authorization_code) {
        return tokenService.getAuthToken(received_authorization_code,
                        RetrofitFactory.getInstance().getBase64Encode())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<BandResponse<BandList>> getBandList() {
        return bandService.getBandList(getAccessTokenFromPref())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<PageableResponse<Pageable<List<Album>>>> getAlbumListTest(String bandKey, Map<String, String> nextParam) {
        return bandService.getBandAlbums(getAccessTokenFromPref(), bandKey, nextParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<PageableResponse<Pageable<List<Photo>>>> getPhotoList(String bandKey, String albumKey, Map<String, String> nextParam) {
        return bandService.getBandPhotos(getAccessTokenFromPref(), bandKey, albumKey, nextParam)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

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
