package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bandphotoviewer.model.AuthorizationInfo;
import com.bandphotoviewer.network.RetrofitHelper;
import com.bandphotoviewer.utils.Pref;

import io.reactivex.disposables.Disposable;
/**
 * Created by ssion.dev on 2017. 12. 28..
 *
 */

public class RedirectBindingActivity extends BaseBindingActivity {
    private final String TAG = RedirectBindingActivity.class.getSimpleName();

    private RetrofitHelper retrofitHelper = new RetrofitHelper();
    private Pref pref = Pref.getInstance();

    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRedirectUrlIntent(getIntent());

        pref.setContext(this);
    }

    private void getRedirectUrlIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkUrlData = intent.getData();

        startActivity(new Intent(appLinkAction, appLinkUrlData));
        callRetrofitForLogin(appLinkUrlData);

        finish();
    }

    private void callRetrofitForLogin(Uri uri){
        String auth_token = uri.getQueryParameter("code");
        if (auth_token != null) {
            disposable = retrofitHelper.requestForAuthToken(auth_token)
                    .subscribe(authorizationInfo ->
                            completeLogin(authorizationInfo)
                            ,throwable -> throwable.printStackTrace());
        } else {
            startActivity(new Intent(RedirectBindingActivity.this, LoginBindingActivity.class));
            finish();
        }
    }

    private void completeLogin(AuthorizationInfo authorizationInfo){
        retrofitHelper.saveJsonToPref(authorizationInfo);
        startActivity(new Intent(RedirectBindingActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
