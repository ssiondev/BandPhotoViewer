package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bandphotoviewer.Model.Page;
import com.bandphotoviewer.Model.PageableResponse;
import com.bandphotoviewer.View.Adapter.ItemDecoratorViews;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bandphotoviewer.Model.PhotoList;
import com.bandphotoviewer.NetworkManager.RequestRetrofitFactory;
import com.bandphotoviewer.R;
import com.bandphotoviewer.Utils.Pref;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.RecyclerItemClickListener;
import com.bandphotoviewer.databinding.ActivityPhotoBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class PhotoBindingActivity extends BaseToolbarBindingActivity<ActivityPhotoBinding> {
    private static final String TAG = PhotoBindingActivity.class.getSimpleName();

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

    private RecyclerView recyclerView;
    private RecyclerItemAdapter recyclerItemAdapter;
    private MenuItem menuItem;

    private String bandKey;
    private String albumKey;
    private String albumName;

    private Page paging;
    private Disposable disposable;

    private int spanCount = 3;
    private int spacing = 2;
    private boolean includeEdge = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_photo);
        setNavigationIconVisibility(TAG, View.VISIBLE);

        pref.setContext(this);
        getIntentForCallRetrofit(getIntent());

        disposable = requestRetrofitFactory.getPhotoList(bandKey, albumKey, new HashMap<>())
                .subscribe(listPageableResponse -> {
                    updateList(listPageableResponse);
                }, throwable -> throwable.printStackTrace());

        initView();
    }

    public void getIntentForCallRetrofit(Intent intent) {
        bandKey = intent.getStringExtra("band_key");
        albumKey = intent.getStringExtra("album_key");
        albumName = intent.getStringExtra("album_name");
    }


    private void updateList(PageableResponse<List<PhotoList>> pageableResponse) {
        if (pageableResponse != null) {
            paging = pageableResponse.getResultData().getPage();
        }

        List<AbstractViewModel> viewModelList = new ArrayList<>();

        Log.e("nextParam", paging.getNextParams() != null ? paging.getNextParams().toString() : "");

        recyclerItemAdapter.setItemList(viewModelList);
        recyclerItemAdapter.notifyDataSetChanged();
    }

    public void initView() {
        setToolbarTitle("Photo");
        recyclerView = getContentBinding().photoRecyclerview;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new ItemDecoratorViews(spanCount, spacing, includeEdge));

        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext(), photoListClickListener);
        recyclerView.setAdapter(recyclerItemAdapter);
    }

    RecyclerItemClickListener photoListClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Object o) {
            if (o instanceof PhotoList) {
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

    public List<PhotoList> convertToPhotoList() {
        String json = pref.getString(Pref.BAND_PHOTO_KEY + albumKey, null);
        Type listType = new TypeToken<ArrayList<PhotoList>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<PhotoList> list = gson.fromJson(json, listType);
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

