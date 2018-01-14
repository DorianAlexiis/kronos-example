package com.kronos.example.ui.modules.detail;


import com.kronos.example.data.models.Contact;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DetailContactPresenter {
    private DetailFragmentView detailFragmentView;

    @Inject
    public DetailContactPresenter() {
    }

    public void setView(DetailFragmentView detailFragmentView){
        this.detailFragmentView = detailFragmentView;
    }

    public void getContact(Contact mContact) {
        detailFragmentView.setDetailContact(mContact);
    }
}
