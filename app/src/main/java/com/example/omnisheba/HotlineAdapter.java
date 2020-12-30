package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HotlineAdapter extends RecyclerView.Adapter<ViewHolderHotline> {

    ContactHotline contactHotline;
    ArrayList<Hotline> hotlineArrayList;

    public HotlineAdapter(ContactHotline contactHotline, ArrayList<Hotline> hotlineArrayList) {
        this.contactHotline = contactHotline;
        this.hotlineArrayList = hotlineArrayList;
    }

    @NonNull
    @Override
    public ViewHolderHotline onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(contactHotline.getBaseContext());
        View view = layoutInflater.inflate(R.layout.hotline_list,parent,false);
        return new ViewHolderHotline(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHotline holder, int position) {
        holder.name.setText(hotlineArrayList.get(position).getName());
        holder.email.setText(hotlineArrayList.get(position).getEmail());
        holder.hotline.setText(hotlineArrayList.get(position).getHotline());
        holder.location.setText(hotlineArrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return hotlineArrayList.size();
    }
}
