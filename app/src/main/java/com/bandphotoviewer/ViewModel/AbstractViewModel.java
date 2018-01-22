package com.bandphotoviewer.ViewModel;

/**
 * Created by user on 2018. 1. 15..
 */

abstract public class AbstractViewModel {

    protected RecyclerItemClickListener recyclerItemClickListener;

    public AbstractViewModel() {
    }

    public AbstractViewModel(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    abstract public BindListViewType getViewType();

}
