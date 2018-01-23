package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bandphotoviewer.customview.BindListViewType;
import com.bumptech.glide.Glide;
import com.bandphotoviewer.model.Photo;

/**
 * Created by user on 2018. 1. 16..
 */

public class PhotoDetailViewModel extends AbstractViewModel {

    private Photo photo;

    public PhotoDetailViewModel(Photo photo) {
        super(photo);
        this.photo = photo;
    }

    @Override
    public BindListViewType getViewType() {
        return null;
    }

    public String getUrl() {return photo.getUrl();}

    public String getPhotoKey() {return photo.getPhotoKey();}

    public int getHeight(){
        return photo.getHeight();
    }

    public int getWidth(){
        return photo.getWidth();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setPhotoUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

}
