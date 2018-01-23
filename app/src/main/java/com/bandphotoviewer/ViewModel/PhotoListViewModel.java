package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bandphotoviewer.customview.BindListViewType;
import com.bandphotoviewer.customview.RecyclerItemClickListener;
import com.bumptech.glide.Glide;
import com.bandphotoviewer.model.Photo;

/**
 * Created by user on 2017. 12. 20..
 */

public class PhotoListViewModel extends AbstractViewModel {

    private Photo photo;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.PHOTOLIST;
    }

    public PhotoListViewModel(Photo photo, RecyclerItemClickListener recyclerItemClickListener) {
        super(photo, recyclerItemClickListener);
        this.photo = photo;
    }



    public void onItemClick() {
        recyclerItemClickListener.onItemClick(photo);
    }

    public String getUrl() {
        return photo.getUrl();
    }

    public String getPhotoKey() {
        return photo.getPhotoKey();
    }

    public int getHeight() {
        return photo.getHeight();
    }

    public int getWidth() {
        return photo.getWidth();
    }

    public long getCreated() {
        return photo.getCreated_at();
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
