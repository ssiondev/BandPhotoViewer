package com.bandphotoviewer.View.Adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bandphotoviewer.R;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.ViewModel.PhotoItemViewModel;
import com.bandphotoviewer.databinding.ItemPhotodetailBinding;
import com.bandphotoviewer.model.Page;
import com.bandphotoviewer.model.Pageable;
import com.bandphotoviewer.model.PageableResponse;
import com.bandphotoviewer.model.Photo;
import com.bandphotoviewer.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2018. 1. 24..
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<AbstractViewModel> viewModelList = new ArrayList<>();
    private RetrofitHelper retrofitHelper = new RetrofitHelper();

    private Page page;

    private String bandKey;
    private String albumKey;

    public ViewPagerAdapter(){

    }

    public ViewPagerAdapter(String bandKey, String albumKey) {
        this.bandKey = bandKey;
        this.albumKey = albumKey;
    }

    private List<AbstractViewModel> convertPhotoToPhotoViewModel(List<Photo> originalPhotoList) {
        List<AbstractViewModel> viewModelList = new ArrayList<>();
        for (Photo photo : originalPhotoList) {
            viewModelList.add(new PhotoItemViewModel(photo, null));
        }

        return viewModelList;
    }

    public void getPhotoList() {
        if (page != null && page.getNextParams() == null) {
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

        if (page == null) {
            clearItemList();
        }

        addItemList(convertPhotoToPhotoViewModel(bandResponse.getResultData().getItems()));
        notifyDataSetChanged();

        page = bandResponse.getResultData().getPage();
    }

    public void addItemList(List<AbstractViewModel> viewModelList) {
        this.viewModelList.addAll(viewModelList);
    }

    private void clearItemList() {
        this.viewModelList.clear();
    }

    @Override
    public int getCount() {
        return viewModelList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        ItemPhotodetailBinding itemPhotodetailBinding = DataBindingUtil
                .inflate(LayoutInflater.from(container.getContext()),
                        R.layout.item_photodetail, container, false);

        itemPhotodetailBinding.setViewModel((PhotoItemViewModel) viewModelList.get(position));
        container.addView(itemPhotodetailBinding.getRoot(), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        return itemPhotodetailBinding.getRoot();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

