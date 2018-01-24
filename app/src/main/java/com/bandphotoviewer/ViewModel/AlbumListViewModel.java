package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.bandphotoviewer.customview.BindListViewType;
import com.bandphotoviewer.model.Album;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017. 12. 20..
 */

public class AlbumListViewModel extends AbstractViewModel {

    private final Album album;
    private final Navigator navigator;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.ALBUMLIST;
    }

    public AlbumListViewModel(Album album, Navigator navigator) {
        super(album);
        this.album = album;
        this.navigator = navigator;
    }

    public void onItemClick() {
        if(navigator !=  null){
            navigator.onClickAlbum(album);
        }
    }

    public String getAlbumKey() {
        return album.getPhotoAlbumKey();
    }

    public String getAlbumName() {
        return album.getName();
    }

    public long getCreatedat() {
        return album.getCreatedAt();
    }

    public int getPhotoCount() {
        return album.getPhotoCount();
    }

    @BindingAdapter("bind:photoCount")
    public static void setPhotoCount(TextView text, int count) {
        text.setText(Integer.toString(count));
    }

    @BindingAdapter("bind:dateCount")
    public static void getDate(TextView textView, long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat showDate = new SimpleDateFormat("yyyy년 MM월");
        textView.setText(showDate.format(date));
    }

    public interface Navigator {
        void onClickAlbum(Album album);
    }
}
