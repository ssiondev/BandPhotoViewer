package com.bandphotoviewer.customview;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView Item Scroll 시 사용되는 Listener
 */

public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    RecyclerView.LayoutManager mLayoutManager;

    public RecyclerViewScrollListener(LinearLayoutManager layoutManager, int visibleThreshold) {
        this.mLayoutManager = layoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    public RecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

        if(layoutManager == null) {
            return;
        }

        int lastVisibleItemPosition = 0;
        int totalItemCount = view.getLayoutManager().getItemCount();

       if (layoutManager instanceof GridLayoutManager) {
           //현재 화면에서 보여지는 아이템의 마지막 포지션
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            onLoadMore(totalItemCount, view);
            loading = true;
        }
    }

    public abstract void onLoadMore(int totalItemsCount, RecyclerView view);

}