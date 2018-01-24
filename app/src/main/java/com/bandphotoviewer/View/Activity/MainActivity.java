package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bandphotoviewer.model.Band;
import com.bandphotoviewer.model.BandList;
import com.bandphotoviewer.model.BandResponse;
import com.bandphotoviewer.network.RetrofitHelper;
import com.bandphotoviewer.R;
import com.bandphotoviewer.View.Adapter.ItemDecoratorViews;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.ViewModel.BandListViewModel;
import com.bandphotoviewer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseToolbarBindingActivity<ActivityMainBinding> implements BandListViewModel.Navigator {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView bandListRecyclerview;
    private RecyclerItemAdapter recyclerItemAdapter;

    private RetrofitHelper retrofitHelper = new RetrofitHelper();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);
        setNavigationIconVisibility(TAG, View.GONE);

        disposable = retrofitHelper
                .getBandList()
                .subscribe(bandListBandResponse -> updateItemList(bandListBandResponse)
                        , throwable -> throwable.printStackTrace());

        initView();
    }

    public void initView() {
        setToolbarTitle("Band.");
        bandListRecyclerview = getContentBinding().bandListRecyclerview;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        bandListRecyclerview.setLayoutManager(gridLayoutManager);
        bandListRecyclerview.setHasFixedSize(true);

        bandListRecyclerview.addItemDecoration(new ItemDecoratorViews(2, 4, true));
        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext());
        bandListRecyclerview.setAdapter(recyclerItemAdapter);
    }


    private void updateItemList(BandResponse<BandList> bandResponse) {
        if (bandResponse.getResultData() == null) {
            return;
        }

        List<AbstractViewModel> viewModelList = new ArrayList<>();
        for (Band band : bandResponse.getResultData().getBands()) {
            viewModelList.add(new BandListViewModel(band, this));
        }
        recyclerItemAdapter.setItemList(viewModelList);
        recyclerItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickBandList(Band band) {
        goToAlbumActivity(band);
    }

    private void goToAlbumActivity(Band band) {
        Intent intent = new Intent(MainActivity.this, AlbumListBindingActivity.class);
        intent.putExtra("band_key", band.getBand_key());
        intent.putExtra("name", band.getName());
        intent.putExtra("cover", band.getCover());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
