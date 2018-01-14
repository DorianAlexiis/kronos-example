package com.kronos.example.di;

import android.app.Application;
import android.content.Context;

import com.kronos.example.di.components.ApplicationComponent;
import com.kronos.example.di.components.DaggerApplicationComponent;
import com.kronos.example.di.modules.ApplicationModule;

public class App extends Application {

    public ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
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
