package com.kronos.example.ui.modules.rateapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kronos.example.R;
import com.kronos.example.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateAppDialogFragment extends DialogFragment {

    private BaseActivity activity;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    public static RateAppDialogFragment newInstace() {
        return new RateAppDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mView = inflater.inflate(R.layout.dialog_rateapp, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle.setText(getString(R.string.rateapp_with_nameapp, getString(R.string.app_name)));
    }

    @OnClick({R.id.tvLater, R.id.tvRateApp})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvLater:
                dismiss();
                break;
            case R.id.tvRateApp:
                break;
        }
    }
}
