package com.bandphotoviewer.View.Activity;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.bandphotoviewer.R;
import com.bandphotoviewer.View.Adapter.ViewPagerAdapter;
import com.bandphotoviewer.ViewModel.AbstractViewModel;
import com.bandphotoviewer.ViewModel.PhotoItemViewModel;
import com.bandphotoviewer.customview.PageScrollListener;
import com.bandphotoviewer.databinding.ActivityPhotoDetailBinding;
import com.bandphotoviewer.model.Page;
import com.bandphotoviewer.model.Photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2018. 1. 14..
 */

public class PhotoDetailBindingActivity extends BaseBindingActivity<ActivityPhotoDetailBinding> {
    private static final String TAG = PhotoDetailBindingActivity.class.getSimpleName();

    private List<Photo> photoList = new ArrayList<>();

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private String bandKey;
    private String albumKey;

    private Boolean isSlideShow;
    private int currentPosition;

    private Page page;
    private PageScrollListener pageScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityLayout(R.layout.activity_photo_detail);

        getIntentData();
        initView();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    public void getIntentData() {
        bandKey = getIntent().getStringExtra("band_key");
        albumKey = getIntent().getStringExtra("album_key");
        isSlideShow = getIntent().getBooleanExtra("slide_show", false);
        currentPosition = getIntent().getIntExtra("select_index", 0);
        Serializable serializable = getIntent().getSerializableExtra("next_page");
        if (serializable != null) {
            page = new Page();
            page.setNextParams((HashMap<String, String>) serializable);
        }
        photoList = getIntent().getParcelableArrayListExtra("photo_list");
    }

    public void initView() {
        viewPager = getActivityBinding().detailViewPager;
        viewPagerAdapter = new ViewPagerAdapter(bandKey, albumKey);
        viewPagerAdapter.addItemList(convertPhotoToPhotoViewModel(photoList));

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(currentPosition);
        viewPager.addOnPageChangeListener(pageScrollListener);

        if (isSlideShow) {
            setAutoPager();
        }
    }

    private List<AbstractViewModel> convertPhotoToPhotoViewModel(List<Photo> originalPhotoList) {
        List<AbstractViewModel> viewModelList = new ArrayList<>();
        for (Photo photo : originalPhotoList) {
            viewModelList.add(new PhotoItemViewModel(photo, null));
        }
        return viewModelList;
    }

    private void setAutoPager() {
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            viewPager.setCurrentItem(currentPosition, true);
            if (currentPosition == photoList.size()) {
                finish();
            } else {
                ++currentPosition;
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 1500, 1800);
    }
}
