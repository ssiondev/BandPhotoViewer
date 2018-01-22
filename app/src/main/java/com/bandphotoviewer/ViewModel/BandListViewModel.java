package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bandphotoviewer.Model.BandListModel;

/**
 * 여기서는 모델데이터 가지고 룰루랄라해야함
 */

public class BandListViewModel extends AbstractViewModel {

    private BandListModel bandListModel;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.BANDLIST;
    }

    public BandListViewModel(BandListModel bandListModel, RecyclerItemClickListener recyclerItemClickListener) {
        super(recyclerItemClickListener);
        this.bandListModel = bandListModel;
    }

    public void onItemClick() {
        recyclerItemClickListener.onItemClick(bandListModel);
    }

    public String getName() {
        return bandListModel.getName();
    }

    public String getCover() {
        return bandListModel.getCover();
    }

    public String getBandKey() {
        return bandListModel.getBand_key();
    }

    public int getMemberCount() {
        return bandListModel.getMember_count();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setBandCoverImageView(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

}
