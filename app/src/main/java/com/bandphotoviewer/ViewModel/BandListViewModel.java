package com.bandphotoviewer.ViewModel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bandphotoviewer.customview.BindListViewType;
import com.bumptech.glide.Glide;
import com.bandphotoviewer.model.Band;

/**
 * 여기서는 모델데이터 가지고 룰루랄라해야함
 */

public class BandListViewModel extends AbstractViewModel<Band> {

    private final Band band;
    private final Navigator navigator;

    @Override
    public BindListViewType getViewType() {
        return BindListViewType.BANDLIST;
    }

    public BandListViewModel(Band band, Navigator navigator) {
        super(band);
        this.band = band;
        this.navigator = navigator;
    }

    public void onItemClick() {
        if(navigator != null){
            navigator.onClickBandList(band);
        }
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

    public interface Navigator {
        void onClickBandList(Band band);
    }
}
