package com.kronos.example.di.components;


import com.kronos.example.di.modules.ApplicationModule;
import com.kronos.example.ui.modules.detail.DetailContactFragment;
import com.kronos.example.ui.modules.main.MainActivity;
import com.kronos.example.ui.modules.main.MainFragment;
import com.kronos.example.ui.modules.splash.SplashFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(
        modules = {
                ApplicationModule.class
        }
)

public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(MainFragment mainFragment);

    void inject(DetailContactFragment detailContactFragment);

    void inject(SplashFragment splashFragment);
}
