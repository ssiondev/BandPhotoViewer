package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bandphotoviewer.Model.PhotoList;

/**
 * Created by user on 2017. 12. 20..
 */

public class PhotoListViewModel extends AbstractViewModel {

    private PhotoList photoList;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.PHOTOLIST;
    }

    public PhotoListViewModel(PhotoList photoList, RecyclerItemClickListener recyclerItemClickListener) {
        super(photoList, recyclerItemClickListener);
        this.photoList = photoList;
    }



    public void onItemClick() {
        recyclerItemClickListener.onItemClick(photoList);
    }

    public String getUrl() {
        return photoList.getUrl();
    }

    public String getPhotoKey() {
        return photoList.getPhotoKey();
    }

    public int getHeight() {
        return photoList.getHeight();
    }

    public int getWidth() {
        return photoList.getWidth();
    }

    public long getCreated() {
        return photoList.getCreated_at();
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
