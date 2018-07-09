package com.kronos.example.ui.modules.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dars.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.kronos.example.R;
import com.kronos.example.data.models.Contact;
import com.kronos.example.di.App;
import com.kronos.example.ui.adapters.ContactAdapter;
import com.kronos.example.ui.modules.detail.DetailContactFragment;
import com.kronos.example.utils.RecyclerItemClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment implements MainFragmentView{

    private static final String ARG_PARAM_CONTACTS = "contacts";

    private ArrayList<Contact> contacts;
    private StaggeredGridLayoutManager mLayoutManager;
    private ContactAdapter mAdapter;

    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;
    @BindView(R.id.llWithoutElement)
    LinearLayout llWithoutElement;
    @BindView(R.id.ivStatus)
    ImageView ivStatus;
    @BindView(R.id.tvDetail)
    TextView tvDetail;

    @Inject
    MainFragmentPresenter mainFragmentPresenter;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHaveToolbar(true);
        if(savedInstanceState != null){
            contacts = savedInstanceState.getParcelableArrayList(ARG_PARAM_CONTACTS);
        }
    }

    @Override
    public int getFragmentLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.main_contacts);
        setupImageToolbar(R.drawable.ic_launcher, false);
        if((savedInstanceState == null) || (contacts == null)){
            loadContacts();
        }else{
            isContactEmpty();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_PARAM_CONTACTS, contacts);
    }

    @Override
    public void initialize() {
        ((App) getActivity().getApplication()).getApplicationComponent().inject(this);
        mainFragmentPresenter.setView(this);
    }

    private void loadContacts() {
        showProgressDialog();
        mainFragmentPresenter.getContacts();
    }

    public void isContactEmpty() {
        if(contacts.isEmpty()){
            llWithoutElement.setVisibility(View.VISIBLE);
            rvContacts.setVisibility(View.GONE);
        }else{
            rvContacts.setVisibility(View.VISIBLE);
            llWithoutElement.setVisibility(View.GONE);
        }
    }

    @Override
    public void setContactsAdapter(ArrayList<Contact> contacts) {
        dismissDialog();
        this.contacts = contacts;
        isContactEmpty();
        if (!contacts.isEmpty()){
            mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            rvContacts.setLayoutManager(mLayoutManager);
            mAdapter = new ContactAdapter(getContext(), Glide.with(MainFragment.this), contacts);

            rvContacts.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
                pushFragment(DetailContactFragment.newInstance(contacts.get(position)));
            }));
            rvContacts.setAdapter(mAdapter);
        }
    }

    @Override
    public void onFailure() {
        dismissDialog();
        llWithoutElement.setVisibility(View.VISIBLE);
        ivStatus.setImageResource(R.drawable.ic_error_connection);
        tvDetail.setText(R.string.main_error_contact);
    }

    @OnClick(R.id.ivStatus)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivStatus:
                loadContacts();
                break;
        }
    }

}
