package com.bandphotoviewer.NetworkManager;

import com.google.gson.JsonObject;
import com.bandphotoviewer.Model.AuthorizationInfo;
import com.bandphotoviewer.Model.PagableModel;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by ssion.dev
 */

public interface BandService {

    @GET("oauth2/token?grant_type=authorization_code")
    Single<AuthorizationInfo> getAuthToken(@Query("code") String code, @Header("Authorization") String authorization);

    @GET("v2.1/bands")
    Single<JsonObject> getBandList(@Query("access_token") String accessToken);

    @GET("v2/band/albums")
    Single<JsonObject> getBandAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey);

    @GET("v2/band/album/photos")
    Single<JsonObject> getBandPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey);

    @GET("v2/band/")
    Flowable<PagableModel> getNextPage(@QueryMap(encoded = true) Map<Object, Object> paging);

}
