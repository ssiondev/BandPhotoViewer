package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bandphotoviewer.Model.AuthorizationInfo;
import com.bandphotoviewer.Utils.Pref;

/**
 * token check 후 인텐트 넘기는 역할만 수행하는 액티비티
 */


public class TokenCheckBindingActivity extends BaseBindingActivity {
    private static final String TAG = TokenCheckBindingActivity.class.getSimpleName();

    private AuthorizationInfo authorizationInfo;
    private Pref pref = Pref.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref.setContext(this);
        checkAccessTokenExist();
    }

    public void checkAccessTokenExist() {
        authorizationInfo = pref.getObject(Pref.ACCESS_TOKEN_KEY, null, AuthorizationInfo.class);
        if(authorizationInfo != null){
            startActivity(new Intent(TokenCheckBindingActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(TokenCheckBindingActivity.this, LoginBindingActivity.class));
            finish();
        }
    }
}
