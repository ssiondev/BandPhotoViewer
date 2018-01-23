package com.bandphotoviewer.View.Activity;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bandphotoviewer.model.Photo;
import com.bandphotoviewer.R;
import com.bandphotoviewer.utils.Pref;
import com.bandphotoviewer.ViewModel.PhotoDetailViewModel;
import com.bandphotoviewer.databinding.ActivityPhotoDetailBinding;
import com.bandphotoviewer.databinding.ItemPhotodetailBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2018. 1. 14..
 */

public class PhotoDetailBindingActivity extends BaseToolbarBindingActivity<ActivityPhotoDetailBinding> {
    private static final String TAG = PhotoDetailBindingActivity.class.getSimpleName();

    private List<Photo> photoList = new ArrayList<>();
    private PhotoPagerAdapter photoViewPagerAdapter;
    private Pref pref = Pref.getInstance();

    private ViewPager viewPager;
    private String albumKey;
    private Boolean isSlideShow;
    private int currentPage = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_photo_detail);
        setNavigationIconVisibility(TAG, View.VISIBLE);

        getIntentData(getIntent());
        initView();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    public void getIntentData(Intent intent) {
        albumKey = intent.getStringExtra("album_key");
        isSlideShow = intent.getBooleanExtra("slide_show", false);
    }

    public void initView() {
        viewPager = getContentBinding().detailViewPager;
        photoList = convertToPhotoList();

        photoViewPagerAdapter = new PhotoPagerAdapter();
        viewPager.setAdapter(photoViewPagerAdapter);

        if (isSlideShow) {
            photoViewPagerAdapter.setAutoPager();
        }
    }

    public List<Photo> convertToPhotoList() {
        String json = pref.getString(Pref.BAND_PHOTO_KEY + albumKey, null);
        Type listType = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<Photo> list = gson.fromJson(json, listType);
        return list;
    }

    public class PhotoPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            ItemPhotodetailBinding itemPhotodetailBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(container.getContext()),
                            R.layout.item_photodetail, container, false);

            itemPhotodetailBinding.setViewModel(new PhotoDetailViewModel(photoList.get(position)));
            container.addView(itemPhotodetailBinding.getRoot(), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            return itemPhotodetailBinding.getRoot();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        private void setAutoPager() {
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(currentPage, true);
                    if (currentPage == photoList.size()) {
                        finish();
                    } else {
                        ++currentPage;
                    }
                }
            };
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, 1500, 2000);
        }
    }
}
