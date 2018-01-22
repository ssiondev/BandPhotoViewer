package com.bandphotoviewer.View.Activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * 여기서 재사용가능한 툴바 붙이고
 */

public abstract class BaseBindingActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private T activityBinding;

    public void setActivityLayout(@LayoutRes int layoutId) {
        if (activityBinding == null) {
            activityBinding = DataBindingUtil.setContentView(this, layoutId);
        }
    }

    public T getActivityBinding() {
        return activityBinding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

}
