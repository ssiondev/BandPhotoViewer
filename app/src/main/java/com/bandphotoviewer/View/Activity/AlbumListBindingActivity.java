package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bandphotoviewer.R;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.ViewModel.AlbumListViewModel;
import com.bandphotoviewer.customview.RecyclerViewScrollListener;
import com.bandphotoviewer.databinding.ActivityAlbumBinding;
import com.bandphotoviewer.model.Album;
import com.bandphotoviewer.model.Page;
import com.bandphotoviewer.model.Pageable;
import com.bandphotoviewer.model.PageableResponse;
import com.bandphotoviewer.network.RetrofitHelper;
import com.bandphotoviewer.utils.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2017. 12. 20..
 */

public class AlbumListBindingActivity extends BaseToolbarBindingActivity<ActivityAlbumBinding> implements AlbumListViewModel.Navigator {
    private static final String TAG = AlbumListBindingActivity.class.getSimpleName();
    private Pref pref = Pref.getInstance();
    private RetrofitHelper retrofitHelper = new RetrofitHelper();
    private RecyclerView albumListRecyclerView;

    private String bandKey;
    private String bandName;
    private String cover;

    private RecyclerItemAdapter recyclerItemAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Page page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_album);
        setNavigationIconVisibility(View.VISIBLE);

        pref.setContext(this);
        getIntentForCallRetrofit();
    }

    public void getIntentForCallRetrofit() {
        bandKey = getIntent().getStringExtra("band_key");
        bandName = getIntent().getStringExtra("name");
        cover = getIntent().getStringExtra("cover");

        getAlbumList();
        initView();
    }

    public void initView() {
        setToolbarTitle("Album.");
        albumListRecyclerView = getContentBinding().albumRecyclerview;

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        albumListRecyclerView.setHasFixedSize(true);

        albumListRecyclerView.addOnScrollListener(getViewScrollListener(linearLayoutManager));
        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext());
        albumListRecyclerView.setAdapter(recyclerItemAdapter);
    }

    private void getAlbumList() {
        if (page != null && page.getNextParams() == null) {
            return;
        }

        retrofitHelper.getCompositeDisposable()
                .add(retrofitHelper.getAlbumListTest(bandKey, page == null ? new HashMap<>() : page.getNextParams())
                        .subscribe(pagingBandResponse -> {
                            updateList(pagingBandResponse);
                        }, throwable -> throwable.printStackTrace()));
    }


    private void updateList(PageableResponse<Pageable<List<Album>>> bandResponse) {
        if (bandResponse == null
                || bandResponse.getResultData() == null
                || bandResponse.getResultData().getItems() == null) {
            return;
        }

        List<AbstractViewModel> viewModelList = new ArrayList<>();
        for (Album album : bandResponse.getResultData().getItems()) {
            viewModelList.add(new AlbumListViewModel(album, this));
        }

        if (page == null) {
            recyclerItemAdapter.clearItemList();
        }

        recyclerItemAdapter.addItemList(viewModelList);
        recyclerItemAdapter.notifyDataSetChanged();

        page = bandResponse.getResultData().getPage();
    }

    RecyclerViewScrollListener getViewScrollListener(LinearLayoutManager linearLayoutManager) {
        return new RecyclerViewScrollListener(linearLayoutManager, 5) {

            @Override
            public int getLastVisibleItem(int[] lastVisibleItemPositions) {
                return super.getLastVisibleItem(lastVisibleItemPositions);
            }

            @Override
            public void onLoadMore(int totalItemsCount, RecyclerView view) {
                getAlbumList();
            }
        };
    }

    @Override
    public void onClickAlbum(Album album) {
        goToPhotoActivity(album);
    }

    private void goToPhotoActivity(Album album){
        Intent intent = new Intent(AlbumListBindingActivity.this, PhotoBindingActivity.class);

        intent.putExtra("band_key", bandKey);
        intent.putExtra("album_key", album.getPhotoAlbumKey());

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
