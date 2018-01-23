package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bandphotoviewer.R;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.ViewModel.AlbumListViewModel;
import com.bandphotoviewer.customview.RecyclerItemClickListener;
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

public class AlbumListBindingActivity extends BaseToolbarBindingActivity<ActivityAlbumBinding> {
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
        setNavigationIconVisibility(TAG, View.VISIBLE);

        pref.setContext(this);
        getIntentForCallRetrofit(getIntent());
    }

    public void getIntentForCallRetrofit(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        bandName = intent.getStringExtra("name");
        cover = intent.getStringExtra("cover");

        getAlbumList();
        initView();
    }

    public void initView() {
        setToolbarTitle("Album.");
        setCollapsingLayoutImage(cover, View.VISIBLE);
        albumListRecyclerView = getContentBinding().albumRecyclerview;

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        albumListRecyclerView.setHasFixedSize(true);
        albumListRecyclerView.addOnScrollListener(getViewScrollListener(linearLayoutManager));
        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext(), albumListClickListener);
        albumListRecyclerView.setAdapter(recyclerItemAdapter);
    }

    private void getAlbumList() {
        if(page != null && page.getNextParams() == null) {
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
            viewModelList.add(new AlbumListViewModel(album, albumListClickListener));
        }

        if(page == null) {
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

    RecyclerItemClickListener albumListClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Object object) {
            if (object instanceof Album) {
                Intent intent = new Intent(AlbumListBindingActivity.this, PhotoBindingActivity.class);

                intent.putExtra("band_key", bandKey);
                intent.putExtra("album_key", ((Album) object).getPhotoAlbumKey());
                intent.putExtra("album_name", ((Album) object).getName());

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
