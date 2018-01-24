package com.bandphotoviewer.customview;

import android.support.v4.view.ViewPager;

import com.bandphotoviewer.View.Adapter.ViewPagerAdapter;

/**
 * ViewPager에서 사용하는 PageScrollListener
 */

public abstract class PageScrollListener implements ViewPager.OnPageChangeListener {
    private ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
    private int previousTotalItemCount = 0;

    //현재 포지션에서부터 몇 개까지 보여줄 것인지
    private int visibleThreshold = 5;
    private boolean loading = true;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int totalItemCount = viewPagerAdapter.getCount();

        if (totalItemCount < previousTotalItemCount) {
            previousTotalItemCount = viewPagerAdapter.getCount();
            if (totalItemCount == 0) {
                loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (position + visibleThreshold) > totalItemCount) {
            viewPagerAdapter.getPhotoList();
            loading = true;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
