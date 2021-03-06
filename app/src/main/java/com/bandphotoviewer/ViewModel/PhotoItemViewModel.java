package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bandphotoviewer.customview.BindListViewType;
import com.bandphotoviewer.model.Photo;
import com.bumptech.glide.Glide;

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

    public void onItemClick(int position) {
        if (navigator != null) {
            navigator.onClickPhoto(position);
        }
    }

    @BindingAdapter("bind:imageUrl")
    public static void setPhotoUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    public interface Navigator {
        void onClickPhoto(int position);
    }

}
