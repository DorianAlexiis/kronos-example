package com.kronos.example.di;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.kronos.example.di.components.ApplicationComponent;
import com.kronos.example.di.components.DaggerApplicationComponent;
import com.kronos.example.di.modules.ApplicationModule;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

    public ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        Fabric.with(this, new Crashlytics());
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
