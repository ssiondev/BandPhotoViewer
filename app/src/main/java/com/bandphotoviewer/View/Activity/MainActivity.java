package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bandphotoviewer.Model.BandList;
import com.bandphotoviewer.Model.BandModel;
import com.bandphotoviewer.Model.BandResponse;
import com.bandphotoviewer.NetworkManager.RequestRetrofitFactory;
import com.bandphotoviewer.R;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.ViewModel.BandListViewModel;
import com.bandphotoviewer.ViewModel.RecyclerItemClickListener;
import com.bandphotoviewer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseToolbarBindingActivity<ActivityMainBinding> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tvTitleView;
    private RecyclerView bandListRecyclerview;
    private RecyclerItemAdapter recyclerItemAdapter;

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);

        setNavigationIconVisibility(TAG, View.GONE);

        disposable = requestRetrofitFactory
                .getBandList()
                .subscribe(bandListBandResponse -> updateItemList(bandListBandResponse)
                        , throwable -> throwable.printStackTrace());

        initView();
    }

    public void initView() {
        setToolbarTitle("Band.");
        bandListRecyclerview = getContentBinding().bandListRecyclerview;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        bandListRecyclerview.setHasFixedSize(true);
        bandListRecyclerview.setLayoutManager(gridLayoutManager);
        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext(), bandListClickListener);
        bandListRecyclerview.setAdapter(recyclerItemAdapter);
    }


    private void updateItemList(BandResponse<BandList> bandResponse) {
        if (bandResponse.getResultData() == null) {
            return;
        }

        List<AbstractViewModel> viewModelList = new ArrayList<>();
        for (BandModel bandModel : bandResponse.getResultData().getBands()) {
            viewModelList.add(new BandListViewModel(bandModel, bandListClickListener));
        }
        recyclerItemAdapter.setItemList(viewModelList);
        recyclerItemAdapter.notifyDataSetChanged();
    }

    RecyclerItemClickListener bandListClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Object object) {
            if (object instanceof BandModel) {
                Intent intent = new Intent(MainActivity.this, AlbumListBindingActivity.class);
                intent.putExtra("band_key", ((BandModel) object).getBand_key());
                intent.putExtra("name", ((BandModel) object).getName());
                startActivity(intent);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
