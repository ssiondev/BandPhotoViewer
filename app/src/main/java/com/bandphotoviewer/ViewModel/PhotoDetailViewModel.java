package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bandphotoviewer.Model.BandPhotoModel;

/**
 * Created by user on 2018. 1. 16..
 */

public class PhotoDetailViewModel extends AbstractViewModel {

    private BandPhotoModel bandPhotoModel;

    public PhotoDetailViewModel(BandPhotoModel bandPhotoModel) {
        super();
        this.bandPhotoModel = bandPhotoModel;
    }

    @Override
    public BindListViewType getViewType() {
        return null;
    }

    public String getUrl() {return bandPhotoModel.getUrl();}

    public String getPhotoKey() {return bandPhotoModel.getPhoto_key();}

    public int getHeight(){
        return bandPhotoModel.getHeight();
    }

    public int getWidth(){
        return bandPhotoModel.getWidth();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setPhotoUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

}
