package com.kronos.example.ui.adapters;

import android.content.Context;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kronos.example.R;
import com.kronos.example.data.models.Contact;
import com.kronos.example.data.models.Phone;
import com.kronos.example.utils.Constant;
import com.kronos.example.utils.GlideHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhonesAdapter extends RecyclerView.Adapter<PhonesAdapter.ContactViewHolder> {

    private List<Phone> list;
    private Context context;

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.ivType)
        ImageView ivType;
        @BindView(R.id.divider)
        View divider;

        ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public PhonesAdapter(Context context, List<Phone> list) {
        this.context = context;
        this.list = list;
    }

    public PhonesAdapter(Context context, RequestManager glide) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Phone phone = list.get(position);

        holder.tvNumber.setText(phone.getNumber());
        holder.tvType.setText(phone.getType());
        switch (phone.getType()){
            case Constant.TYPE_HOME:
                holder.ivType.setImageResource(R.drawable.ic_type_home);
                break;
            case Constant.TYPE_CELLPHONE:
                holder.ivType.setImageResource(R.drawable.ic_type_cellphone);
                break;
            case Constant.TYPE_OFFICE:
                holder.ivType.setImageResource(R.drawable.ic_type_office);
                break;
        }
        holder.divider.setVisibility(position == (list.size()-1) ? View.GONE : View.VISIBLE);
    }

    public Phone getItemPosition(int position) {
        return list.get(position);
    }

}