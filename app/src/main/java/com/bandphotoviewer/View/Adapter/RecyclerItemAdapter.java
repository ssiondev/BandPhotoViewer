package com.bandphotoviewer.View.Adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bandphotoviewer.BR;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.customview.BindListViewType;
import com.bandphotoviewer.databinding.ItemAlbumcardviewBinding;
import com.bandphotoviewer.databinding.ItemGridPhotoBinding;
import com.bandphotoviewer.databinding.ItemMaincardviewBinding;
import com.bandphotoviewer.model.Album;
import com.bandphotoviewer.model.Band;
import com.bandphotoviewer.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.BindingViewHolder> {
    private ArrayList<AbstractViewModel> itemList = new ArrayList<>();
    private Context context;

    public RecyclerItemAdapter(Context context) {
        this.context = context;
    }

    public void setItemList(List<AbstractViewModel> viewModelList) {
        this.itemList.clear();
        this.itemList.addAll(viewModelList);
    }

    public void addItemList(List<AbstractViewModel> viewModelList) {
        this.itemList.addAll(viewModelList);
    }

    public void clearItemList() {
        this.itemList.clear();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (BindListViewType.values()[viewType]) {
            case BANDLIST:
                return new BindingViewHolder<ItemMaincardviewBinding, Band>(ItemMaincardviewBinding.inflate(LayoutInflater.from(context)));
            case ALBUMLIST:
                return new BindingViewHolder<ItemAlbumcardviewBinding, Album>(ItemAlbumcardviewBinding.inflate(LayoutInflater.from(context)));
            case PHOTOLIST:
                return new BindingViewHolder<ItemGridPhotoBinding, Photo>(ItemGridPhotoBinding.inflate(LayoutInflater.from(context)));
            default:
                return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public ArrayList<AbstractViewModel> getItemList() {
        return itemList;
    }

    public static class BindingViewHolder<T extends ViewDataBinding, VM> extends RecyclerView.ViewHolder {
        private T binding;
        public VM viewmodel;

        private BindingViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(VM viewmodel, int position) {
            binding.setVariable(BR.position, position);
            binding.setVariable(BR.viewModel, viewmodel);
            binding.executePendingBindings();
        }
    }
}
