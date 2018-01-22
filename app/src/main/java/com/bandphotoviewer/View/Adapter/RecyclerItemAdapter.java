package com.bandphotoviewer.View.Adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bandphotoviewer.BR;
import com.bandphotoviewer.Model.BandAlbumModel;
import com.bandphotoviewer.Model.BandListModel;
import com.bandphotoviewer.Model.BandPhotoModel;
import com.bandphotoviewer.Utils.Pref;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.ViewModel.AlbumListViewModel;
import com.bandphotoviewer.ViewModel.BandListViewModel;
import com.bandphotoviewer.ViewModel.BindListViewType;
import com.bandphotoviewer.ViewModel.PhotoListViewModel;
import com.bandphotoviewer.ViewModel.RecyclerItemClickListener;
import com.bandphotoviewer.databinding.ItemAlbumcardviewBinding;
import com.bandphotoviewer.databinding.ItemGridPhotoBinding;
import com.bandphotoviewer.databinding.ItemMaincardviewBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.BindingViewHolder> {

    private Context context;
    private RecyclerItemClickListener recyclerItemClickListener;
    private Pref pref = Pref.getInstance();

    private List<AbstractViewModel> itemList = new ArrayList<>();

    public RecyclerItemAdapter(Context context, RecyclerItemClickListener recyclerItemClickListener) {
        this.context = context;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public List<BandListModel> convertToBandList() {
        String json = pref.getString(Pref.BAND_LIST_KEY, null);
        Type listType = new TypeToken<ArrayList<BandListModel>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<BandListModel> list = gson.fromJson(json, listType);
        return list;
    }

    public void setBandItemList(List<BandListModel> bandListModels) {
        List<AbstractViewModel> itemList = new ArrayList<>();

        for (BandListModel bandListModel : bandListModels) {
            itemList.add(new BandListViewModel(bandListModel, recyclerItemClickListener));
        }

        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void setAlbumItemList(List<BandAlbumModel> bandAlbumModels) {
        List<AbstractViewModel> itemList = new ArrayList<>();

        for (BandAlbumModel bandAlbumModel : bandAlbumModels) {
            itemList.add(new AlbumListViewModel(bandAlbumModel, recyclerItemClickListener));
        }
        this.itemList = itemList;
        Collections.reverse(itemList);
        notifyDataSetChanged();
    }

    public void setPhotoItemList(List<BandPhotoModel> bandPhotoModels) {
        List<AbstractViewModel> itemList = new ArrayList<>();

        for (BandPhotoModel model : bandPhotoModels) {
            itemList.add(new PhotoListViewModel(model, recyclerItemClickListener));
        }
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (BindListViewType.values()[viewType]) {
            case BANDLIST:
                return new BindingViewHolder<ItemMaincardviewBinding, BandListModel>(ItemMaincardviewBinding.inflate(LayoutInflater.from(context)));
            case ALBUMLIST:
                return new BindingViewHolder<ItemAlbumcardviewBinding, BandAlbumModel>(ItemAlbumcardviewBinding.inflate(LayoutInflater.from(context)));
            case PHOTOLIST:
                return new BindingViewHolder<ItemGridPhotoBinding, BandPhotoModel>(ItemGridPhotoBinding.inflate(LayoutInflater.from(context)));
            default:
                return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.bind(itemList.get(position));
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

    public static class BindingViewHolder<T extends ViewDataBinding, VM> extends RecyclerView.ViewHolder {
        private T binding;
        public VM viewmodel;

        private BindingViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(VM viewmodel) {
            binding.setVariable(BR.viewModel, viewmodel);
            binding.executePendingBindings();
        }

    }


}
