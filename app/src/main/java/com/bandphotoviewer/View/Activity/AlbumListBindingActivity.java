package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.bandphotoviewer.Model.BandAlbumModel;
import com.bandphotoviewer.NetworkManager.RequestRetrofitFactory;
import com.bandphotoviewer.R;
import com.bandphotoviewer.Utils.Pref;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.RecyclerItemClickListener;
import com.bandphotoviewer.databinding.ActivityAlbumBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by user on 2017. 12. 20..
 */

public class AlbumListBindingActivity extends BaseToolbarBindingActivity<ActivityAlbumBinding> {
    private static final String TAG = AlbumListBindingActivity.class.getSimpleName();
    private Pref pref = Pref.getInstance();
    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private RecyclerView albumListRecyclerView;

    private String bandKey;
    private String bandName;

    private TextView tvAlbumListTitle;
    private TextView tvBandName;

    private RecyclerItemAdapter recyclerItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_album);
        setNavigationIconVisibility(TAG, View.VISIBLE);

        pref.setContext(this);
        getIntentForCallRetrofit(getIntent());
    }

    public void getIntentForCallRetrofit(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        bandName = intent.getStringExtra("name");

        requestRetrofitFactory.getCompositeDisposable().add(
                requestRetrofitFactory.getAlbumList(bandKey)
                        .subscribe(consumer));
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            int result = jsonObject.get("result_code").getAsInt();
            if (result == 1) {
                JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                        .get("items").getAsJsonArray();

                pref.putString(Pref.BAND_ALBUM_KEY + bandKey, jsonArray.toString());
                initView();
            }
        }
    };

    public List<BandAlbumModel> convertToAlbumList() {
        String json = pref.getString(Pref.BAND_ALBUM_KEY + bandKey, null);
        Type listType = new TypeToken<ArrayList<BandAlbumModel>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<BandAlbumModel> list = gson.fromJson(json, listType);
        return list;
    }

    public void initView() {
        albumListRecyclerView = getContentBinding().albumRecyclerview;
        tvAlbumListTitle = getContentBinding().albumListTitle;
        tvBandName = getContentBinding().bandName;

        tvAlbumListTitle.setText(R.string.main_album_title);
        tvBandName.setText(bandName);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        albumListRecyclerView.setHasFixedSize(true);
        albumListRecyclerView.setLayoutManager(gridLayoutManager);

        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext(), albumListClickListener);
        albumListRecyclerView.setAdapter(recyclerItemAdapter);
        recyclerItemAdapter.setAlbumItemList(convertToAlbumList());
    }


    RecyclerItemClickListener albumListClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Object object) {
            if (object instanceof BandAlbumModel) {
                Intent intent = new Intent(AlbumListBindingActivity.this, PhotoBindingActivity.class);
                intent.putExtra("band_key", bandKey);
                intent.putExtra("album_key", ((BandAlbumModel) object).getPhoto_album_key());
                intent.putExtra("album_name", ((BandAlbumModel) object).getName());
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
