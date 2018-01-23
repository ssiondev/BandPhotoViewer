package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bandphotoviewer.Model.PhotoList;

/**
 * Created by user on 2018. 1. 16..
 */

public class PhotoDetailViewModel extends AbstractViewModel {

    private PhotoList photoList;

    public PhotoDetailViewModel(PhotoList photoList) {
        super(photoList);
        this.photoList = photoList;
    }

    @Override
    public BindListViewType getViewType() {
        return null;
    }

    public String getUrl() {return photoList.getUrl();}

    public String getPhotoKey() {return photoList.getPhotoKey();}

    public int getHeight(){
        return photoList.getHeight();
    }

    public int getWidth(){
        return photoList.getWidth();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setPhotoUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

}
