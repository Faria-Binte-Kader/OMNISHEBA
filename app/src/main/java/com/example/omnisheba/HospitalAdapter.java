package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<ViewHolderHospital> {

    FindHospital findHospital;
    ArrayList<Hospital> hospitalArrayList;

    public HospitalAdapter(FindHospital findHospital, ArrayList<Hospital> hospitalArrayList) {
        this.findHospital = findHospital;
        this.hospitalArrayList = hospitalArrayList;
    }

    @NonNull
    @Override
    public ViewHolderHospital onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findHospital.getBaseContext());
        View view = layoutInflater.inflate(R.layout.hospital_list,parent,false);
        return new ViewHolderHospital(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHospital holder, int position) {
        holder.name.setText(hospitalArrayList.get(position).getName());
        holder.type.setText(hospitalArrayList.get(position).getType());
        holder.description.setText(hospitalArrayList.get(position).getDescription());
        holder.location.setText(hospitalArrayList.get(position).getLocation());
        holder.hotline.setText(hospitalArrayList.get(position).getHotline());
        holder.email.setText(hospitalArrayList.get(position).getEmail());
        holder.foundationYear.setText(hospitalArrayList.get(position).getFoundation());
    }

    @Override
    public int getItemCount() {
        return hospitalArrayList.size();
    }
}
