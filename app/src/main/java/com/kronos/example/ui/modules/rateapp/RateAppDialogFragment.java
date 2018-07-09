package com.kronos.example.ui.modules.rateapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.dars.base.BaseDialog;
import com.kronos.example.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RateAppDialogFragment extends BaseDialog {


    @BindView(R.id.tvTitle)
    TextView tvTitle;

    public static RateAppDialogFragment newInstance() {
        return new RateAppDialogFragment();
    }

    @Override
    public int getDialogLayoutResId() {
        return R.layout.dialog_rateapp;
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
