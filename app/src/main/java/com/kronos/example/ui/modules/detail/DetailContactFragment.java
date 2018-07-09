package com.kronos.example.ui.modules.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dars.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.kronos.example.R;
import com.kronos.example.data.models.Addresses;
import com.kronos.example.data.models.Contact;
import com.kronos.example.data.models.Phone;
import com.kronos.example.di.App;
import com.kronos.example.ui.adapters.PhonesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class DetailContactFragment extends BaseFragment implements DetailFragmentView{

    private static final String ARG_PARAM_CONTACT = "contact";

    private Contact mContact;
    private ActionBarDrawerToggle toggle;
    private StaggeredGridLayoutManager mLayoutManager;
    private PhonesAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.imgDetail)
    ImageView imgDetail;
    @BindView(R.id.rvPhones)
    RecyclerView rvPhones;
    @BindView(R.id.llWithoutElement)
    LinearLayout llWithoutElement;
    @BindView(R.id.cvInfo)
    CardView cvInfo;

    @BindView(R.id.tvTitleWork)
    TextView tvTitleWork;
    @BindView(R.id.tvWork)
    TextView tvWork;
    @BindView(R.id.tvTitleHome)
    TextView tvTitleHome;
    @BindView(R.id.tvHome)
    TextView tvHome;

    @Inject
    DetailContactPresenter detailContactPresenter;

    public DetailContactFragment() {
        // Required empty public constructor
    }


    public static DetailContactFragment newInstance(Contact contact) {
        DetailContactFragment fragment = new DetailContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContact = getArguments().getParcelable(ARG_PARAM_CONTACT);
        }
        setHaveToolbar(true);
    }

    @Override
    public int getFragmentLayoutResId() {
        return R.layout.fragment_detail_contact;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toggle = new ActionBarDrawerToggle(getActivity(), drawer_layout, toolbar, R.string.detail_navigation_drawer_open, R.string.detail_navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        detailContactPresenter.getContact(mContact);
    }

    @Override
    public void initialize() {
        ((App) getActivity().getApplication()).getApplicationComponent().inject(this);
        detailContactPresenter.setView(this);
    }



    private void setToolbar(Contact contact){
        String name = getResources().getString(R.string.main_contact_name, contact.getFirst_name(), contact.getLast_name());
        collapsingToolbar.setTitle(name);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setNavigationOnClickListener(view -> goBack());
    }

    public void setPhones() {
        List<Phone> phones = mContact.getPhones();
        for (int i = 0; i<phones.size(); i ++){
            if(phones.get(i).getNumber() == null){
                phones.remove(i--);
            }
        }

        if(phones.isEmpty()){
            llWithoutElement.setVisibility(View.VISIBLE);
            rvPhones.setVisibility(View.GONE);
        }else {
            llWithoutElement.setVisibility(View.GONE);
            rvPhones.setVisibility(View.VISIBLE);

            //LOAD RECYCLERVIEW
            mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            rvPhones.setLayoutManager(mLayoutManager);
            mAdapter = new PhonesAdapter(getContext(), phones);

            rvPhones.setAdapter(mAdapter);
            rvPhones.setNestedScrollingEnabled(false);
        }
        mContact.setPhones(phones);
    }

    public void setAddress(List<Addresses> address) {
        if(address.isEmpty()){
            cvInfo.setVisibility(View.GONE);
        }else {
            Addresses addres = address.get(0);
            if((addres.getHome() == null) && (addres.getWork() == null)){
                cvInfo.setVisibility(View.GONE);
            }else{
                cvInfo.setVisibility(View.VISIBLE);
                if(addres.getWork()== null){
                    tvWork.setVisibility(View.GONE);
                    tvTitleWork.setVisibility(View.GONE);
                }else{
                    tvWork.setVisibility(View.VISIBLE);
                    tvTitleWork.setVisibility(View.VISIBLE);
                    tvWork.setText(addres.getWork());
                }

                if(addres.getHome()== null){
                    tvHome.setVisibility(View.GONE);
                    tvTitleHome.setVisibility(View.GONE);
                }else{
                    tvHome.setVisibility(View.VISIBLE);
                    tvTitleHome.setVisibility(View.VISIBLE);
                    tvHome.setText(addres.getHome());
                }
            }
        }
    }

    @Override
    public void setDetailContact(Contact contact) {
        setToolbar(contact);
        mContact = contact;

        setPhones();
        setAddress(mContact.getAddresses());

        if (mContact.getPhoto().isEmpty()) {
            imgDetail.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(this)
                    .load(mContact.getPhoto())
                    .dontAnimate()
                    .placeholder(R.color.colorPrimary)
                    .error(R.drawable.ic_error_404)
                    .centerCrop()
                    .into(imgDetail);
        }

        dismissDialog();
    }

}
