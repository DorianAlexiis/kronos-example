package com.kronos.example.ui.modules.splash;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class SplashFragmentPresenter {

    private SplashFragmentView splashFragmentView;

    @Inject
    public SplashFragmentPresenter() {
    }

    public void setView(SplashFragmentView splashFragmentView){
        this.splashFragmentView = splashFragmentView;
    }

    public void sleepTime(){
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        splashFragmentView.gotoMainFragment();
                    }

                    @Override
                    public void onError(Throwable e) {
                        splashFragmentView.gotoMainFragment();
                    }

                    @Override
                    public void onNext(Long aLong) {}
                });
    }
}
