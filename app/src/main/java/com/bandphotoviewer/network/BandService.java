package com.bandphotoviewer.network;

import com.bandphotoviewer.model.Album;
import com.bandphotoviewer.model.AuthorizationInfo;
import com.bandphotoviewer.model.BandList;
import com.bandphotoviewer.model.BandResponse;
import com.bandphotoviewer.model.Pageable;
import com.bandphotoviewer.model.PageableResponse;
import com.bandphotoviewer.model.Photo;

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
    Single<PageableResponse<Pageable<List<Album>>>> getBandAlbums(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @QueryMap Map<String, String> nextParam);

    @GET("v2/band/album/photos")
    Single<PageableResponse<Pageable<List<Photo>>>> getBandPhotos(@Query("access_token") String accessToken, @Query("band_key") String bandKey, @Query("photo_album_key") String photoAlbumKey, @QueryMap Map<String, String> nextParam);

}
