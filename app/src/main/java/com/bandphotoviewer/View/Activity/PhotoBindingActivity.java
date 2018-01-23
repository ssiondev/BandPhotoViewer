package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bandphotoviewer.ViewModel.AlbumListViewModel;
import com.bandphotoviewer.ViewModel.PhotoListViewModel;
import com.bandphotoviewer.customview.RecyclerViewScrollListener;
import com.bandphotoviewer.model.Album;
import com.bandphotoviewer.model.Page;
import com.bandphotoviewer.model.Pageable;
import com.bandphotoviewer.model.PageableResponse;
import com.bandphotoviewer.View.Adapter.ItemDecoratorViews;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.model.Photo;
import com.bandphotoviewer.network.RetrofitHelper;
import com.bandphotoviewer.R;
import com.bandphotoviewer.utils.Pref;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.customview.RecyclerItemClickListener;
import com.bandphotoviewer.databinding.ActivityPhotoBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class PhotoBindingActivity extends BaseToolbarBindingActivity<ActivityPhotoBinding> {
    private static final String TAG = PhotoBindingActivity.class.getSimpleName();

    private RetrofitHelper retrofitHelper = new RetrofitHelper();
    private Pref pref = Pref.getInstance();

    private RecyclerView recyclerView;
    private RecyclerItemAdapter recyclerItemAdapter;
    private MenuItem menuItem;

    private String bandKey;
    private String albumKey;
    private String albumName;

    private GridLayoutManager gridLayoutManager;
    private Disposable disposable;
    private Page page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_photo);
        setNavigationIconVisibility(TAG, View.VISIBLE);

        pref.setContext(this);
        getIntentForCallRetrofit(getIntent());
    }

    public void getIntentForCallRetrofit(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        albumKey = intent.getStringExtra("album_key");
        albumName = intent.getStringExtra("album_name");

        getPhotoList();
        initView();
    }


    private void getPhotoList(){
        if(page != null && page.getNextParams() == null) {
            return;
        }

        retrofitHelper.getCompositeDisposable()
                .add(retrofitHelper.getPhotoList(bandKey, albumKey, page == null ? new HashMap<>() : page.getNextParams())
                        .subscribe(pagingBandResponse -> {
                            updateList(pagingBandResponse);
                        }, throwable -> throwable.printStackTrace()));
    }

    private void updateList(PageableResponse<Pageable<List<Photo>>> bandResponse) {
        if (bandResponse == null
                || bandResponse.getResultData() == null
                || bandResponse.getResultData().getItems() == null) {
            return;
        }

        List<AbstractViewModel> viewModelList = new ArrayList<>();
        for (Photo photo : bandResponse.getResultData().getItems()) {
            viewModelList.add(new PhotoListViewModel(photo, photoListClickListener));
        }

        if(page == null) {
            recyclerItemAdapter.clearItemList();
        }

        recyclerItemAdapter.addItemList(viewModelList);
        recyclerItemAdapter.notifyDataSetChanged();

        page = bandResponse.getResultData().getPage();
    }

    public void initView() {
        setToolbarTitle("Photo");
        recyclerView = getContentBinding().photoRecyclerview;
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.addOnScrollListener(getViewScrollListener(gridLayoutManager));

        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext(), photoListClickListener);
        recyclerView.addItemDecoration(new ItemDecoratorViews(3, 6, true));
        recyclerView.setAdapter(recyclerItemAdapter);
    }

    RecyclerViewScrollListener getViewScrollListener(GridLayoutManager gridLayoutManager) {
        return new RecyclerViewScrollListener(gridLayoutManager, 5) {

            @Override
            public int getLastVisibleItem(int[] lastVisibleItemPositions) {
                return super.getLastVisibleItem(lastVisibleItemPositions);
            }

            @Override
            public void onLoadMore(int totalItemsCount, RecyclerView view) {
                getPhotoList();
            }
        };
    }

    RecyclerItemClickListener photoListClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Object o) {
            if (o instanceof Photo) {
                Intent intent = new Intent(PhotoBindingActivity.this, PhotoDetailBindingActivity.class);
                intent.putExtra("album_key", albumKey);
                intent.putExtra("slide_show", false);
                startActivity(intent);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        menuItem = menu.findItem(R.id.action_photo_slide);
        menuItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo_slide:
                Intent intent = new Intent(PhotoBindingActivity.this, PhotoDetailBindingActivity.class);
                intent.putExtra("album_key", albumKey);
                intent.putExtra("slide_show", true);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

