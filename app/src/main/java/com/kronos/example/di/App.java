package com.kronos.example.di;

import android.content.Context;

import com.android.dars.base.BaseApp;
import com.google.android.gms.ads.MobileAds;
import com.kronos.example.R;
import com.kronos.example.di.components.ApplicationComponent;
import com.kronos.example.di.components.DaggerApplicationComponent;
import com.kronos.example.di.modules.ApplicationModule;

public class App extends BaseApp {

    public ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        MobileAds.initialize(this, getString(R.string.admobs_app_id));
    }

    protected void initializeInjector() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
