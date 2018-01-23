package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.bandphotoviewer.Model.AlbumList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017. 12. 20..
 */

public class AlbumListViewModel extends AbstractViewModel {

    private AlbumList albumList;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.ALBUMLIST;
    }

    public AlbumListViewModel(AlbumList albumList, RecyclerItemClickListener recyclerItemClickListener) {
        super(albumList, recyclerItemClickListener);
        this.albumList = albumList;
    }

    public void onItemClick() {
        recyclerItemClickListener.onItemClick(albumList);
    }

    public String getAlbumKey() {
        return albumList.getPhoto_album_key();
    }

    public String getAlbumName() {
        return albumList.getName();
    }

    public long getCreatedat() {
        return albumList.getCreated_at();
    }

    public int getPhotoCount() {
        return albumList.getPhoto_count();
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
}
