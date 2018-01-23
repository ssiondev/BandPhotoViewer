package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bandphotoviewer.Model.BandModel;

/**
 * 여기서는 모델데이터 가지고 룰루랄라해야함
 */

public class BandListViewModel extends AbstractViewModel<BandModel> {

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.BANDLIST;
    }

    public BandListViewModel(BandModel bandModel, RecyclerItemClickListener recyclerItemClickListener) {
        super(bandModel, recyclerItemClickListener);
    }

    public void onItemClick() {
        recyclerItemClickListener.onItemClick(getItem());
    }

    public String getName() {
        return getItem().getName();
    }

    public String getCover() {
        return getItem().getCover();
    }

    public String getBandKey() {
        return getItem().getBand_key();
    }

    public int getMemberCount() {
        return getItem().getMember_count();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setBandCoverImageView(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

}
