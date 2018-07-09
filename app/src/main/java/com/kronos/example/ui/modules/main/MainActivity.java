package com.kronos.example.ui.modules.main;

import android.content.Context;
import android.os.Bundle;

import com.android.dars.base.BaseActivity;
import com.kronos.example.ui.modules.splash.SplashFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {


    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);

        if (savedInstanceState == null){
            replaceFragment(SplashFragment.newInstance());
        } else {
            replaceFragment(MainFragment.newInstance());
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
