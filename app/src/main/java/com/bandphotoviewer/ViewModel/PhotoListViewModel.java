package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bandphotoviewer.Model.BandPhotoModel;

/**
 * Created by user on 2017. 12. 20..
 */

public class PhotoListViewModel extends AbstractViewModel {

    private BandPhotoModel bandPhotoModel;

    public PhotoListViewModel(BandPhotoModel bandPhotoModel, RecyclerItemClickListener recyclerItemClickListener) {
        super(recyclerItemClickListener);
        this.bandPhotoModel = bandPhotoModel;
    }

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.PHOTOLIST;
    }

    public void onItemClick() {
        recyclerItemClickListener.onItemClick(bandPhotoModel);
    }

    public String getUrl() {
        return bandPhotoModel.getUrl();
    }

    public String getPhotoKey() {
        return bandPhotoModel.getPhoto_key();
    }

    public int getHeight() {
        return bandPhotoModel.getHeight();
    }

    public int getWidth() {
        return bandPhotoModel.getWidth();
    }

    public long getCreated() {
        return bandPhotoModel.getCreated_at();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setPhotoUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    @BindingAdapter("bind:startSlideOption")
    public static void startSlideOption(ImageView photoView, String url) {
        Glide.with(photoView.getContext()).load(url).into(photoView);
    }


}
