package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bandphotoviewer.Model.Page;
import com.bandphotoviewer.Model.PageableResponse;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.Model.AlbumList;
import com.bandphotoviewer.NetworkManager.RequestRetrofitFactory;
import com.bandphotoviewer.R;
import com.bandphotoviewer.Utils.Pref;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.RecyclerItemClickListener;
import com.bandphotoviewer.databinding.ActivityAlbumBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private RecyclerItemAdapter recyclerItemAdapter;
    private Page paging;

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
                requestRetrofitFactory.getAlbumList(bandKey, new HashMap<>())
                        .subscribe(pageableResponse -> {
                                    updateList(pageableResponse);
                                }
                                , throwable -> throwable.printStackTrace()));

        initView();
    }

    public void initView() {
        setToolbarTitle("Album.");
        albumListRecyclerView = getContentBinding().albumRecyclerview;


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        albumListRecyclerView.setHasFixedSize(true);
        albumListRecyclerView.setLayoutManager(gridLayoutManager);

        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext(), albumListClickListener);
        albumListRecyclerView.setAdapter(recyclerItemAdapter);
    }

    private void updateList(PageableResponse<List<AlbumList>> pageableResponse) {
        if (pageableResponse != null) {
            paging = pageableResponse.getResultData().getPage();
        }

        List<AbstractViewModel> viewModelList = new ArrayList<>();

        Log.e("nextParam", paging.getNextParams() != null ? paging.getNextParams().toString() : "");

        recyclerItemAdapter.setItemList(viewModelList);
        recyclerItemAdapter.notifyDataSetChanged();
    }

    RecyclerItemClickListener albumListClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Object object) {
            if (object instanceof AlbumList) {
                Intent intent = new Intent(AlbumListBindingActivity.this, PhotoBindingActivity.class);
                intent.putExtra("band_key", bandKey);
                intent.putExtra("album_key", ((AlbumList) object).getPhotoAlbumKey());
                intent.putExtra("album_name", ((AlbumList) object).getName());
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
