package com.kronos.example.ui.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kronos.example.R;
import com.kronos.example.di.App;
import com.kronos.example.di.components.ApplicationComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {
    public static final int TRANSACTION_WITHOUT_ANIMATION = 0;
    private static final String TAG_FRAGMENTS = "fragments";
    private final String TAG = BaseActivity.class.getSimpleName();


    protected FragmentManager fm;
    protected ArrayList<String> mTagFragments;
    private ProgressDialog progressDialog = null;
    private AlertDialog alertDialog = null;
    private boolean haveToolbar;
    private Toolbar mToolbar;

    @Nullable
    @BindView(R.id.tvTitle)
    TextView tvTitlte;

    public int getActivityLayoutResId() {
        return R.layout.activity_base;
    }

    public void onCreateView(Bundle savedInstanceState){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Rubik-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        fm = getSupportFragmentManager();
        mTagFragments = new ArrayList<>();
        setContentView(getActivityLayoutResId());
        ButterKnife.bind(this);
        onCreateView(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void setToolbar(Toolbar toolBar) {
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            mToolbar = toolBar;
            haveToolbar = true;
        }
    }

    @Override
    public void setTitle(@NonNull @StringRes int titleId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleId);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setSubtitle("");
            if(tvTitlte != null){
                tvTitlte.setText(title);
            }
        }
    }

    public void showToolbar() {
        if ((getSupportActionBar() != null) && haveToolbar) {
            setToolbar(mToolbar);
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    public void hideToolbar() {
        if ((getSupportActionBar() != null) && haveToolbar) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    public void setupImageToolbar(@NonNull @DrawableRes int resImage, boolean enable) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setHomeAsUpIndicator(resImage);
            getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            if (mTagFragments.size() > 0) {
                Fragment fragment = fm.findFragmentByTag(mTagFragments.get(mTagFragments.size() - 1));
                if (fragment instanceof BaseFragment) {
                    BaseFragment base = (BaseFragment) fragment;
                    if (base.onBackToolbar()) {
                        return true;
                    }
                }
            }
            goBack();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (mTagFragments.size() > 0) {
            Fragment fragment = fm.findFragmentByTag(mTagFragments.get(mTagFragments.size() - 1));
            if (fragment instanceof BaseFragment) {
                BaseFragment base = (BaseFragment) fragment;
                if (base.onBackPressed()) {
                    return;
                }
            }
            mTagFragments.remove(mTagFragments.size() - 1);
        }
        super.onBackPressed();
    }

    /**
     * guarda la instancia de todos los tags y el ArrayList de Tags
     *
     * @param outState  bundle para guardar datos
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; i < mTagFragments.size(); i++) {
            if (fm.findFragmentByTag(mTagFragments.get(i)) != null) {
                fm.putFragment(outState,
                        mTagFragments.get(i),
                        fm.findFragmentByTag(mTagFragments.get(i))
                );
            }
        }
        outState.putStringArrayList(TAG_FRAGMENTS, mTagFragments);
    }

    /**
     * se restablece todos los fragment con el ArrayList que contiene los tags
     *
     * @param savedInstanceState  bundle con los datos para restablecer
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTagFragments = savedInstanceState.getStringArrayList(TAG_FRAGMENTS);
        if (mTagFragments == null) {
            return;
        }
        for (int i = 0; i < mTagFragments.size(); i++) {
            Fragment fragment = fm.getFragment(savedInstanceState, mTagFragments.get(i));
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            if ((fragmentTransaction != null) && (fragment != null)) {
                fragmentTransaction
                        .replace(R.id.container, fragment, mTagFragments.get(i))
                        .commit();
            }
        }
    }


    public void goBack() {
        if (mTagFragments.size() > 0) {
            mTagFragments.remove(mTagFragments.size() - 1);
            fm.popBackStackImmediate();
        }
    }

    protected void replaceFragment(Fragment fragment){
        pushFragment(fragment, R.id.container, false );
    }

    public void pushFragment(Fragment fragment) {
        pushFragment(fragment, R.id.container, true);
    }

    public void pushFragment(Fragment fragment, int container, boolean addBackStack, int... animations) {
        FragmentTransaction transaction = fm.beginTransaction();
        String tag = fragment.getClass().getSimpleName();

        if (addBackStack) {
            transaction.addToBackStack(tag);
        }

        switch (animations.length) {
            case 0:
                transaction.setCustomAnimations(
                        R.anim.push_show_in_simple,
                        R.anim.push_hidden_out_simple,
                        0,
                        0);
                break;
            case 1:
                break;
            case 2:
                transaction.setCustomAnimations(animations[0], animations[1]);
                break;
            case 4:
                transaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
                break;
            default:
                throw new RuntimeException("Error with animations transaction");
        }


        transaction.replace(container, fragment, tag);
        try {
            transaction.commit();
        } catch (Exception e) {
            return;
        }
        if((mTagFragments.size() == 0 )|| addBackStack){
            mTagFragments.add(tag);
        }else if (mTagFragments.size() > 0) {
            mTagFragments.set(mTagFragments.size() - 1, tag);
        }
    }

    public void showProgressDialog() {
        showProgressDialog(R.string.loading);
    }

    public void showProgressDialog(@NonNull @StringRes int resMsg) {
        showProgressDialog(getString(R.string.app_name), getString(resMsg), false);
    }


    public void showProgressDialog(String title, String msg, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this,R.style.MyDialogTheme);
            progressDialog.setProgressStyle(R.style.MyDialogTheme);
            progressDialog.setCancelable(cancelable);
            progressDialog.setTitle(title);
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public String getLastTagFragment() {
        if (mTagFragments.size() == 0) {
            return "";
        } else {
            return mTagFragments.get(mTagFragments.size() - 1);
        }
    }

    public BaseFragment findFragmentByTag(String tag){
        try{
            return (BaseFragment) fm.findFragmentByTag(tag);
        }catch (Exception e){
            return null;
        }
    }

    public void showOptionDialog(String title , CharSequence[] items, final DialogInterface.OnClickListener listener) {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setTitle(title);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onClick(dialogInterface, i);
                    dialogInterface.dismiss();
                    alertDialog = null;
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        } catch(Exception e){
            Log.d(TAG+" Dars","error dialogue option");
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fm.findFragmentByTag(getLastTagFragment());
        if ((fragment != null) && (fragment instanceof BaseFragment)) {
            BaseFragment base = (BaseFragment) fragment;
            base.onActivityResult(requestCode, resultCode, data);
        }
    }


}
