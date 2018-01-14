package com.kronos.example.ui.modules.splash;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kronos.example.R;
import com.kronos.example.ui.base.BaseFragment;
import com.kronos.example.ui.modules.main.MainFragment;

import javax.inject.Inject;

import butterknife.BindView;


public class SplashFragment extends BaseFragment implements SplashFragmentView{
    private static final String ARG_PARAM_FINISHEP = "finishep";

    private boolean finished_splash = false;

    @BindView(R.id.ivPinBlack)
    ImageView ivPinBlack;
    @BindView(R.id.ivPinRed)
    ImageView ivPinRed;

    @Inject
    SplashFragmentPresenter splashFragmentPresenter;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
       return new SplashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        if(savedInstanceState != null){
            finished_splash = savedInstanceState.getBoolean(ARG_PARAM_FINISHEP);
        }
    }

    @Override
    public int getFragmentLayoutResId() {
        return R.layout.fragment_splash;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivPinBlack.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_left_pin));
        ivPinRed.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_right_pin));
        splashFragmentPresenter.sleepTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(finished_splash){
            replaceFragment(MainFragment.newInstance());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_PARAM_FINISHEP, finished_splash);
    }

    @Override
    public void initialize() {
        getApplicationComponent().inject(this);
        splashFragmentPresenter.setView(this);
    }

    @Override
    public void gotoMainFragment() {
        finished_splash = true;
        pushFragment(MainFragment.newInstance(), R.id.container, false);
    }
}
