package com.bandphotoviewer.View.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bandphotoviewer.Model.AuthorizationInfo;
import com.bandphotoviewer.NetworkManager.RequestRetrofitFactory;
import com.bandphotoviewer.Utils.Pref;

import io.reactivex.functions.Consumer;


/**
 * Created by ssion.dev on 2017. 12. 28..
 *
 */

public class RedirectBindingActivity extends BaseBindingActivity {
    private final String TAG = RedirectBindingActivity.class.getSimpleName();

    private RequestRetrofitFactory requestRetrofitFactory = new RequestRetrofitFactory();
    private Pref pref = Pref.getInstance();

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
            requestRetrofitFactory.getCompositeDisposable().add(
                    requestRetrofitFactory.requestForAuthToken(auth_token)
                    .subscribe(consumer));
        } else {
            startActivity(new Intent(RedirectBindingActivity.this, LoginBindingActivity.class));
            finish();
        }
    }

    Consumer<AuthorizationInfo> consumer = new Consumer<AuthorizationInfo>() {
        @Override
        public void accept(AuthorizationInfo authorizationInfo) {
            requestRetrofitFactory.saveJsonToPref(authorizationInfo);
            startActivity(new Intent(RedirectBindingActivity.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
