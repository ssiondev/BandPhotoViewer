package com.bandphotoviewer.ViewModel;

import com.bandphotoviewer.customview.BindListViewType;
import com.bandphotoviewer.customview.RecyclerItemClickListener;

/**
 * Created by user on 2018. 1. 15..
 */

abstract public class AbstractViewModel<T> {

    private final T item;

    protected RecyclerItemClickListener recyclerItemClickListener;

    public AbstractViewModel(T item) {
        this.item = item;
    }

    public AbstractViewModel(T item, RecyclerItemClickListener recyclerItemClickListener) {
        this.item = item;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public T getItem() {
        return item;
    }

    abstract public BindListViewType getViewType();

}
