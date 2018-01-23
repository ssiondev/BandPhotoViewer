package com.bandphotoviewer.View.Activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bandphotoviewer.R;
import com.bandphotoviewer.databinding.ActivityBaseToolbarBinding;
import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;

/**
 * Created by user on 2018. 1. 20..
 */

public abstract class BaseToolbarBindingActivity<T extends ViewDataBinding> extends BaseBindingActivity<ActivityBaseToolbarBinding> {

    private Toolbar toolbar;
    private FrameLayout contentLayout;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    public T contentBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityLayout(R.layout.activity_base_toolbar);

        toolbar = getActivityBinding().toolbar;
        collapsingToolbarLayout = getActivityBinding().toolbarLayout;

        setSupportActionBar(toolbar);

        contentLayout = getActivityBinding().baseContentLayout;
        contentBinding = DataBindingUtil.getBinding(contentLayout);
    }

    public T getContentBinding() {
        return contentBinding;
    }

    public void setContentLayout(int resId) {
        contentBinding = DataBindingUtil.inflate(getLayoutInflater(), resId, contentLayout, true);
    }

    public void setToolbarTitle(String title) {
        Typeface font = Typer.set(getApplicationContext()).getFont(Font.ROBOTO_MEDIUM);
        collapsingToolbarLayout.setTitle(title);

        collapsingToolbarLayout.setExpandedTitleTypeface(font);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTextApperance);

        collapsingToolbarLayout.setCollapsedTitleTypeface(font);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedTextApperance);
    }

    public void setNavigationIconVisibility(String tag, int visibility) {
        if(visibility == View.VISIBLE) {
            if(tag.contains("PhotoDetailBindingActivity")){
                getActivityBinding().toolbar.setNavigationIcon(R.drawable.ic_action_close);
            } else {
                getActivityBinding().toolbar.setNavigationIcon(R.drawable.ic_action_back);
            }
            getActivityBinding().toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
