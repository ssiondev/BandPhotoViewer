package com.bandphotoviewer.NetworkManager;

import com.bandphotoviewer.Model.AlbumList;
import com.bandphotoviewer.Model.BandList;
import com.bandphotoviewer.Model.PhotoList;
import com.bandphotoviewer.Model.BandResponse;
import com.bandphotoviewer.Model.PageableResponse;
import com.bandphotoviewer.Model.AuthorizationInfo;

import java.util.List;
import java.util.Map;

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
    Single<BandResponse<BandList>> getBandList(@Query("access_token") String accessToken);

    @GET("v2/band/albums")
    Single<PageableResponse<List<AlbumList>>> getBandAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @QueryMap Map<String, String> nextParam);

    @GET("v2/band/album/photos")
    Single<PageableResponse<List<PhotoList>>> getBandPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey, @QueryMap Map<String, String> nextParam);

}
