package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.bandphotoviewer.Model.BandAlbumModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017. 12. 20..
 */

public class AlbumListViewModel extends AbstractViewModel {

    private BandAlbumModel bandAlbumModel;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.ALBUMLIST;
    }

    public AlbumListViewModel(BandAlbumModel bandAlbumModel, RecyclerItemClickListener recyclerItemClickListener) {
        super(recyclerItemClickListener);
        this.bandAlbumModel = bandAlbumModel;
    }

    public void onItemClick() {
        recyclerItemClickListener.onItemClick(bandAlbumModel);
    }

    public String getAlbumKey() {
        return bandAlbumModel.getPhoto_album_key();
    }

    public String getAlbumName() {
        return bandAlbumModel.getName();
    }

    public long getCreatedat() {
        return bandAlbumModel.getCreated_at();
    }

    public int getPhotoCount() {
        return bandAlbumModel.getPhoto_count();
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
