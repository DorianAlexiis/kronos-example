package com.kronos.example.ui.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kronos.example.R;
import com.kronos.example.di.components.ApplicationComponent;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    public static final int TRANSACTION_WITHOUT_ANIMATION = 0;
    private static final String KEY_ERROR = "errror";

    private BaseActivity activity;
    public boolean haveToolbar;
    private Toolbar mToolbar;
    private int resToolbar = 0;
    private boolean errorOverrideCreateView;
    private TextView tvTitle;
    private RxPermissions rxPermissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        rxPermissions = new RxPermissions(activity);
        initialize();
        errorOverrideCreateView = savedInstanceState == null || savedInstanceState.getBoolean(KEY_ERROR);
    }

    public void initialize() {

    }

    abstract public int getFragmentLayoutResId();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getFragmentLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        if (haveToolbar) {
            onCreateToolbar(view, resToolbar, null);
        }
        errorOverrideCreateView = false;
        return view;
    }


    public void onCreateToolbar(View view, int resToolbar, Toolbar toolbar) {
        if (toolbar == null) {
            try {
                if (resToolbar == 0) {
                    resToolbar = R.id.toolbar;
                }
                mToolbar = (Toolbar) view.findViewById(resToolbar);
                tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                mToolbar.setTitle("");
                mToolbar.setSubtitle("");
                setToolBar(mToolbar);
                activity.hideToolbar();
                haveToolbar = true;
                setEnableBackToolbar(true);
            } catch (Exception e) {
                Log.e(TAG + " ", "");
                haveToolbar = false;
            }
        } else {
            mToolbar = toolbar;
            haveToolbar = true;
        }
    }

    public void setupImageToolbar(@NonNull @DrawableRes int resImage, boolean enable) {
        activity.setupImageToolbar(resImage, enable);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (errorOverrideCreateView) {
            throw new RuntimeException("You should call the super for onCreateview");
        }

        if (haveToolbar) {
            activity.hideToolbar();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (haveToolbar && !haveToolbarLastFragment()) {
            activity.showToolbar();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ERROR, errorOverrideCreateView);
    }


    public void setHaveToolbar(boolean haveToolbar) {
        this.haveToolbar = haveToolbar;
    }

    protected void showProgressDialog() {
        activity.showProgressDialog();
    }

    protected void replaceFragment(Fragment fragment){
        pushFragment(fragment, R.id.container, false );
    }

    protected void pushFragment(Fragment fragment) {
        activity.pushFragment(fragment);
    }

    protected void pushFragment(Fragment fragment, int container, boolean addBackStack) {
        activity.pushFragment(fragment, container, addBackStack);
    }

    protected void dismissDialog() {
        if (activity != null) {
            activity.dismissProgressDialog();
        }
    }

    protected void setTitle(@NonNull @StringRes int resId) {
        setTitle(getContext().getString(resId));
    }

    protected void setTitle(String title) {
        if (haveToolbar) {
            mToolbar.setTitle("");
            mToolbar.setSubtitle("");
            tvTitle.setText(title);
        } else {
            activity.setTitle(title);
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean onBackToolbar(){
        return false;
    }


    protected void goBack() {
        activity.goBack();
    }

    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog
                .Builder(getActivity(), R.style.MyDialogTheme)
                .setTitle(getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .create()
                .show();
    }

    public void setToolBar(Toolbar toolBar) {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        if (baseActivity != null) {
            mToolbar = toolBar;
            haveToolbar = true;
            ((BaseActivity) getActivity()).setSupportActionBar(toolBar);
        }
    }

    public void setEnableBackToolbar(boolean enable) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(enable);
        }
    }

    public void startActivity(Intent intent) {
        activity.startActivity(intent);
    }

    public String getLastTagFragment() {
        return activity.getLastTagFragment();
    }

    public boolean haveToolbarLastFragment() {
        String tag = getLastTagFragment();
        BaseFragment baseFragment = activity.findFragmentByTag(tag);
        return baseFragment != null && (!tag.isEmpty() && baseFragment.haveToolbar);
    }


    public ApplicationComponent getApplicationComponent() {
        return this.activity.getApplicationComponent();
    }

    public RxPermissions getRxPermissions(){
        return this.rxPermissions;
    }
}
