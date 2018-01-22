package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.bandphotoviewer.Model.BandListModel;
import com.bandphotoviewer.NetworkManager.RequestRetrofitFactory;
import com.bandphotoviewer.R;
import com.bandphotoviewer.Utils.Pref;
import com.bandphotoviewer.View.Adapter.RecyclerItemAdapter;
import com.bandphotoviewer.ViewModel.RecyclerItemClickListener;
import com.bandphotoviewer.databinding.ActivityMainBinding;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseToolbarBindingActivity<ActivityMainBinding> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tvTitleView;
    private RecyclerView bandListRecyclerview;
    private RecyclerItemAdapter recyclerItemAdapter;

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);
        setNavigationIconVisibility(TAG, View.GONE);

        pref.setContext(this);
        requestRetrofitFactory.getCompositeDisposable().add(
                requestRetrofitFactory.getBandList().subscribe(consumer));
    }

    Consumer<JsonObject> consumer = new Consumer<JsonObject>() {
        @Override
        public void accept(JsonObject jsonObject) throws Exception {
            int result = jsonObject.get("result_code").getAsInt();
            if (result == 1) {
                JsonArray jsonArray = jsonObject.get("result_data").getAsJsonObject()
                        .get("bands").getAsJsonArray();
                pref.putString(Pref.BAND_LIST_KEY, jsonArray.toString());
                initView();
            }
        }
    };


    public void initView() {
        tvTitleView = getContentBinding().mainTitle;
        tvTitleView.setText(R.string.main_toolbar_name);

        bandListRecyclerview = getContentBinding().bandListRecyclerview;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        bandListRecyclerview.setHasFixedSize(true);
        bandListRecyclerview.setLayoutManager(gridLayoutManager);

        recyclerItemAdapter = new RecyclerItemAdapter(getApplicationContext(), bandListClickListener);
        bandListRecyclerview.setAdapter(recyclerItemAdapter);
        recyclerItemAdapter.setBandItemList(recyclerItemAdapter.convertToBandList());
    }

    RecyclerItemClickListener bandListClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Object object) {
            if (object instanceof BandListModel) {
                Intent intent = new Intent(MainActivity.this, AlbumListBindingActivity.class);
                intent.putExtra("band_key", ((BandListModel) object).getBand_key());
                intent.putExtra("name", ((BandListModel) object).getName());
                startActivity(intent);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
