package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bandphotoviewer.customview.BindListViewType;
import com.bumptech.glide.Glide;
import com.bandphotoviewer.model.Photo;

/**
 * Created by user on 2017. 12. 20..
 */

public class PhotoItemViewModel extends AbstractViewModel {

    private final Photo photo;
    private final Navigator navigator;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.PHOTOLIST;
    }

    public PhotoItemViewModel(Photo photo, Navigator navigator) {
        super(photo);
        this.photo = photo;
        this.navigator = navigator;
    }

    public void onItemClick(int position) {
        if(navigator != null) {
            navigator.onClickPhoto(position);
        }
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

    public interface Navigator {
        void onClickPhoto(int position);
    }

}
