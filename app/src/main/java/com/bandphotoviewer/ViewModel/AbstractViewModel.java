package com.bandphotoviewer.ViewModel;

import com.bandphotoviewer.customview.BindListViewType;

/**
 * Root ViewModel
 */

abstract public class AbstractViewModel<T> {
    private final T item;

    public AbstractViewModel(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    abstract public BindListViewType getViewType();

}
